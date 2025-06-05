package projects;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Category;
import projects.entity.Ingredient;
import projects.entity.Recipe;
import projects.entity.Step;
import projects.entity.Unit;
import projects.exception.DbException;
import projects.service.RecipeService;

public class app {
	
	private Scanner s = new Scanner(System.in);
	private RecipeService rp = new RecipeService();
	private Recipe currecipe;
	
	//@formatter:off
	private List<String> operations = List.of(
			"1) Create and populate all tables",
			"2) Add a Recipe",
			"3) List Recipes",
			"4) Select a Recipe",
			"5) Add Ingredient to Current Recipe",
			"6) Add Step to Current Recipe",
			"7) Add Category to Current Recipe",
			"8) Modify Step in Current Recipe",
			"9) Delete Recipe"
			);
	//@formatter:on
	
	public static void main(String[] args) {
		
		new app().displayMenu();

	}
	
	private void displayMenu() {
		
		boolean done = false;
		
		while(!done) {
			
			try {
				int sel = getOperation();
				
				switch(sel) {
				case -1:
					done = exitMenu();
					break;
				
				case 1:
					createTables();
					break;
					
				case 2:
					addRecipe();
					break;
					
				case 3:
					listRecipes();
					break;
					
				case 4:
					selectRecipe();					
					break;
					
				case 5:
					addIngredient();
					break;
					
				case 6:
					addStep();
					break;
					
				case 7:
					addCategory();
					break;
					
				case 8:
					updateStep();
					break;
					
				case 9:
					deleteRecipe();
					break;
					
				default:
					System.out.println("\n" + sel + " is not valid try again!");
					break;
				}
			} catch (Exception e) {
				System.out.println("\nError: " + e.toString() + "\nTry Again");
			}
			
		}
		
	}
	
	private void deleteRecipe() {
		
		listRecipes();
		
		Integer Id = getIntInput("Enter the Recipe ID to Delete");
		
		if(Objects.nonNull(Id)) {
			
			System.out.println(rp.deleteRecipe(Id));
			
			if(Objects.nonNull(currecipe) && currecipe.getRecipe_id().equals(Id)) {
				currecipe = null;
			}
			
		}
		
	}

	private void updateStep() {
		
		if(Objects.isNull(currecipe)) {
			System.out.println("\nSelect Recipe First!");
			return;
		}
		
		List<Step> s = rp.fetchSteps(currecipe.getRecipe_id());
		
		System.out.println("\nSteps in Current Recipe");
		
		s.forEach(step -> System.out.println(step));
		
		Integer stepId = getIntInput("Enter Id of Step to Modify");
		
		if(Objects.nonNull(stepId)) {
			
			String stepText = getStringInput("Enter New Step Text: ");
			
			if(Objects.nonNull(stepText)) {
				
				Step ns = new Step();
				
				ns.setStepId(stepId);
				ns.setStepText(stepText);
				
				rp.modifyStep(ns);
				
				currecipe = rp.fetchRecipeByID(currecipe.getRecipe_id());
				
			}
			
		}
		
	}

	private void addCategory() {
		
		if(Objects.isNull(currecipe)) {
			System.out.println("\nSelect Recipe First!");
			return;
		}
		
		List<Category> c = rp.fetchCategories();
		
		c.forEach(cs -> System.out.println(cs.getCategoryName()));
		
		String category = getStringInput("Enter Category To Add");
		
		if(Objects.nonNull(category)) {
			rp.addCategory(currecipe.getRecipe_id(), category);
			currecipe = rp.fetchRecipeByID(currecipe.getRecipe_id());
		}
		
	}

	private void addStep() {
		
		if(Objects.isNull(currecipe)) {
			System.out.println("\nSelect Recipe First!");
			return;
		}
		
		String stepText = getStringInput("Enter step text:");
		
		if(Objects.nonNull(stepText)) {
			
			Step s = new Step();
			
			s.setRecipeId(currecipe.getRecipe_id());
			s.setStepText(stepText);
			
			rp.addStep(s);
			
			currecipe = rp.fetchRecipeByID(s.getRecipeId());
			
		}
		
	}

