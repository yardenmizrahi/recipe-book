package sql;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import model.AmountType;
import model.Category;
import model.Ingredient;
import model.Picture;
import model.Recipe;
import model.UserAccount;

public class sql {
	private Connection connection = null;
	private static sql _instance = null; //static attribute

	private sql() throws SQLException, ClassNotFoundException { //private constructor
		Class.forName("com.mysql.cj.jdbc.Driver");
		String dbUrl = "jdbc:mysql://localhost:3306/my_recipe_book";
		connection = DriverManager.getConnection(dbUrl, "root", "");

	}
	
	public static sql getInstance() throws ClassNotFoundException, SQLException { //return the static attribute
		if(_instance == null)
			_instance = new sql();
		return _instance;
	}

	public Connection getConnection() {
		return connection;
	}


	private static final String CREATE_RECIPE = "INSERT INTO recipe (recipe_ID, recipe_name, cooking_time, recipe_description, "
			+ "user_ID) values (?,?,?,?,?)";
	private static final String CREATE_INGREDIENT = "INSERT INTO ingredient (ingredient_ID, ingredient_name) values (?,?)";
	private static final String DELETE_RECIPE = "DELETE FROM recipe WHERE recipe_ID = ?";
	private static final String DELETE_INGREDIENTS = "DELETE FROM ingredients NATURAL JOIN recipe WHERE recipe_name = ?";
	private static final String QUERY_RECIPES = "SELECT recipe_ID, recipe_name, cooking_time, recipe_description FROM recipe";
	private static final String QUERY_RECIPES_THIS_USER = "SELECT recipe_ID, recipe_name, cooking_time, recipe_description FROM recipe WHERE user_ID = ?";
	private static final String QUERY_SPECIFIC_RECIPE = "SELECT recipe_ID, recipe_name, cooking_time, recipe_description FROM recipe "
			+ "WHERE recipe_name = ?";
	private static final String QUERY_INGREDIENT_SPECIFIC_RECIPE = "SELECT ingredient_ID, ingredient_name "
			+ "FROM ingredient NATURAL JOIN recipe_ingredient NATURAL JOIN recipe WHERE recipe_ID = ?";
	private static final String QUERY_INGREDIENT = "SELECT ingredient_ID, ingredient_name FROM ingredient";
	private static final String UPDATE_RECIPE_NAME = "UPDATE recipe SET recipe_name = ? WHERE recipe_ID = ?";
	private static final String UPDATE_RECIPE_COOKING_TIME = "UPDATE recipe SET cooking_time = ? WHERE recipe_ID = ?";
	private static final String UPDATE_RECIPE_DESCRIPTION = "UPDATE recipe SET recipe_description = ? WHERE recipe_ID = ?";

	private static final String QUERY_ACCOUNT = "SELECT userID, username, fName, lname, email FROM user_account";
	private static final String INSERT_ACCOUNT = "INSERT INTO user_account (userID, username, fName, lName, email) values (?,?,?,?,?)";
	private static final String QUERY_ACCOUNT_SPESIFIC = "SELECT userID, username, fName, lName, password, email FROM user_account WHERE userID = ?";

	private static final String QUERY_CATEGORY = "SELECT category_ID, category_name FROM category";
	private static final String INSERT_CATAGORY = "INSERT INTO category (category_ID, category_name) values (?,?)";
	private static final String INSERT_CATEGORY_TO_RC = "INSERT INTO recipe_category (recipe_ID, category_ID) values (?,?)";

	private static final String INSERT_PICTURE = "INSERT INTO picture (picture_ID, picture_title, picture_description, picture_author, "
			+ "recipe_ID) values (?,?,?,?,?)";
	private static final String MAX_INGREDIENT = "SELECT MAX(ingredient_ID) as max_number FROM ingredient";
	private static final String MAX_CATEGORY = "SELECT MAX(category_ID) as max_number FROM category";
	private static final String MAX_RECIPE = "SELECT MAX(recipe_ID) as max_number FROM recipe";
	private static final String MAX_PICTURE = "SELECT MAX(picture_ID) as max_number FROM picture";

