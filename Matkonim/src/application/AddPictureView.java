
package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Recipe;
import mvc.UIeventsListener;

public class AddPictureView implements actionTemplate{
	private UIeventsListener myListener;
	private mainViewPageTemplate myMainPage;
	private Recipe myRecipeToAddPic;

	@FXML private Button btnAddPicture;
	@FXML private Label errors;
	@FXML private Label lblRecipeId;
	@FXML private TextField tfAuthor;
	@FXML private TextField tfLink;
	@FXML private TextField tfTitle;

	@FXML
	void addPictureAction(ActionEvent event) {
		errors.setText("");
		boolean flag = true;
		if (tfTitle.getText() == null || tfTitle.getText().equals(""))
			flag = false;
		if (tfLink.getText() == null || tfLink.getText().equals(""))
			flag = false;
		if (tfAuthor.getText() == null || tfAuthor.getText().equals(""))
			flag = false;
		if (!flag) {
			errors.setText("Missing Information");
			return;
		}
		try {
			if (myListener.addPicture(myRecipeToAddPic.getRecipeId(), tfTitle.getText(), tfAuthor.getText(), tfLink.getText())) {
				myMainPage.successCloseMe("Picture added sucessfully", 0);
			}
		} catch(Exception e) {errors.setText(e.getMessage());}
	}

	@Override
	public void initData() {
		errors.setText("");
		lblRecipeId.setText("");
	}

	@Override
	public void registerListener(UIeventsListener myListener, mainViewPageTemplate myMainPageViewTemplate) {
		this.myListener = myListener;
		myMainPage = myMainPageViewTemplate;
		
	}
	
	public void initData2(Recipe temp) {
		myRecipeToAddPic = temp;
		lblRecipeId.setText("Adding To Recipe: " + myRecipeToAddPic.getRecipeId() + ", " + myRecipeToAddPic.getName());
	}

}
