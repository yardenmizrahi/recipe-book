package model;

import java.util.Objects;

public class Ingredient {
	private int ingredientId;	
	private String name;
	
	public Ingredient(int initialID) {
		ingredientId = initialID;
	}

	public Ingredient(int initialID, String name) throws Exception {
		ingredientId = initialID;
		setName(name);
	}

	public int getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(int ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getName() {
		return name;
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
	
	public void setName(String name) throws Exception {
		if (checkName(name))
			this.name = name;
	}


	public boolean equals(Object obj) {
		if (!(obj instanceof Ingredient))
			return false;
		Ingredient tempIng = (Ingredient)obj;
		if (ingredientId == tempIng.getIngredientId())
				return true;
		return false;
	}

	@Override
	public String toString() {
		return "Ingredient [ingredientId=" + ingredientId + ", name=" + name + "]";
	}
	
	

}