	//private static final String INSERT_PICTURE_TO_RP = "INSERT INTO recipe_picture (recipe_ID, picture_ID) values (?,?)";
	//private static final String INSERT_ACCOUNT_TO_RU = "INSERT INTO recipe_useraccount (recipe_ID, userID) values (?,?)";
	//private static final String DELETE_ACCOUNT_FROM_RU = "DELETE FROM recipe_useraccount NATURAL JOIN recipe WHERE recipe_name = ?";
	//private static final String DELETE_INGREDIENTS_FROM_RI = "DELETE FROM recipe_ingredient NATURAL JOIN recipe  WHERE recipe_name = ?";


	private static final String CREATE_INGREDIENT_RECIPE = "INSERT INTO recipe_ingredient (recipe_ID, ingredient_ID, ingredient_amount, ingredient_amountType) values (?,?,?,?)";
	private static final String INSERT_USER_FAVORITS = "INSERT INTO user_favorite_recipes (recipe_ID, userID) values (?,?)";
	private static final String REMOVE_USER_FAVORITS = "DELETE FROM user_favorite_recipes WHERE recipe_ID = ?";
	private static final String QUERY_RECIPE_SPESIFIC_ING = "SELECT recipe.recipe_ID, recipe.recipe_name, recipe.cooking_time,"
			+ " recipe.recipe_description FROM recipe NATURAL JOIN recipe_ingredient WHERE ingredient_ID = ?";
	private static final String QUERY_RECIPE_FAVORITES = "SELECT recipe.recipe_ID, recipe.recipe_name, recipe.cooking_time,"
			+ " recipe.recipe_description FROM recipe NATURAL JOIN user_favorite_recipes WHERE userID = ?";
	private static final String CHECK_INGREDIENTS_NAME = "SELECT ingredient_name FROM ingredient WHERE ingredient_name = ?";
	private static final String CHECK_CATEGORY_NAME = "SELECT category_name FROM category WHERE category_name = ?";
	private static final String QUERY_RECIPE_CATEGORY = "SELECT category_ID, category_name FROM category NATURAL JOIN recipe_category WHERE recipe_ID = ?";
	private static final String DELETE_RECIPE_INGREDIENT = "DELETE FROM recipe_ingredient WHERE recipe_ID = ?";
	private static final String DELETE_RECIPE_CATEGORY = "DELETE FROM recipe_category WHERE recipe_ID = ?";
	private static final String DELETE_RECIPE_USER_FAVORITE = "DELETE FROM user_favorite_recipes WHERE recipe_ID = ?";
	private static final String QUERY_ALL_PICTURE = "SELECT picture_ID, picture_title, picture_description, "
			+ "picture_author FROM picture";
	private static final String QUERY_PICTURE_RECIPE = "SELECT picture_ID, picture_title, picture_description, picture_author FROM picture WHERE recipe_ID = ?";
	private static final String DELETE_PICTURE_RECIPE = "DELETE FROM picture WHERE recipe_ID = ?";

