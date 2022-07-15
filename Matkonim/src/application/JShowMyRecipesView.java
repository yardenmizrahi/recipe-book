package application;

import java.net.MalformedURLException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.Category;
import model.Recipe;
import mvc.UIeventsListener;

public class JShowMyRecipesView implements actionTemplate{
	private UIeventsListener myListener;
	private mainViewPageTemplate myMainPage;
	
	@FXML private ListView<Recipe> allMyRecipes;
    @FXML private Button btnAddPic;
	@FXML private Label errors;
	@FXML private Button submit;
	
    public void addPicAction(ActionEvent event) {
    	Recipe temp = allMyRecipes.getSelectionModel().selectedItemProperty().getValue();
		if(temp != null) {
			myMainPage.openAddPicture(temp);
		}
    }

	public void submitAction(ActionEvent event) {
		Recipe temp = allMyRecipes.getSelectionModel().selectedItemProperty().getValue();
		if(temp != null) {
			myMainPage.openEditMyRecipe(temp);
		}
	}

	@Override
	public void initData() {
		errors.setText("");
		try {
			allMyRecipes.getItems().addAll(myListener.showAllMyRecipesToView()); //TODO

			allMyRecipes.setCellFactory(lv -> new ListCell<Recipe>() {
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

}
