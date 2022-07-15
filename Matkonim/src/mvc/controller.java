package mvc;

import java.sql.SQLException;
import java.util.ArrayList;

import application.MyMainView;
import javafx.stage.Stage;
import model.AmountType;
import model.Category;
import model.Ingredient;
import model.Recipe;
import model.RecipeBook;

public class controller implements UIeventsListener, BLeventsListener {
	private RecipeBook model;
	private MyMainView view;
	private int currentUserID;
	
	public controller(RecipeBook model, MyMainView view, int currentUserId) throws SQLException {
		this.model = model;
		this.view = view;
		view.registerListener(this);
		model.registerListener(this);
		this.currentUserID = currentUserId;
		model.setCurrentUser(currentUserId);
		System.out.println("" + model.getCurrentUser().getFirstName());
		view.setLBLUA("" + model.getCurrentUser().getFirstName());
	}

	
	@Override
	public ArrayList<Ingredient> showAllIngredientsToView() throws Exception {
		return model.getAllIngredientsFromDb();
	}

	@Override
	public ArrayList<Recipe> showAllMyRecipesToView() throws Exception {
		return model.getMyRecipesFromDb();
	}
	@Override
	public ArrayList<Recipe> showAllRecipesToView() throws Exception {
		return model.getAllRecipesFromDb();
	}

	@Override
	public ArrayList<Category> showAllCategorysToView() throws Exception {
		return model.getAllCategorysFromDb();
	}

	@Override
	public boolean addRecipeToUserFavorite(int recipeId) throws SQLException {
		model.addRecipeToFav(recipeId);
		return true;
	}

	@Override
	public boolean addIngredientToDB(String ingName) throws Exception {
		model.createIngredient(ingName);
		return true;
	}

	@Override
	public boolean addCategoryToDB(String catName) throws Exception {
		model.createCategory(catName);
		return true;
	}

	@Override
	public boolean removeRecipeFromUserFavorite(int recipeID) throws SQLException {
		model.removeRecipeFromFav(recipeID);
		return true;
	}

	@Override
	public boolean addRecipeToDB(String name, int cookingTime, String description,
			ArrayList<Ingredient> allIngredients, ArrayList<Integer> amount, ArrayList<AmountType> amountType,
			ArrayList<Category> recipeCategorys) throws SQLException {
		model.createRecipe(name, cookingTime, description, allIngredients, amount, amountType, recipeCategorys);
		return true;
	}

	@Override
	public ArrayList<Recipe> showAllRecipesToViewThatHave(ArrayList<Ingredient> tempIngredients) throws Exception {
		return model.getRecipesWithSpesificIngredients(tempIngredients);
	}

	@Override
	public ArrayList<Recipe> showAllUserFavoriteRecipesToView() throws Exception {
		return model.getUserFavoritesRecipes();
	}


	@Override
	public boolean updateRecipe(int recipeID, String recipeName, int cookingTime, String description) throws SQLException {
		model.updateRecipe(recipeID, recipeName, cookingTime, description);
		return true;
	}


	@Override
	public boolean removeRecipe(int recipeID) throws SQLException {
		model.removeRecipeFromDB(recipeID);
		return true;
	}


	@Override
	public boolean addPicture(int recipeID, String title, String author, String description) throws SQLException {
		model.addPicture(recipeID, title, author, description);
		return true;
	}


	@Override
	public void updateMyRecipesData() {
		view.setNewMyRecipeData();
	}
	
	

}
