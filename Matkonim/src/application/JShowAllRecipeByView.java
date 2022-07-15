package application;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.Category;
import model.Recipe;
import mvc.UIeventsListener;

public class JShowAllRecipeByView implements actionTemplate{
	private UIeventsListener myListener;
	private mainViewPageTemplate myMainPage;

	@FXML private ListView<Recipe> allRecipe;
	@FXML private Label errors;
	@FXML private Button submit;

	public void submitAction(ActionEvent event) {
		Recipe temp = allRecipe.getSelectionModel().selectedItemProperty().getValue();
		try {
			if (temp != null)
			{
				errors.setText("");
				myListener.addRecipeToUserFavorite(temp.getRecipeId());
				myMainPage.successMsg("Recipe added successfully to your favorites list");
			}
		}catch(Exception e) {errors.setText(e.getMessage());}

	}

	@Override
	public void initData() {
		errors.setText("");
		try {
			allRecipe.getItems().addAll(myListener.showAllRecipesToView());

			allRecipe.setCellFactory(lv -> new ListCell<Recipe>() {
				public void updateItem(Recipe item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setText(null);
					}else {
						String text = "Recipe Name: " + item.getName() + " \tID: " + item.getRecipeId() + " \tCooking Time: " + item.getCookingTime();
						text += "\nDescription: " + item.getDescription() + "\nIngredients:";
						for (int i = 0; i < item.getAllIngredients().size(); i++)
							text += "\t\n" + (i+1) + ") " + item.getAllIngredients().get(i).getName();
						text += "\nPictures:";
						for (int i = 0; i < item.getAllPictures().size(); i++) {
							text += "\t\n" + (i+1) + ") " + item.getAllPictures().get(i).getDescription();
							/*try {
								URL tempLink = new URL("" + item.getAllPictures().get(i).getDescription());
								text +="\t\n" + tempLink;
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
		
						}
						setText(text);
					}
				}
			});
		}catch(Exception e) {errors.setText(e.getMessage());}
	}

	@Override
	public void registerListener(UIeventsListener myListener, mainViewPageTemplate myMainPageViewTemplate) {
		this.myListener = myListener;
		this.myMainPage = myMainPageViewTemplate;
	}

	public void setRecipeArray(ArrayList<Recipe> showThis) {
		allRecipe.getItems().clear();
		try {
			allRecipe.getItems().addAll(showThis);

			allRecipe.setCellFactory(lv -> new ListCell<Recipe>() {
				public void updateItem(Recipe item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setText(null);
					}else {
						String text = "Recipe Name: " + item.getName() + ", \tID: " + item.getRecipeId() + ", \tCooking Time: " + item.getCookingTime();
						text += "\nDescription: " + item.getDescription() + " \n  Ingredients:";
						for (int i = 0; i < item.getAllIngredients().size(); i++)
							text += "\t\n" + (i+1) + ") " + item.getAllIngredients().get(i).getName();
						text += " \n  Pictures:";
						for (int i = 0; i < item.getAllPictures().size(); i++) {
							text += "\t\n" + (i+1) + ") " + item.getAllPictures().get(i).getDescription();
							/*try {
								URL tempLink = new URL("" + item.getAllPictures().get(i).getDescription());
								text +="\t\n" + tempLink;
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
		
						}
						setText(text);
					}
				}
			});
		}catch(Exception e) {errors.setText(e.getMessage());}
	}

}
