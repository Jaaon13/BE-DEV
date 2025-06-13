package projects.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import projects.entity.Category;
import projects.dao.RecipeDao;
import projects.entity.Ingredient;
import projects.entity.Recipe;
import projects.entity.Step;
import projects.entity.Unit;
import projects.exception.DbException;

public class RecipeService {
	
	private static final String SCHEMA_FILE = "recipe_schema.sql";
	private static final String DATA_FILE = "recipe_data.sql";
	
	private static RecipeDao recipeDao = new RecipeDao();
	
	public void createAndPopulateTables() {
		
		loadFromFile(SCHEMA_FILE);
		loadFromFile(DATA_FILE);
		
	}
	
	public Recipe fetchRecipeByID(Integer id) {
		return recipeDao.fetchRecipe(id).orElseThrow(() -> new NoSuchElementException("Recipe with id=" + id + " does not exist"));
	}
	
	public List<Recipe> fetchRecipes() {
		// @formatter:off
		return recipeDao.getAllRecipe()
				.stream()
				.sorted( (r1, r2) -> r1.getRecipe_id() - r2.getRecipe_id() )
				.collect(Collectors.toList());
		// @formatter:on
	}
	
	public Recipe addRecipe(Recipe r) {
		return recipeDao.insertRecipe(r);
	}

	private void loadFromFile(String fileName) {
		
		String content = readFileContent(fileName);
		
		List<String> sqlStatements = convertContent(content);
		
		recipeDao.executeBatch(sqlStatements);
		
	}

	private String readFileContent(String fileName) {

		try {
			Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
			return Files.readString(path);
		} catch (Exception e) {
			throw new DbException(e);
		}
		
	}
	
	private List<String> convertContent(String content) {
		
		content = removeComments(content);
		content = removeWhiteSpace(content);
		
		return extractLines(content);
		
	}

	private List<String> extractLines(String content) {
		
		List<String> lines = new LinkedList<>();
		
		while(!content.isEmpty()) {
			
			int semicolon = content.indexOf(";");
			
			if(semicolon == -1) {
				
				if(!content.isBlank()) {
					lines.add(content);
				}
				
				content = "";
				
			} else {
				
				lines.add(content.substring(0, semicolon).trim());
				content = content.substring(semicolon+1);
				
			}
			
		}
		
		return lines;
	}

	private String removeWhiteSpace(String content) {
		return content.replaceAll("\\s+", " ");
	}

	private String removeComments(String content) {
		
		StringBuilder s = new StringBuilder(content);
		int commentPos = 0;
		
		while((commentPos = s.indexOf("-- ", commentPos)) != -1) {
			
			int eolPos = s.indexOf("\n", commentPos +1);
			
			if(eolPos == -1) {
				
				s.replace(commentPos, s.length(), "");
				
			} else {
				
				s.replace(commentPos, eolPos+1, "");
				
			}
			
		}
		
		return s.toString();
		
	}

	public List<Unit> fetchUnits() {
		return recipeDao.getAllUnits();
	}

	public void addIngredient(Ingredient i) {
		
		recipeDao.addIngredient(i);
		
	}

	public void addStep(Step s) {
		
		recipeDao.addStep(s);
		
	}

	public List<Category> fetchCategories() {
		return recipeDao.fetchCategories();
	}

	public void addCategory(Integer recipe_id, String category) {
		recipeDao.addCategory(recipe_id, category);
	}

	public List<Step> fetchSteps(Integer recipe_id) {
		return recipeDao.fetchSteps(recipe_id);
	}

	public void modifyStep(Step s) {
		if(!recipeDao.modifyStep(s)) {
			throw new DbException("Step with id " + s.getStepId() + " does not exist");
		}
	}

	public String deleteRecipe(Integer id) {
		if(!recipeDao.deleteRecipe(id)) {
			throw new DbException("Recipe with id " + id + " does not exist");
		} else {
			return "Recipe deleted!";
		}
	}
	
}