	public ArrayList<Recipe> getMyRecipes(Connection conn, UserAccount ua) throws Exception {
		ArrayList<Recipe> lr = new ArrayList<>();
		PreparedStatement prepStmt = conn.prepareStatement(QUERY_RECIPES_THIS_USER);
		prepStmt.setInt(1, ua.getUserId());
		ResultSet rs = prepStmt.executeQuery();
		Recipe recipe;
		while(rs.next()) {
			recipe = new Recipe(rs.getInt("recipe_id"), rs.getString("recipe_name"), rs.getInt("cooking_time"), rs.getString("recipe_description"), ua);

			PreparedStatement prepStmt2 = conn.prepareStatement(QUERY_INGREDIENT_SPECIFIC_RECIPE);
			prepStmt2.setInt(1, recipe.getRecipeId());
			ResultSet rs2 = prepStmt2.executeQuery();
			ArrayList<Ingredient> allIngredients = new ArrayList<Ingredient>();
			while(rs2.next()) {
				Ingredient temp = new Ingredient(rs2.getInt("ingredient_ID"), rs2.getString("ingredient_name"));
				allIngredients.add(temp);
			}
			recipe.setAllIngredients(allIngredients);

			PreparedStatement prepStmt3 = conn.prepareStatement(QUERY_RECIPE_CATEGORY);
			prepStmt3.setInt(1, recipe.getRecipeId());
			ResultSet rs3 = prepStmt3.executeQuery();
			ArrayList<Category> allCategorys = new ArrayList<Category>();
			while(rs3.next()) {
				Category temp = new Category(rs3.getInt("category_ID"), rs3.getString("category_name"));
				allCategorys.add(temp);
			}
			recipe.setRecipeCategorys(allCategorys);
			
			PreparedStatement prepStmt4 = conn.prepareStatement(QUERY_PICTURE_RECIPE);
			prepStmt4.setInt(1, recipe.getRecipeId());
			ResultSet rs4 = prepStmt4.executeQuery();
			ArrayList<Picture> allPictures = new ArrayList<Picture>();
			while(rs4.next()) {
				Picture temp = new Picture(rs4.getInt("picture_ID"), rs4.getString("picture_title"), rs4.getString("picture_author"), rs4.getString("picture_description"));
				temp.setRecipe(recipe);
				allPictures.add(temp);
			}
			recipe.setAllPictures(allPictures);
			

			lr.add(recipe);
		}
		return lr;
	}

	public ArrayList<Ingredient> getAllIngredients(Connection conn) throws Exception {
		ArrayList<Ingredient> li = new ArrayList<>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(QUERY_INGREDIENT);
		Ingredient ing;
		while(rs.next()) {
			ing = new Ingredient(rs.getInt("ingredient_ID"), rs.getString("ingredient_name"));
			//ing.setIngredientId(rs.getInt("ingredient_ID"));
			//ing.setName(rs.getString("ingredient_name"));

			li.add(ing);
		}
		return li;
	}

	public ArrayList<Category> getCategorys(Connection conn) throws Exception {
		ArrayList<Category> lc = new ArrayList<>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(QUERY_CATEGORY);
		Category c;
		while(rs.next()) {
			c = new Category(rs.getInt("category_ID"), rs.getString("category_name"));
			//c.setCategoryId(rs.getInt("category_ID"));
			//c.setCategoryName(rs.getString("category_name"));
			lc.add(c);
		}
		return lc;
	}

	public Recipe createRecipe(Connection con, UserAccount ua, String name, int cookingTime, 
			String description, ArrayList<Ingredient> allIngredients, ArrayList<Integer> amount, 
			ArrayList<AmountType> amountType, ArrayList<Category> recipeCategorys) throws SQLException {
		int id = getMaxRecipeId(con);
		PreparedStatement createRecipe = con.prepareStatement(CREATE_RECIPE);
		createRecipe.setInt(1, (id+1));
		createRecipe.setString(2, name);
		createRecipe.setInt(3, cookingTime);
		createRecipe.setString(4, description);
		createRecipe.setInt(5,ua.getUserId());
		Recipe r = new Recipe((id+1), name, cookingTime, description, ua);
		createRecipe.executeUpdate();

		setupIngridients(con, allIngredients, amount, amountType, r);
		setupCategorys(con, recipeCategorys, r);
		r.setAllIngredients(allIngredients);
		r.setRecipeCategorys(recipeCategorys);

		return r;
	}