	private void addIngredient() {
		
		if(Objects.isNull(currecipe)) {
			System.out.println("\nSelect Recipe First!");
			return;
		}
		
		String name = getStringInput("Enter the ingredient name: ");
		String instruction = getStringInput("Enter the instruction (if any): ");
		Double inpamount = getDoubleInput("Enter the ingredient amount: ");
		
		List<Unit> units = rp.fetchUnits();
		
		BigDecimal amount = Objects.isNull(inpamount) ? null : new BigDecimal(inpamount).setScale(2);
		
		System.out.println("Units:");
		
		units.forEach(unit -> System.out.println(unit.getUnitId() + ". "
		+ unit.getUnitNameSingular() + " (" + unit.getUnitNamePlural() + ")"));
		
		Integer unitId = getIntInput("Enter a unit ID (Enter to escape): ");
		
		Unit u = new Unit();
		u.setUnitId(unitId);
		
		Ingredient i = new Ingredient();
		i.setRecipeId(currecipe.getRecipe_id());
		i.setUnit(u);
		i.setIngredientName(name);
		i.setInstruction(instruction);
		i.setAmount(amount);
		
		rp.addIngredient(i);
		
		currecipe = rp.fetchRecipeByID(i.getRecipeId());
		
	}

	private void selectRecipe() {
		
		List<Recipe> r = listRecipes();
		
		Integer id = getIntInput("Select a Recipe Id");
		
		currecipe = null;
		
		for(Recipe re : r) {
			if(re.getRecipe_id().equals(id)) {
				currecipe = rp.fetchRecipeByID(id);
				break;
			}
		}
		
		if(Objects.isNull(currecipe)) {
			System.out.println("Error / Invalid id");
		} else {
			String s = currecipe.toString();
			System.out.println(s);
		}
		
	}

	private List<Recipe> listRecipes() {
		
		 List<Recipe> r = rp.fetchRecipes();
		
		System.out.println("\nRecipes");
		
		r.forEach(rd -> System.out.println("" + rd.getRecipe_id() + ": " + rd.getRecipeName()));
		
		return r;
		
	}

	private void addRecipe() {
		
		String name = getStringInput("Enter Recipe Name: ");
		String notes = getStringInput("Enter Recipe Notes: ");
		Integer numofServing = getIntInput("Enter Number of Servings: ");
		Integer prepMin = getIntInput("Enter Prep Time(minutes):");
		Integer cookMin = getIntInput("Enter Cook Time(minutes):");
		
		LocalTime prepTime = minToLocalTime(prepMin);
		LocalTime cookTime = minToLocalTime(cookMin);
		
		Recipe recipe = new Recipe();
		
		recipe.setRecipeName(name);
		recipe.setNotes(notes);
		recipe.setNumServings(numofServing);
		recipe.setPrepTime(prepTime);
		recipe.setCookTime(cookTime);
		
		Recipe dbR = rp.addRecipe(recipe);
		System.out.println("You added this recipe:\n"+dbR);
		
		currecipe = rp.fetchRecipeByID(dbR.getRecipe_id());
		
	}

	private LocalTime minToLocalTime(Integer numMin) {
		
		int min = Objects.isNull(numMin) ? 0 : numMin;
		int hours = min/60;
		int minutes = min&60;
		
		return LocalTime.of(hours, minutes);
		
	}

	private void createTables() {
		
		rp.createAndPopulateTables();
		System.out.println("\nTables Created");
		
	}

	private boolean exitMenu() {
		System.out.println("\n\nQuiting...");
		return true;
	}

	private int getOperation() {
		
		printOperations();
		
		Integer sel = getIntInput("Enter a selection (Enter to quit):");
		
		return Objects.isNull(sel) ? -1 : sel;
		
	}
	
	private void printOperations() {
		
		System.out.println("\nOptions:");
		
		operations.forEach(op -> System.out.println(op));
		
	}
	
	private Integer getIntInput(String prompt) {
		
		String in = getStringInput(prompt);
		
		if(Objects.isNull(in)) {
			return null;
		}
		
		try {
			return Integer.parseInt(in);
		} catch (NumberFormatException e) {
			throw new DbException(in + " is not a valid number");
		}
		
	}
	
	private Double getDoubleInput(String prompt) {
		
		String in = getStringInput(prompt);
		
		if(Objects.isNull(in)) {
			return null;
		}
		
		try {
			return Double.parseDouble(in);
		} catch (NumberFormatException e) {
			throw new DbException(in + " is not a valid number");
		}
		
	}

	private String getStringInput(String prompt) {
		
		System.out.println(prompt);
		
		String line = s.nextLine();
		
		return line.isBlank() ? null : line.trim();
		
	}


}
