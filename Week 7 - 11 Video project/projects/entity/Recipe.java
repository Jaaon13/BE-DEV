package projects.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Recipe {
	
	private Integer recipe_id;
	private String recipeName;
	private String notes;
	private Integer numServings;
	private LocalTime prepTime;
	private LocalTime cookTime;
	private LocalDateTime createdAt;
	
	private List<Ingredient> ingredients = new LinkedList<>();
	private List<Step> step = new LinkedList<>();
	private List<Category> category = new LinkedList<>();
	
	public String toString() {
		
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		//@SuppressWarnings("unused")
		String createTime = Objects.nonNull(createdAt) ? fmt.format(createdAt) : "(null)";
		
		String recipe = "";
		
		recipe += "\n   id=" + recipe_id;
		recipe += "\n   recipe name=" + recipeName;
		recipe += "\n   notes=" + notes;
		recipe += "\n   num servings=" + numServings;
		recipe += "\n   prep time=" + prepTime;
		recipe += "\n   cook time=" + cookTime;
		recipe += "\n   created at=" + createTime;
		
		recipe += "\n   Ingredients: ";
		
		for(Ingredient i : ingredients) {
			recipe += "\n    " + i;
		}
		
		recipe += "\n   Steps: ";
		
		for(Step s : step) {
			recipe += "\n    " + s;
		}
		
		recipe += "\n   Categories: ";
		
		for(Category c : category) {
			recipe += "\n    " + c;
		}
		
		return recipe;
		
	}
	
	public Integer getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(Integer recipe_id) {
		this.recipe_id = recipe_id;
	}
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String name) {
		this.recipeName = name;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Integer getNumServings() {
		return numServings;
	}
	public void setNumServings(Integer numServings) {
		this.numServings = numServings;
	}
	public LocalTime getPrepTime() {
		return prepTime;
	}
	public void setPrepTime(LocalTime prepTime) {
		this.prepTime = prepTime;
	}
	public LocalTime getCookTime() {
		return cookTime;
	}
	public void setCookTime(LocalTime cookTime) {
		this.cookTime = cookTime;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public List<Step> getStep() {
		return step;
	}
	public List<Category> getCategory() {
		return category;
	}
	
	
	
}