	public ArrayList<Ingredient> setupIngridients(Connection conn, ArrayList<Ingredient> allIngredients, 
			ArrayList<Integer> amount, ArrayList<AmountType> amountType, Recipe r) throws SQLException {
		ArrayList<Ingredient> ingridientsList = new ArrayList<Ingredient>();
		for(int i = 0; i < allIngredients.size(); i++) {
			PreparedStatement prepStmt2 = conn.prepareStatement(CREATE_INGREDIENT_RECIPE);
			prepStmt2.setInt(1, r.getRecipeId());
			prepStmt2.setInt(2, allIngredients.get(i).getIngredientId());
			prepStmt2.setInt(3, amount.get(i));
			String amountTypeS = amountType.get(i).name();
			prepStmt2.setString(4, amountTypeS);

			ingridientsList.add(allIngredients.get(i));
			prepStmt2.executeUpdate();
		}

		return ingridientsList;
	}

	public ArrayList<Category> setupCategorys(Connection conn, ArrayList<Category> recipeCategorys, 
			Recipe r) throws SQLException {
		ArrayList<Category> categoryList = new ArrayList<Category>();
		for(int i =0; i < recipeCategorys.size(); i++) {
			PreparedStatement prepStmt = conn.prepareStatement(INSERT_CATEGORY_TO_RC);
			prepStmt.setInt(1, r.getRecipeId());
			prepStmt.setInt(2, recipeCategorys.get(i).getCategoryId());

			categoryList.add(recipeCategorys.get(i));
			prepStmt.executeUpdate();
		}
		return categoryList;
	}

	public boolean checkIfIngredientNameExists(Connection conn, String name) throws SQLException {
		PreparedStatement prepStmt = conn.prepareStatement(CHECK_INGREDIENTS_NAME);
		prepStmt.setString(1, name);
		ResultSet rs = prepStmt.executeQuery();
		int num = 0;
		while(rs.next()) {
			num++;
		}
		if(num > 0)
			return true;
		return false;
	}

	public Ingredient addIngridient(Connection conn, String name) throws Exception {
		int id = getMaxIngredientId(conn);
		Ingredient ing = new Ingredient((id+1), name);
		if (checkIfIngredientNameExists(conn, name))
			throw new Exception("This Ingredient already exists in DB");
		PreparedStatement prepStmt = conn.prepareStatement(CREATE_INGREDIENT);
		prepStmt.setInt(1, ing.getIngredientId());
		prepStmt.setString(2, name);
		prepStmt.executeUpdate();
		return ing;
	}

	public boolean checkIfCategoryNameExists(Connection conn, String name) throws SQLException {
		PreparedStatement prepStmt = conn.prepareStatement(CHECK_CATEGORY_NAME);
		prepStmt.setString(1, name);
		ResultSet rs = prepStmt.executeQuery();
		int num = 0;
		while(rs.next()) {
			num++;
		}
		if(num > 0)
			return true;
		return false;
	}

	public Category addCategoryGeneral(Connection conn, String CategortName) throws Exception {
		int id = getMaxCategoryId(conn);
		Category c = new Category((id+1), CategortName);
		if(checkIfCategoryNameExists(conn, CategortName))
			throw new Exception("This Category already exists in DB");
		PreparedStatement prepStmt = conn.prepareStatement(INSERT_CATAGORY);
		prepStmt.setInt(1, c.getCategoryId());
		prepStmt.setString(2, CategortName);
		prepStmt.executeUpdate();
		return c;
	}

	public void addRecipeToFavorite(Connection conn, UserAccount ua, int recipeId) throws SQLException {
		PreparedStatement prepStmt = conn.prepareStatement(INSERT_USER_FAVORITS);
		prepStmt.setInt(1, recipeId);
		prepStmt.setInt(2, ua.getUserId());

		prepStmt.executeUpdate();
	}

	public void removeRecipeFromFavorite(Connection conn, int recipeId) throws SQLException {
		PreparedStatement prepStmt = conn.prepareStatement(REMOVE_USER_FAVORITS);
		prepStmt.setInt(1, recipeId);

		prepStmt.executeUpdate();
	}

