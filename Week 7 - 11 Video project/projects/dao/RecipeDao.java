package projects.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import projects.entity.Category;
import projects.entity.Ingredient;
import projects.entity.Recipe;
import projects.entity.Step;
import projects.entity.Unit;
import projects.exception.DbException;
import provided.util.DaoBase;

public class RecipeDao extends DaoBase {
	
	private static String RECIPE_TABLE = "recipe";
	private static String INGREDIENT_TABLE = "ingredient";
	private static String UNIT_TABLE = "unit";
	private static String STEP_TABLE = "step";
	private static String CATEGORY_TABLE = "category";
	private static String RECIPE_CATEGORY_TABLE = "recipe_category";
	
	
	 public void executeBatch(List<String> sqlBatch) {
		 
		 try (Connection conn = DbConnection.getConnection()) {
			 
			 startTransaction(conn);
			 
			 try(Statement  stmt = conn.createStatement()) {
				 
				 for(String line : sqlBatch) {
					 
					 stmt.addBatch(line);
					 
				 }
				 
				 stmt.executeBatch();
				 commitTransaction(conn);
				 
			 } catch(Exception e) {
				 
				 conn.rollback();
				 throw new DbException(e);
				 
			 }
			 
		 } catch (SQLException e) {
			 throw new DbException(e);
		 }
		 
	 }
	 
	public Optional<Recipe> fetchRecipe(Integer id) {
		
		String sql = "SELECT * FROM " + RECIPE_TABLE + " WHERE recipe_id = ?";
		
		try(Connection conn = DbConnection.getConnection()) {
			
			startTransaction(conn);
			
			try {
				
				Recipe r = null;
				
				try(PreparedStatement stmt = conn.prepareStatement(sql)) {
					
					setParameter(stmt, 1, id, Integer.class);
					
					
					try(ResultSet rs = stmt.executeQuery()) {
						
						if(rs.next()) {
							r = extract(rs, Recipe.class);
						}
						
					}
					
				}
				
				if(Objects.nonNull(r)) {
					
					r.getIngredients().addAll(fetchIngredients(conn, id));
					r.getStep().addAll(fetchSteps(conn, id));
					r.getCategory().addAll(fetchCategories(conn, id));
					
				}
				
				return Optional.ofNullable(r);
				
			} catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			
		} catch(Exception e) {
			throw new DbException(e);
		}
		
	}

