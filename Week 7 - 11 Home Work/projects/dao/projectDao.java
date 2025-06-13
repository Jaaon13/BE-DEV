package projects.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import projects.entity.Category;
import projects.entity.Material;
import projects.entity.Project;
import projects.entity.Step;
import projects.exception.DbE;
import projects.util.DaoBase;

public class projectDao extends DaoBase {
	
	private final String CATEGORY_TABLE = "category";
	private final String MATERIAL_TABLE = "material";
	private final String PROJECT_TABLE = "project";
	private final String PROJECT_CATEGORY_TABLE = "project_category";
	private final String STEP_TABLE = "step";

	public Project addProject(Project p) {

		String sql = ""
				+ "INSERT INTO " + PROJECT_TABLE + " "
				+ "(project_name, estimated_hours, actual_hours, difficulty, notes) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?)";
		
		try (Connection conn = DbC.getConn()) {
			
			startTransaction(conn);
			
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				setParameter(stmt, 1, p.getProjectName(), String.class);
				setParameter(stmt, 2, p.getEstimatedHours(), BigDecimal.class);
				setParameter(stmt, 3, p.getActualHours(), BigDecimal.class);
				setParameter(stmt, 4, p.getDifficulty(), Integer.class);
				setParameter(stmt, 5, p.getNotes(), String.class);
				
				stmt.executeUpdate();
				
				Integer id = getLastInsertId(conn, PROJECT_TABLE);
				
				commitTransaction(conn);
				
				p.setProjectId(id);
				return p;
				
			} catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbE(e);
			}
			
		} catch (Exception e) {
			System.out.println("\nAction Failed!\n" + e);
			throw new DbE(e);
		}
		
	}
	
	public boolean modifyProject(Project p) {
		
		String sql = "UPDATE " + PROJECT_TABLE + " SET "
				+ "project_name = ?, estimated_hours = ?, actual_hours = ?, difficulty = ?, notes = ? WHERE project_id = ?";
		
		try (Connection conn = DbC.getConn()) {
			
			startTransaction(conn);
			
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				setParameter(stmt, 1, p.getProjectName(), String.class);
				setParameter(stmt, 2, p.getEstimatedHours(), BigDecimal.class);
				setParameter(stmt, 3, p.getActualHours(), BigDecimal.class);
				setParameter(stmt, 4, p.getDifficulty(), Integer.class);
				setParameter(stmt, 5, p.getNotes(), String.class);
				setParameter(stmt, 6, p.getProjectId(), Integer.class);
				
				Integer w = stmt.executeUpdate();
				
				commitTransaction(conn);
				
				if(w == 1) {
					return true;
				} else {
					return false;
				}
				
			} catch (SQLException e) {
				rollbackTransaction(conn);
				throw new DbE(e);
			}
			
		} catch (SQLException e) {
			throw new DbE(e);
		}
	}
	
	public boolean deleteProject(Integer id) {
		
		String sql = "DELETE FROM " + PROJECT_TABLE + " WHERE project_id = ?";
		
		try (Connection conn = DbC.getConn()) {
			
			startTransaction(conn);
			
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				setParameter(stmt, 1, id, Integer.class);
				
				Integer w = stmt.executeUpdate();
				
				commitTransaction(conn);
				
				if(w == 1) {
					return true;
				} else {
					return false;
				}
				
			} catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbE(e);
			}
			
		} catch (Exception e) {
			throw new DbE(e);
		}
		
	}
	
	public List<Project> getProjects() {
		
		String sql = "SELECT * FROM " + PROJECT_TABLE + " ORDER BY project_name";
		
		try (Connection conn = DbC.getConn()) {
			
			startTransaction(conn);
			
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				List<Project> p = new ArrayList<>();
				
				try (ResultSet rs = stmt.executeQuery()) {
					
					while (rs.next()) {
						p.add(extract(rs, Project.class));
					}
					
				}
				
				return p;
				
			} catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbE(e);
			}
			
		} catch (Exception e) {
			System.out.println("\nAction Failed!\n" + e);
			throw new DbE(e);
		}
		
	}

	public Optional<Project> getProjectById(Integer id) {
		
		String sql = "SELECT * FROM " + PROJECT_TABLE + " WHERE project_id = ?";
		
		try (Connection conn = DbC.getConn()) {
		
			startTransaction(conn);
			
			try {
				Project p = null;
				
				try (PreparedStatement stmt = conn.prepareStatement(sql)) {
					setParameter(stmt, 1, id, Integer.class);
					try(ResultSet rs = stmt.executeQuery()) {
						if(rs.next()) {
							p = extract(rs, Project.class);
						}
					}
					
				}
				
				if(Objects.nonNull(p)) {
					p.getMaterials().addAll(getMaterials(conn, id));
					p.getSteps().addAll(getSteps(conn, id));
					p.getCategories().addAll(getCategories(conn, id));
				}
				
				commitTransaction(conn);
				
				return Optional.ofNullable(p);
			} catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbE(e);
			}
			
		} catch (Exception e) {
			throw new DbE(e);
		}
	}

	private List<Category> getCategories(Connection conn, Integer id) throws SQLException {
		List<Category> c = new ArrayList<>();
		
		String sql = "SELECT c.* FROM " + CATEGORY_TABLE + " c "
				+ "JOIN " + PROJECT_CATEGORY_TABLE + " ps USING (category_id) "
				+ "WHERE project_id = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			setParameter(stmt, 1, id, Integer.class);
			
			try (ResultSet rs = stmt.executeQuery()) {
				
				while(rs.next()) {
					c.add(extract(rs, Category.class));
				}
				
				return c;
				
			}
			
		}
	}

	private List<Material> getMaterials(Connection conn, Integer id) throws SQLException {
		
		List<Material> m = new ArrayList<>();
		
		String sql = "SELECT * FROM " + MATERIAL_TABLE + " WHERE project_id = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			setParameter(stmt, 1, id, Integer.class);
			
			try (ResultSet rs = stmt.executeQuery()) {
				
				while(rs.next()) {
					m.add(extract(rs, Material.class));
				}
				
				return m;
				
			}
			
		}
		
	}

	private List<Step> getSteps(Connection conn, Integer id) throws SQLException {
		
		List<Step> s = new ArrayList<>();
		
		String sql = "SELECT * FROM " + STEP_TABLE + " WHERE project_id = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			setParameter(stmt, 1, id, Integer.class);
			
			try (ResultSet rs = stmt.executeQuery()) {
				
				while(rs.next()) {
					s.add(extract(rs, Step.class));
				}
				
				return s;
				
			}
			
		}
		
	}




}
