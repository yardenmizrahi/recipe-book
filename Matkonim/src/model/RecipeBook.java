package model;

import java.sql.SQLException;
import java.util.ArrayList;

import mvc.BLeventsListener;
import sql.sql;

public class RecipeBook {
	private sql recipeBookHandler;
	private UserAccount currentUser;

	private BLeventsListener myListener;

	public RecipeBook() throws ClassNotFoundException, SQLException  {
		recipeBookHandler = sql.getInstance();
		currentUser = getCurrentUser();

	}

	public ArrayList<Recipe> getMyRecipesFromDb() throws Exception  {
		return recipeBookHandler.getMyRecipes(recipeBookHandler.getConnection(), currentUser);
	}

	public ArrayList<Recipe> getAllRecipesFromDb() throws Exception  {
		return recipeBookHandler.getAllRecipes(recipeBookHandler.getConnection());
	}

	public ArrayList<Ingredient> getAllIngredientsFromDb() throws Exception {
		return this.recipeBookHandler.getAllIngredients(this.recipeBookHandler.getConnection());
	}

	public ArrayList<Category> getAllCategorysFromDb() throws Exception {
		return this.recipeBookHandler.getCategorys(this.recipeBookHandler.getConnection());
	}

	public Recipe createRecipe(String name, int cookingTime, String description, ArrayList<Ingredient> allIngredients, 
			ArrayList<Integer> amount, ArrayList<AmountType> amountType ,ArrayList<Category> recipeCategorys) throws SQLException {
		Recipe r = this.recipeBookHandler.createRecipe(this.recipeBookHandler.getConnection(), currentUser, 
				name, cookingTime, description, allIngredients, amount, amountType, recipeCategorys);
		fireUpdateRecipeInformation();
		return r;
	}

	public Ingredient createIngredient(String ingName) throws Exception {
		return this.recipeBookHandler.addIngridient(this.recipeBookHandler.getConnection(), ingName);
	}

	public Category createCategory(String catName) throws Exception {
		return this.recipeBookHandler.addCategoryGeneral(this.recipeBookHandler.getConnection(), catName);
	}

	public void addRecipeToFav(int recipeId) throws SQLException {
		this.recipeBookHandler.addRecipeToFavorite(this.recipeBookHandler.getConnection(), currentUser, recipeId);
	}

	public void removeRecipeFromFav(int recipeId) throws SQLException {
		this.recipeBookHandler.removeRecipeFromFavorite(this.recipeBookHandler.getConnection(), recipeId);
	}

	public ArrayList<Recipe> getRecipesWithSpesificIngredients(ArrayList<Ingredient> tempIngredients) throws Exception {
		//return this.recipeBookHandler.getAllRecipesWithSpesificIngredients(this.recipeBookHandler.connection, tempIngredients);
		ArrayList<Recipe> allRec = this.recipeBookHandler.getAllRecipes(this.recipeBookHandler.getConnection());
		ArrayList<Recipe> relevantRecipies = new ArrayList<Recipe>();
		for (Recipe tempRecipe : allRec) {
			int tempCount = 0;
			for (Ingredient tempIng : tempIngredients) {
				if(tempRecipe.getAllIngredients().contains(tempIng)) {
					tempCount++;}
			}
			if(!relevantRecipies.contains(tempRecipe) && tempCount == (tempIngredients.size())) //size -1
				relevantRecipies.add(tempRecipe);
		}
		return relevantRecipies;
	}

	public ArrayList<Recipe> getUserFavoritesRecipes() throws Exception {
		return this.recipeBookHandler.getAllFavoriteRecipes(this.recipeBookHandler.getConnection(), currentUser);
	}

	public void removeRecipeFromDB(int recipeId) throws SQLException { //TODO DELETE FROM ARRAY LIST HERE
		this.recipeBookHandler.removeRecipe(this.recipeBookHandler.getConnection(), recipeId);
		fireUpdateRecipeInformation();
	}

	public void updateRecipe(int recipeID, String recipeName, int cookingTime, String description) throws SQLException {
		this.recipeBookHandler.updateRecipe(this.recipeBookHandler.getConnection(), recipeID, recipeName, cookingTime, description);
		fireUpdateRecipeInformation();
	}

	public Picture addPicture(int recipeID, String title, String author, String description) throws SQLException {
		Picture newPic =  this.recipeBookHandler.addPicture(this.recipeBookHandler.getConnection(), recipeID, title, author, description);
		//		Recipe r = findRecipeById(recipeID);
		//		newPic.setRecipe(r);
		fireUpdateRecipeInformation();
		return newPic;
	}


	public UserAccount getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(int currentUserId) throws SQLException {
		UserAccount ua = this.recipeBookHandler.getCurrentUserById(this.recipeBookHandler.getConnection(), currentUserId);
		this.currentUser = ua;
	}

	public BLeventsListener getMyListener() {
		return myListener;
	}

	public void setMyListener(BLeventsListener myListener) {
		this.myListener = myListener;
	}

	@Override
	public String toString() {
		return "RecipeBook [recipeBookHandler="
				+ recipeBookHandler + ", currentUser=" + currentUser + ", myListener=" + myListener + "]";
	}

	public void fireUpdateRecipeInformation() {
		myListener.updateMyRecipesData();
	}

	public void registerListener(BLeventsListener myListener) {
		this.myListener = myListener;
	}

}
