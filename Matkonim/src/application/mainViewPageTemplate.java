package application;

import java.util.ArrayList;

import model.Recipe;

public interface mainViewPageTemplate {
	public void openShowAllRecipeBy(ArrayList<Recipe> allMyRecipes, boolean flag);
	public void openEditMyRecipe(Recipe tempRec);
	public void openAddPicture(Recipe tempRec);
	public void successCloseMe(String msg, int side);
	public void successMsg(String msg);
}
