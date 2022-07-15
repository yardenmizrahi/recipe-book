package model;

import java.util.ArrayList;

public class Category {
	private int categoryId;
	//private static int countCategoryId = 1;
	private String categoryName;
	private ArrayList<Recipe> allRecipes;
	
	public Category(int categoryId) {
		this.categoryId = categoryId;
		allRecipes = new ArrayList<Recipe>();
		
	}

	public Category(int categoryId, String categoryName) throws Exception {
		this.categoryId = categoryId;
		setCategoryName(categoryName);
		allRecipes = new ArrayList<Recipe>();
	}
	
	private boolean checkName(String name) throws Exception {
		String goodChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		if (name.length() <= 0) {
			throw new Exception("Error name is null");
		}
		if (name.charAt(0) == ' ' || name.charAt(name.length() - 1) == ' ') {
			throw new Exception("Name cant strat or end with ' ' (space)");
		}
		boolean spaceFlag = false;
		for (int i = 0; i < name.length(); i++) {
			spaceFlag = false;
			if (!(goodChars.contains("" + name.charAt(i)))) {
				//if this is not an English letter
				if (name.charAt(i) == ' ') {//check if its space
					if (name.charAt((i+1)) == ' ') { //if space check no double spaces
						//we know this is not the last tab because we checked that its not ending with space
						throw new Exception("Name cant have two ' ',' ' (spaces) one after another");
					}
					else
						spaceFlag = true; //we have a space here
				}
				else {
					if (spaceFlag == false) {
						//not space and not English letter
						throw new Exception("Name can only have English letters and spaces");
					}
				}
			}
		}
		return true;
		//add lower case		
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) throws Exception {
		if(checkName(categoryName))
			this.categoryName = categoryName;
	}

	public ArrayList<Recipe> getAllRecipes() {
		return allRecipes;
	}

	public void setAllRecipes(ArrayList<Recipe> allRecipes) {
		this.allRecipes = allRecipes;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + ", allRecipes=" + allRecipes
				+ "]";
	}
	

}