	//	public ArrayList<Recipe> getAllRecipesWithSpesificIngredients(Connection conn, ArrayList<Ingredient> tempIngredients) throws SQLException {
	//		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
	//		for(int i =0; i< tempIngredients.size(); i++) {
	//			PreparedStatement prepStmt = conn.prepareStatement(QUERY_RECIPE_SPESIFIC_ING);
	//			prepStmt.setInt(1, tempIngredients.get(i).getIngredientId());
	//			ResultSet rs = prepStmt.executeQuery();
	//			while(rs.next()) {
	//				Recipe r = new Recipe(rs.getInt("recipe_ID"), rs.getString("recipe_name"), rs.getInt("cooking_time"), rs.getString("recipe_description"));
	//				if(!recipes.contains(r))
	//					recipes.add(r);
	//			}
	//		}
	//		return recipes;
	//	}


	public ArrayList<Recipe> getAllFavoriteRecipes(Connection conn, UserAccount ua) throws Exception {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		PreparedStatement prepStmt = conn.prepareStatement(QUERY_RECIPE_FAVORITES);
		prepStmt.setInt(1, ua.getUserId());
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next()) {
			Recipe r = new Recipe(rs.getInt("recipe_ID"), rs.getString("recipe_name"), rs.getInt("cooking_time"), rs.getString("recipe_description"), ua);
			
			PreparedStatement prepStmt2 = conn.prepareStatement(QUERY_INGREDIENT_SPECIFIC_RECIPE);
			prepStmt2.setInt(1, r.getRecipeId());
			ResultSet rs2 = prepStmt2.executeQuery();
			ArrayList<Ingredient> allIngredients = new ArrayList<Ingredient>();
			while(rs2.next()) {
				Ingredient temp = new Ingredient(rs2.getInt("ingredient_ID"), rs2.getString("ingredient_name"));
				allIngredients.add(temp);
			}
			r.setAllIngredients(allIngredients);
			
			PreparedStatement prepStmt4 = conn.prepareStatement(QUERY_PICTURE_RECIPE);
			prepStmt4.setInt(1, r.getRecipeId());
			ResultSet rs4 = prepStmt4.executeQuery();
			ArrayList<Picture> allPictures = new ArrayList<Picture>();
			while(rs4.next()) {
				Picture temp = new Picture(rs4.getInt("picture_ID"), rs4.getString("picture_title"), rs4.getString("picture_author"), rs4.getString("picture_description"));
				temp.setRecipe(r);
				allPictures.add(temp);
			}
			r.setAllPictures(allPictures);
			
			recipes.add(r);
		}

