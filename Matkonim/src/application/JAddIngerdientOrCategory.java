package application;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mvc.UIeventsListener;

public class JAddIngerdientOrCategory implements actionTemplate{
	private UIeventsListener myListener;
	private mainViewPageTemplate myMainPageViewTemplate;
	
	@FXML private Button btnAddCategory;
	@FXML private Button btnAddIngredient;
	@FXML private Label errors;
	@FXML private TextField tfCategoryName;
	@FXML private TextField tfIngredientName;

	public void submitAddCategory(ActionEvent event) {
		try {
			myListener.addCategoryToDB(tfCategoryName.getText());
			myMainPageViewTemplate.successMsg("Category added successfully");
		} catch (Exception e) { errors.setText(e.getMessage());}
		
	}

	public void submitAddIngredient(ActionEvent event) {
		try {
			myListener.addIngredientToDB(tfIngredientName.getText());
			myMainPageViewTemplate.successMsg("Ingredient added successfully");
		} catch (Exception e) {errors.setText(e.getMessage());}
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerListener(UIeventsListener myListener, mainViewPageTemplate myMainPageViewTemplate) {
		this.myListener = myListener;
		this.myMainPageViewTemplate = myMainPageViewTemplate;
	}

}
