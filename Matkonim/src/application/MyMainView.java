package application;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import model.Recipe;
import mvc.UIeventsListener;

public class MyMainView implements mainViewPageTemplate{
	private UIeventsListener myListener;
	FXMLLoader actionLoader;
	FXMLLoader viewLoader;

	@FXML private BorderPane actionScreen;
	@FXML private Label actionTitle1;
	@FXML private Label bigError;
	@FXML private Button btnAddRecipe;
	@FXML private Button btnAddIngredientorCategory;
	//@FXML private Button btnAddRecipePicture;
	@FXML private Button btnFindMeARecipe;
	@FXML private Button btnMyRecipeBook;
	@FXML private Button btnShowMyFav;
	@FXML private MenuItem editDelete;
	@FXML private Label lblGeneralStatus;
	@FXML private Button lossesActions;
    @FXML private Label lblUA;
	@FXML private MenuItem mainLogout;
	@FXML private BorderPane viewScreen;

	public void registerListener(UIeventsListener newListener) {
		myListener = newListener;
		bigError.setText("");
		lblGeneralStatus.setText(" ");
		successCloseMe(null, 0);		
		successCloseMe(null, 1);
	}
	
	public void setLBLUA(String msg) {
		lblUA.setText("" + msg);
	}
	
	public void userLogOut(ActionEvent event) {
		try {
			Main m = new Main();
			m.changeSceneToLogin("/LoginPageEng.fxml", 950, 268);
		} catch (Exception e) {
			bigError.setText("" + e.getMessage());
		}
	}
	
	public void successMsg(String msg) {
		lblGeneralStatus.setText(msg);
	}

	public void initViewData() {
		actionTemplate tempController = viewLoader.getController();
		tempController.initData();
	}

	public void successCloseMe(String msg, int side) {
		if (side == 0)
			openActionScreen("/EmptyAction.fxml");
		else if (side == 1) {
			openMyViewScreen("/EmptyAction.fxml");
			EmptyView tempEmpty = viewLoader.getController();
			tempEmpty.noImage();
		}
		if (msg != null)
			lblGeneralStatus.setText("" + msg);
	}

	private void openActionScreen(String fxmlName) {	
		lblGeneralStatus.setText(" ");
		try {
			actionLoader = new FXMLLoader();
			actionLoader.setLocation(getClass().getResource(fxmlName));
			Parent tempAction;
			tempAction = actionLoader.load();
			actionScreen.setCenter(tempAction);
			tempAction.minWidth(500);

			actionTemplate tempController = actionLoader.getController();
			tempController.registerListener(myListener, this);
			tempController.initData();
		} catch (Exception e) {	bigError.setText("" + e.getMessage());}
	}

	private void openMyViewScreen(String fxmlName) {	
		lblGeneralStatus.setText(" ");
		try {
			viewLoader = new FXMLLoader();
			viewLoader.setLocation(getClass().getResource(fxmlName));
			Parent tempView;
			tempView = viewLoader.load();
			viewScreen.setCenter(tempView);
			tempView.minWidth(400);

			actionTemplate tempController = viewLoader.getController();
			tempController.registerListener(myListener, this);
			tempController.initData();
		} catch (Exception e) {	bigError.setText(" " + e.getMessage());}
	}

	public void closeInsideAction(ActionEvent event) {
		successCloseMe(null, 0);		
		successCloseMe(null, 1);
	}

	public void openAddRecipePage(ActionEvent event) {
		openActionScreen("/AddRecipe.fxml");
	}

	public void openAddIngredientorCategory(ActionEvent event) {
		openActionScreen("/AddIngredientAndCategory.fxml");
	}

	/*public void openAddRecipePicture(ActionEvent event) {

	}*/

	public void openFindMeARecipePage(ActionEvent event) {
		openActionScreen("/FindMeRecipeXVIEW.fxml");
	}

	public void openMyRecipeBookPage(ActionEvent event) {
		openMyViewScreen("/ShowAllmMyRecipes.fxml");
	}

	public void openShowMyFavPage(ActionEvent event) {
		openMyViewScreen("/ShowAllmMyFav.fxml");
	}

	public void openShowAllRecipeBy(ArrayList<Recipe> allMyRecipes, boolean flag) {
		openMyViewScreen("/ShowAllRecipeBy2.fxml");
		if (flag) {
			JShowAllRecipeByView tempController = viewLoader.getController();
			tempController.setRecipeArray(allMyRecipes);
		}
	}
	
	public void openEditMyRecipe(Recipe tempRec) {
		openActionScreen("/EditRecipe.fxml");
		editRecipeView tempController = actionLoader.getController();
		tempController.initData2(tempRec); 
	}
	
	public void openAddPicture(Recipe tempRec) {
		openActionScreen("/AddPictureToRecipe.fxml");
		AddPictureView tempController = actionLoader.getController();
		tempController.initData2(tempRec);
	}
	
	public void setNewMyRecipeData() {
		if (viewLoader.getController() instanceof JShowMyRecipesView)
			openMyViewScreen("/ShowAllmMyRecipes.fxml");
		if (viewLoader.getController() instanceof JShowAllRecipeByView)
			openMyViewScreen("/ShowAllRecipeBy2.fxml");
	}
}