	private List<Category> fetchCategories(Connection conn, Integer id) throws SQLException {
		
		String sql = "SELECT c.* "
				+ "FROM " + RECIPE_CATEGORY_TABLE + " rc "
				+ "JOIN " + CATEGORY_TABLE + " c USING (category_id) "
				+ "WHERE recipe_id = ? "
				+ "ORDER BY c.category_name";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			setParameter(stmt, 1, id, Integer.class);
			
			try (ResultSet rs = stmt.executeQuery()) {
				
				List<Category> c = new LinkedList<>();
				
				while(rs.next()) {
					
					c.add(extract(rs, Category.class));
					
				}
				
				return c;
				
			}
			
		}
		
	}

	private List<Step> fetchSteps(Connection conn, Integer id) throws SQLException {
		
		String sql = "SELECT * FROM " + STEP_TABLE + " WHERE recipe_id = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			setParameter(stmt, 1, id, Integer.class);
			
			try (ResultSet rs = stmt.executeQuery()) {
				
				List<Step> s = new LinkedList<>();
				
				while(rs.next()) {
					s.add(extract(rs, Step.class));
				}
				
				return s;
				
			}
			
		}
		
	}

	private List<Ingredient> fetchIngredients(Connection conn, Integer id) throws SQLException {
		// @formatter:off
		String sql = "SELECT i.*, u.unit_name_singular, u.unit_name_plural "
				+ "FROM " + INGREDIENT_TABLE + " i "
				+ "LEFT JOIN " + UNIT_TABLE + " u USING (unit_id) "
				+ "WHERE recipe_id = ? "
				+ "ORDER BY i.ingredient_order";
		// @formatter:off
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			setParameter(stmt, 1, id, Integer.class);
			
			try(ResultSet rs = stmt.executeQuery()) {
				
				List<Ingredient> i = new LinkedList<>();
				
				while(rs.next()) {
					
					Ingredient ig = extract(rs, Ingredient.class);
					Unit u = extract(rs, Unit.class);
					
					ig.setUnit(u);
					i.add(ig);
					
				}
				
				return i;
				
			}
			
		}
		
	}
	
	public void addIngredient(Ingredient i) {
		
		String sql = "INSERT INTO " + INGREDIENT_TABLE + " (recipe_id, unit_id, ingredient_name, instruction, ingredient_order,"
				+ " amount) " + "VALUES (?, ?, ?, ?, ?, ?)";
		
		try(Connection conn = DbConnection.getConnection()) {
			
			startTransaction(conn);
			
			try {
				
				Integer order = getNextSequenceNumber(conn, i.getRecipeId(), INGREDIENT_TABLE, "recipe_id");
				
				try(PreparedStatement stmt = conn.prepareStatement(sql)) {
					
					setParameter(stmt, 1, i.getRecipeId(), Integer.class);
					setParameter(stmt, 2, i.getUnit().getUnitId(), Integer.class);
					setParameter(stmt, 3, i.getIngredientName(), String.class);
					setParameter(stmt, 4, i.getInstruction(), String.class);
					setParameter(stmt, 5, order, Integer.class);
					setParameter(stmt, 6, i.getAmount(), BigDecimal.class);
					
					stmt.executeUpdate();
					
					commitTransaction(conn);
					
				}
				
			} catch(Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			
		} catch(Exception e) {
			throw new DbException(e);
		}
		
	}

	public Recipe insertRecipe(Recipe r) {
		// #formatter:off
		String sql = "INSERT INTO " + RECIPE_TABLE + " "
				+ "(recipe_name, notes, num_servings, prep_time, cook_time) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?)";
		// #formatter:on
		
		try (Connection conn  = DbConnection.getConnection()) {
			
			startTransaction(conn);
			
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				setParameter(stmt, 1, r.getRecipeName(), String.class);
				setParameter(stmt, 2, r.getNotes(), String.class);
				setParameter(stmt, 3, r.getNumServings(), Integer.class);
				setParameter(stmt, 4, r.getPrepTime(), LocalTime.class);
				setParameter(stmt, 5, r.getCookTime(), LocalTime.class);
				
				stmt.executeUpdate();
				
				Integer recipeid = getLastInsertId(conn, RECIPE_TABLE);
				
				commitTransaction(conn);
				
				r.setRecipe_id(recipeid);
				
				return r;
				
				
			} catch (SQLException e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			
		} catch (SQLException e) {
			throw new DbException(e);
		}
	}
	
	public void addStep(Step s) {
		
		String sql = "INSERT INTO " + STEP_TABLE + " (recipe_id, step_order, step_text)"
				+ "VALUES (?,?,?)";
		
		try(Connection conn = DbConnection.getConnection()) {
			
			startTransaction(conn);
			
			Integer order = getNextSequenceNumber(conn, s.getRecipeId(), STEP_TABLE, "recipe_id");
			
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				setParameter(stmt, 1, s.getRecipeId(), Integer.class);
				setParameter(stmt, 2, order, Integer.class);
				setParameter(stmt, 3, s.getStepText(), String.class);
				
				stmt.executeUpdate();
				commitTransaction(conn);
				
			} catch (SQLException e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			
		} catch (SQLException e) {
			throw new DbException(e);
		}
		
	}
	
	public List<Step> fetchSteps(Integer recipe_id) {

		try(Connection conn = DbConnection.getConnection()) {
			
			startTransaction(conn);
			
			try {
				
				List<Step> s = fetchSteps(conn, recipe_id);
				return s;
				
			} catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			
		} catch (SQLException e) {
			throw new DbException(e);
		}
		
	}
	
	public boolean modifyStep(Step s) {
		
		String sql = "UPDATE " + STEP_TABLE + " SET step_text = ? WHERE step_id = ?";
		
		try(Connection conn = DbConnection.getConnection()) {
			
			startTransaction(conn);
			
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				setParameter(stmt, 1, s.getStepText(), String.class);
				setParameter(stmt, 2, s.getStepId(), Integer.class);
				
				boolean update = stmt.executeUpdate() == 1;
				
				commitTransaction(conn);
				
				return update;
				
			} catch (SQLException e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			
		} catch (SQLException e) {
			throw new DbException(e);
		}
		
	}
	
	public void addCategory(Integer recipe_id, String category) {

		String subQuery = "(SELECT category_id FROM " + CATEGORY_TABLE + " WHERE category_name = ?)";
		
		String sql = "INSERT INTO " + RECIPE_CATEGORY_TABLE + " (recipe_id, category_id) "
				+ "VALUES (?, " + subQuery + ")";
		
		
		try(Connection conn = DbConnection.getConnection()) {
			
			startTransaction(conn);
			
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				setParameter(stmt, 1, recipe_id, Integer.class);
				setParameter(stmt, 2, category, String.class);
				
				stmt.executeUpdate();
				commitTransaction(conn);
				
			} catch (SQLException e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			
		} catch (SQLException e) {
			throw new DbException(e);
		}
		
	}
	
	public List<Category> fetchCategories() {

		String sql = "SELECT * FROM " + CATEGORY_TABLE + " ORDER BY category_name";
		
		try(Connection conn = DbConnection.getConnection()) {
			
			startTransaction(conn);
			
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				try(ResultSet rs = stmt.executeQuery()) {
					
					List<Category> c = new LinkedList<>();
					
					while(rs.next()) {
						
						c.add(extract(rs, Category.class));
						
					}
					
					return c;
					
				}
				
			} catch (SQLException e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			
		} catch (SQLException e) {
			throw new DbException(e);
		}
		
	}
	
	public List<Unit> getAllUnits() {
		
		String sql = "SELECT * FROM " + UNIT_TABLE + " ORDER BY unit_name_singular";
		
		try(Connection conn = DbConnection.getConnection()) {
			
			startTransaction(conn);
			
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				try(ResultSet rs = stmt.executeQuery()) {
					List<Unit> units = new LinkedList<>();
					
					while(rs.next()) {
						units.add(extract(rs, Unit.class));
					}
					
					return units;
				}
				
			} catch (SQLException e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			
		} catch (SQLException e) {
			throw new DbException(e);
		}
		
	}
	
	public boolean deleteRecipe(Integer id) {
		
		String sql = "DELETE FROM " + RECIPE_TABLE + " WHERE recipe_id = ?";
		
		try(Connection conn = DbConnection.getConnection()) {
			
			startTransaction(conn);
			
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				setParameter(stmt, 1, id, Integer.class);
				
				boolean deleted = stmt.executeUpdate() == 1;
				commitTransaction(conn);
				
				return deleted;
				
			} catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			
		} catch (Exception e) {
			throw new DbException(e);
		}
		
	}

	public List<Recipe> getAllRecipe() {
		
		String sql = "SELECT * FROM " + RECIPE_TABLE + " ORDER BY recipe_name";
		
		try(Connection conn = DbConnection.getConnection()) {
			
			startTransaction(conn);
			
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				try(ResultSet rs = stmt.executeQuery()) {
					
					List<Recipe> r = new LinkedList<>();
					
					while(rs.next()) {
						r.add(extract(rs, Recipe.class));
					}
					
					return r;
					
				}
				
			} catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			
		} catch (Exception e) {
			throw new DbException(e);
		}
		
	}
	
}
