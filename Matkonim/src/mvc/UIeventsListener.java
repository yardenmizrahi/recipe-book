package mvc;

import java.sql.SQLException;
import java.util.ArrayList;

import model.AmountType;
import model.Category;
import model.Ingredient;
import model.Recipe;
 
public interface UIeventsListener {
	public ArrayList<Ingredient> showAllIngredientsToView() throws Exception;
	public ArrayList<Recipe> showAllRecipesToView() throws Exception;
	//public ArrayList<Recipe> showARecipeToView(Recipe tempRec);
	public ArrayList<Recipe> showAllRecipesToViewThatHave(ArrayList<Ingredient> tempIngredients) throws Exception; //run till null TODO
	public ArrayList<Category> showAllCategorysToView() throws Exception;
	
	public boolean addRecipeToUserFavorite(int recipeId) throws SQLException; //user - current user
	public boolean removeRecipeFromUserFavorite(int recipeID) throws SQLException;
	public boolean addRecipeToDB(String name, int cookingTime, String description, ArrayList<Ingredient> allIngredients, 
			ArrayList<Integer> amount, ArrayList<AmountType> amountType ,ArrayList<Category> recipeCategorys) throws SQLException;
	public boolean addIngredientToDB(String ingName) throws Exception;
	public boolean addCategoryToDB(String catName) throws Exception;
	
	public ArrayList<Recipe> showAllUserFavoriteRecipesToView() throws Exception; // uses current user id
	public ArrayList<Recipe> showAllMyRecipesToView() throws Exception; //current user
	
	public boolean updateRecipe(int recipeID, String recipeName, int cookingTime, String description) throws SQLException;
	public boolean removeRecipe(int recipeID) throws SQLException;
	
	public boolean addPicture(int recipeID, String title, String author, String description) throws SQLException;
	
}
