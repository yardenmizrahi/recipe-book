package application;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import mvc.UIeventsListener;

public class EmptyView implements actionTemplate{
	
	@FXML private ImageView imgRecipe;

	public void noImage() {
		imgRecipe.setVisible(false);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerListener(UIeventsListener myListener, mainViewPageTemplate myMainPageViewTemplate) {
		// TODO Auto-generated method stub

	}

}
