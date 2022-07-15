package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Category;
import model.Ingredient;
import model.Recipe;
import mvc.UIeventsListener;

public class editRecipeView implements actionTemplate{
	private UIeventsListener myListener;
	private mainViewPageTemplate myMainPage;
	private Recipe myEditRecipe;
	
	@FXML private ListView<Category> allCategories;
	@FXML private ListView<Ingredient> allIngredients;
	@FXML private TextArea dsDescription;
	@FXML private Label errors;
	@FXML private Label lblEditingRecipeID;
	@FXML private Button remove;
	@FXML private TextField tfName;
	@FXML private TextField tfTime;
	@FXML private Button update;

	@FXML
	void removeAction(ActionEvent event) {
		errors.setText("");
		try {
			if (myListener.removeRecipe(myEditRecipe.getRecipeId())) {
				myMainPage.successCloseMe("Recipe Removed Successfully", 0);
			}
		} catch (Exception e) {errors.setText(e.getMessage());}
	}

	@FXML
	void updateAction(ActionEvent event) {
		errors.setText("");
		boolean flag = true;
		if (tfName.getText() == null || tfName.getText().equals(""))
			flag = false;
		if (tfTime.getText() == null || tfTime.getText().equals(""))
			flag = false;
		if (dsDescription.getText() == null || dsDescription.getText().equals(""))
			flag = false;
		if (flag == false) {
			errors.setText("Missing Information");
			return;
		}
		try {
			int cookingTime = Integer.parseInt(tfTime.getText());
			if (myListener.updateRecipe(myEditRecipe.getRecipeId(), tfName.getText(), cookingTime, dsDescription.getText())) {
				myMainPage.successCloseMe("Recipe Updated Successfully", 0);
			}
		} catch (Exception e) {errors.setText(e.getMessage());}
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
	}
	
	public void initData2(Recipe tempEdit) {
		//init basic data
		errors.setText("");
		myEditRecipe = tempEdit;
		lblEditingRecipeID.setText("Editing Recipe ID: " + myEditRecipe.getRecipeId());
		tfName.setText(myEditRecipe.getName());
		tfTime.setText("" + myEditRecipe.getCookingTime());
		dsDescription.setText(myEditRecipe.getDescription());
		
		
		//init list views
		try {
			allCategories.getItems().addAll(tempEdit.getRecipeCategorys());
			allCategories.setCellFactory(lv -> new ListCell<Category>() {
				public void updateItem(Category item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setText(null);
					}else {
						String text = "Category Name: " + item.getCategoryName();
						setText(text);
					}
				}
			});
			
			allIngredients.getItems().addAll(tempEdit.getAllIngredients());
			allIngredients.setCellFactory(lv -> new ListCell<Ingredient>() {
				public void updateItem(Ingredient item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setText(null);
					}else {
						String text = "Ingredient Name: " + item.getName();
						setText(text);
					}
				}
			});
		}catch(Exception e) {errors.setText(e.getMessage());}
	}

	@Override
	public void registerListener(UIeventsListener myListener, mainViewPageTemplate myMainPageViewTemplate) {
		this.myListener = myListener;
		myMainPage = myMainPageViewTemplate;
	}

}
