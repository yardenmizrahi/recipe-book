package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.Category;
import model.Recipe;
import mvc.UIeventsListener;

public class JShowAllMyFavView implements actionTemplate{

	private UIeventsListener myListiner;

	@FXML private ListView<Recipe> allFavorites;
	@FXML private Label errors;
	@FXML private Button submit;

	public void submitAction(ActionEvent event) {
		try {
			Recipe temp = allFavorites.getSelectionModel().selectedItemProperty().getValue();
			if(temp != null) {
				myListiner.removeRecipeFromUserFavorite(temp.getRecipeId());
				allFavorites.getItems().remove(temp);
				errors.setText("");
			}
		}catch(Exception e) {errors.setText(e.getMessage());}
	}

	@Override
	public void initData() {
		errors.setText("");
		try {
			allFavorites.getItems().addAll(myListiner.showAllUserFavoriteRecipesToView());

			allFavorites.setCellFactory(lv -> new ListCell<Recipe>() {
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
		this.myListiner = myListener;
	}

}