		return recipes;
	}

	public int getMaxIngredientId(Connection conn) throws SQLException {
		Statement stmt0 = conn.createStatement();
		ResultSet rs0 = stmt0.executeQuery(QUERY_INGREDIENT);
		if(rs0 == null)
			return 0;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(MAX_INGREDIENT);
		rs.next();
		int maxNum = rs.getInt("max_number");
		return maxNum;

	}

	public int getMaxCategoryId(Connection conn) throws SQLException {
		Statement stmt0 = conn.createStatement();
		ResultSet rs0 = stmt0.executeQuery(QUERY_CATEGORY);
		if(rs0 == null)
			return 0;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(MAX_CATEGORY);
		rs.next();
		int maxNum = rs.getInt("max_number");
		return maxNum;

	}

	public int getMaxRecipeId(Connection conn) throws SQLException {
		Statement stmt0 = conn.createStatement();
		ResultSet rs0 = stmt0.executeQuery(QUERY_RECIPES);
		if(rs0 == null)
			return 0;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(MAX_RECIPE);
		rs.next();
		int maxNum = rs.getInt("max_number");
		return maxNum;

	}

	public UserAccount getCurrentUserById(Connection conn, int id) throws SQLException {
		UserAccount ua;//= new UserAccount();
		PreparedStatement prepStmt = conn.prepareStatement(QUERY_ACCOUNT_SPESIFIC);
		prepStmt.setInt(1, id);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		ua = new UserAccount(rs.getInt("userID"), rs.getString("username"), rs.getString("fName"), rs.getString("lName"), 
				rs.getString("password"), rs.getString("email"));
		return ua;

	}

	public ArrayList<Recipe> getAllRecipes(Connection conn) throws Exception {
		ArrayList<Recipe> lr = new ArrayList<>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(QUERY_RECIPES);
		Recipe recipe;
		while(rs.next()) {
			recipe = new Recipe(rs.getInt("recipe_ID"), rs.getString("recipe_name"), rs.getInt("cooking_time"), rs.getString("recipe_description"));
			PreparedStatement prepStmt2 = conn.prepareStatement(QUERY_INGREDIENT_SPECIFIC_RECIPE);
			prepStmt2.setInt(1, recipe.getRecipeId());
			ResultSet rs2 = prepStmt2.executeQuery();
			ArrayList<Ingredient> allIngredients = new ArrayList<Ingredient>();
			while(rs2.next()) {
				Ingredient temp = new Ingredient(rs2.getInt("ingredient_ID"), rs2.getString("ingredient_name"));
				allIngredients.add(temp);
			}
			recipe.setAllIngredients(allIngredients);
			lr.add(recipe);
		}
		return lr;
	}

	public void removeRecipe(Connection conn, int recipeId) throws SQLException {
		PreparedStatement prepStmt2 = conn.prepareStatement(DELETE_RECIPE_INGREDIENT);
		prepStmt2.setInt(1, recipeId);
		prepStmt2.executeUpdate();

		PreparedStatement prepStmt3 = conn.prepareStatement(DELETE_RECIPE_CATEGORY);
		prepStmt3.setInt(1, recipeId);
		prepStmt3.executeUpdate();

		PreparedStatement prepStmt4 = conn.prepareStatement(DELETE_RECIPE_USER_FAVORITE);
		prepStmt4.setInt(1, recipeId);
		prepStmt4.executeUpdate();
		
		PreparedStatement prepStmt5 = conn.prepareStatement(DELETE_PICTURE_RECIPE);
		prepStmt5.setInt(1, recipeId);
		prepStmt5.executeUpdate();

		PreparedStatement prepStmt = conn.prepareStatement(DELETE_RECIPE);
		prepStmt.setInt(1, recipeId);
		prepStmt.executeUpdate();

	}

	public void updateRecipe(Connection conn, int recipeID, String recipeName, int cookingTime, String description) throws SQLException {
		PreparedStatement prepStmt = conn.prepareStatement(UPDATE_RECIPE_NAME);
		prepStmt.setString(1, recipeName);
		prepStmt.setInt(2, recipeID);
		prepStmt.executeUpdate();

		PreparedStatement prepStmt2 = conn.prepareStatement(UPDATE_RECIPE_COOKING_TIME);
		prepStmt2.setInt(1, cookingTime);
		prepStmt2.setInt(2, recipeID);
		prepStmt2.executeUpdate();

		PreparedStatement prepStmt3 = conn.prepareStatement(UPDATE_RECIPE_DESCRIPTION);
		prepStmt3.setString(1, description);
		prepStmt3.setInt(2, recipeID);
		prepStmt3.executeUpdate();
	}

	public int getMaxPictureId(Connection conn) throws SQLException {
		Statement stmt0 = conn.createStatement();
		ResultSet rs0 = stmt0.executeQuery(QUERY_ALL_PICTURE);
		if(rs0 == null)
			return 0;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(MAX_PICTURE);
		rs.next();
		int maxNum = rs.getInt("max_number");
		return maxNum;

	}

	public Picture addPicture(Connection conn, int recipeId, String title, String author, String description) throws SQLException {
		int id = getMaxPictureId(conn);
		Picture picture = new Picture((id+1), title, author, description);
		PreparedStatement prepStmt = conn.prepareStatement(INSERT_PICTURE);
		prepStmt.setInt(1, picture.getPictureId());
		prepStmt.setString(2, title);
		prepStmt.setString(3, description);
		prepStmt.setString(4, author);
		prepStmt.setInt(5, recipeId);
		//picture.setRecipe(r);
		prepStmt.executeUpdate();	

		return picture;
	}




}
