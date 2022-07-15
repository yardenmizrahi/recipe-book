package application;


import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;
import model.Ingredient;
import model.Recipe;
import mvc.UIeventsListener;

public class JFindMeARecipeActionView implements actionTemplate{
	private UIeventsListener myListener;
	private mainViewPageTemplate myMainPage;

	@FXML private CheckBox chbIng2;
	@FXML private CheckBox chbIng3;
	@FXML private CheckBox chbIng4;
	@FXML private CheckBox chbIng5;
	@FXML private ComboBox<Ingredient> cmbIng1;
	@FXML private ComboBox<Ingredient> cmbIng2;
	@FXML private ComboBox<Ingredient> cmbIng3;
	@FXML private ComboBox<Ingredient> cmbIng4;
	@FXML private ComboBox<Ingredient> cmbIng5;
	@FXML private Label errors;
	@FXML private Button search;


	/*public void byIngredientNameAction(ActionEvent event) {
		cmbRecipeName.setDisable(true);
		cmbIng1.setDisable(false);
		chbIng2.setDisable(false);
		chbIng3.setDisable(false);
		chbIng4.setDisable(false);
		chbIng5.setDisable(false);
	}

	public void byRecipeNameAction(ActionEvent event) {
		cmbRecipeName.setDisable(false);
		cmbIng1.setDisable(true);
		chbIng2.setDisable(true);
		chbIng3.setDisable(true);
		chbIng4.setDisable(true);
		chbIng5.setDisable(true);
	}*/

	public void chbIng2Action(ActionEvent event) {
		if (chbIng2.isSelected())
			cmbIng2.setDisable(false);
		else
			cmbIng2.setDisable(true);
	}

	public void chbIng3Action(ActionEvent event) {
		if (chbIng3.isSelected())
			cmbIng3.setDisable(false);
		else
			cmbIng3.setDisable(true);
	}

	public void chbIng4Action(ActionEvent event) {
		if (chbIng4.isSelected())
			cmbIng4.setDisable(false);
		else
			cmbIng4.setDisable(true);
	}

	public void chbIng5Action(ActionEvent event) {
		if (chbIng5.isSelected())
			cmbIng5.setDisable(false);
		else
			cmbIng5.setDisable(true);
	}

	public void searchRecipeAction(ActionEvent event) {
		ArrayList<Ingredient> searchByIngs= new ArrayList<Ingredient>(); 
		if(cmbIng1.getValue() != null)
			searchByIngs.add(cmbIng1.getValue());
		if(chbIng2.isSelected() && cmbIng2.getValue() != null)
			searchByIngs.add(cmbIng2.getValue());
		if(chbIng3.isSelected() && cmbIng3.getValue() != null)
			searchByIngs.add(cmbIng3.getValue());
		if(chbIng4.isSelected() && cmbIng4.getValue() != null)
			searchByIngs.add(cmbIng4.getValue());
		if(chbIng5.isSelected() && cmbIng5.getValue() != null)
			searchByIngs.add(cmbIng5.getValue());
		
		try {
			ArrayList<Recipe> foundRecipes = myListener.showAllRecipesToViewThatHave(searchByIngs);
			System.out.println("working on showing");
			errors.setText("");
			myMainPage.openShowAllRecipeBy(foundRecipes, true);
		} catch (Exception e) {errors.setText(e.getMessage());}
	}

	public void initCmbIngredients(ComboBox<Ingredient> tempCMB) {
		try {
			tempCMB.getItems().addAll(FXCollections.observableArrayList(myListener.showAllIngredientsToView()));

			tempCMB.setConverter(new StringConverter<Ingredient>() {
				@Override
				public String toString(Ingredient tempIng) {
					if (tempIng == null)
						return "Please Choose An Ingredient";
					return tempIng.getName();
				}
				@Override
				public Ingredient fromString(String arg0) {
					return null;
				}
			});
		} catch (Exception e) {errors.setText(e.getMessage());}
	}

	@Override
	public void initData() {
		//ToggleGroup tgSearchBy = new ToggleGroup(); //TODO
		//rbtByIngredientName.setToggleGroup(tgSearchBy);
		//rbtByRecipeName.setToggleGroup(tgSearchBy);
		cmbIng1.setDisable(false);
		myMainPage.openShowAllRecipeBy(null, false);
		initCmbIngredients(cmbIng1);
		initCmbIngredients(cmbIng2);
		initCmbIngredients(cmbIng3);
		initCmbIngredients(cmbIng4);
		initCmbIngredients(cmbIng5);

		//		try {
		//			cmbRecipeName.getItems().addAll(FXCollections.observableArrayList(myListener.showAllRecipesToView()));
		//			
		//			cmbRecipeName.setConverter(new StringConverter<Recipe>() {
		//				@Override
		//				public String toString(Recipe tempRec) {
		//					if (tempRec == null)
		//						return "Please Choose An Ingredient";
		//					return tempRec.getName();
		//				}
		//				@Override
		//				public Recipe fromString(String arg0) {
		//					return null;
		//				}
		//			});
		//		} catch (Exception e) {errors.setText(e.getMessage());}
	}

	@Override
	public void registerListener(UIeventsListener myListener, mainViewPageTemplate myMainPageViewTemplate) {
		this.myListener = myListener;
		this.myMainPage = myMainPageViewTemplate;
	}

}
