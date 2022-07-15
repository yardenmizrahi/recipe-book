package model;

import java.util.ArrayList;
import java.util.Objects;

public class Recipe {
	private int recipeId;
	//C:\Users\User\AppData\Local\SceneBuilder\
	private String name;
	private int cookingTime;
	private String description;
	
	private UserAccount ua;
	private ArrayList<Category> recipeCategorys;
	private ArrayList<Ingredient> allIngredients;
	private ArrayList<Picture> allPictures;
	
	
	public Recipe(int recipeId, String name, int cookingTime, String description) {
		this.recipeId = recipeId;
		this.name = name;
		this.cookingTime = cookingTime;
		this.description = description;
		allIngredients = new ArrayList<>();
		recipeCategorys = new ArrayList<Category>();
		allPictures = new ArrayList<Picture>();
	}

	public Recipe(int recipeId, String name, int cookingTime, String description, UserAccount ua) {
		this.recipeId = recipeId;
		this.name = name;
		this.cookingTime = cookingTime;
		this.description = description;
		this.ua = ua;
		allIngredients = new ArrayList<Ingredient>();
		recipeCategorys = new ArrayList<Category>();
		allPictures = new ArrayList<Picture>();
	}

	public int getRecipeId() {
		return recipeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserAccount getUa() {
		return ua;
	}

	public void setUser(UserAccount ua) {
		this.ua = ua;
	}
	public void setUa(int user) {
		this.ua = ua;
	}

	public ArrayList<Category> getRecipeCategorys() {
		return recipeCategorys;
	}

	public void setRecipeCategorys(ArrayList<Category> recipeCategorys) {
		this.recipeCategorys = recipeCategorys;
	}

	public ArrayList<Ingredient> getAllIngredients() {
		return allIngredients;
	}

	public void setAllIngredients(ArrayList<Ingredient> allIngredients) {
		this.allIngredients = allIngredients;
	}
	
	
	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
	

	public ArrayList<Picture> getAllPictures() {
		return allPictures;
	}

	public void setAllPictures(ArrayList<Picture> allPictures) {
		this.allPictures = allPictures;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Recipe))
			return false;
		Recipe tempRec = (Recipe)obj;
		if (recipeId == tempRec.getRecipeId())
				return true;
		return false;
	}


	/*public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		if (recipeId != other.recipeId)
			return false;
		return true;
	}*/

	@Override
	public String toString() {
		return "Recipe [recipeId=" + recipeId + ", name=" + name + ", cookingTime=" + cookingTime + ", description="
				+ description + ", ua=" + ua + ", recipeCategorys=" + recipeCategorys + ", allIngredients="
				+ allIngredients + "]";
	}
	
	
	
}
