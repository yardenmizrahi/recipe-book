package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.AmountType;
import model.Category;
import model.Ingredient;
import mvc.UIeventsListener;

public class AddRecipeView implements actionTemplate{
	private UIeventsListener myListener;
	private mainViewPageTemplate myMainViewPage;

	@FXML private ListView<Category> allCategories;
	@FXML private ListView<Ingredient> allIngredients;
	@FXML private Button btnAddCategory;
	@FXML private Button btnAddIngredient;
	@FXML private Button btnRemoveCategory;
	@FXML private Button btnRemoveIngredient;
	@FXML private ComboBox<AmountType> cmbAmountType;
	@FXML private ComboBox<Category> cmbCategory;
	@FXML private ComboBox<Ingredient> cmbIngredients;
	@FXML private TextArea dsDescription;
	@FXML private Label errors;
	@FXML private Button submit;
	@FXML private TextField tfAmountNum;
	@FXML private TextField tfName;
	@FXML private TextField tfTime;
	
	private ArrayList<Category> tempCats;
	private ArrayList<Ingredient> tempIngs;
	private ArrayList<Integer> amounts;
	private ArrayList<AmountType> myAmountTypes;


	public void addCategoryAction(ActionEvent event) {
		if (!tempCats.contains(cmbCategory.getValue())) {
			tempCats.add(cmbCategory.getValue());
			allCategories.getItems().add(cmbCategory.getValue());
			
			allCategories.setCellFactory(lv -> new ListCell<Category>() {
				public void updateItem(Category item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setText(null);
					}else {
						String text = "Category Name: " + item.getCategoryName() + ", \nID: " + item.getCategoryId();
						setText(text);
					}
				}
			});
		}
	}

	public void addIngredientAction(ActionEvent event) {
		if (!tempIngs.contains(cmbIngredients.getValue())) {
			if (tfAmountNum != null && cmbAmountType.getValue() != null) {
				try {
				int tempAmountNum = Integer.parseInt(tfAmountNum.getText());
				amounts.add(tempAmountNum);
				myAmountTypes.add(cmbAmountType.getValue());
				tempIngs.add(cmbIngredients.getValue());

				allIngredients.getItems().add(cmbIngredients.getValue());
				allIngredients.setCellFactory(lv -> new ListCell<Ingredient>() {
					public void updateItem(Ingredient item, boolean empty) {
						super.updateItem(item, empty);
						if(empty) {
							setText(null);
						}else {
							String text = "Ingredient Name: " + item.getName() + ", \nID: " + item.getIngredientId();
							setText(text);
						}
					}
				});
				errors.setText("");
				} catch(Exception e) {errors.setText(e.getMessage());}
			}
			else
				errors.setText("Please Enter Amount data to add ingredient");
		}
	}

	public void removeCategoryAction(ActionEvent event) {
		Category temp = allCategories.getSelectionModel().selectedItemProperty().getValue();
		if(temp != null) {
			tempCats.remove(temp);
			allCategories.getItems().remove(temp);
		}
	}

	public void removeIngredientAction(ActionEvent event) {
		Ingredient temp = allIngredients.getSelectionModel().selectedItemProperty().getValue();
		if(temp != null) {
			int index = tempIngs.indexOf(temp);
			amounts.remove(index);
			myAmountTypes.remove(index);
			tempIngs.remove(temp);
			allIngredients.getItems().remove(temp);
		}
	}


	public void submitAction(ActionEvent event) {
		boolean flag = true;
		if (tfName.getText() == null || tfName.getText().equals(""))
			flag = false;
		if (tfTime.getText() == null || tfTime.getText().equals(""))
			flag = false;
		if (dsDescription.getText() == null || dsDescription.getText().equals(""))
			flag = false;
		if (tempIngs.size() < 1 || tempCats.size() < 1)
			flag = false;
		if (!flag) {
			errors.setText("Missing Information");
			return;
		}
		
		try {
			int cookingTime = Integer.parseInt(tfTime.getText());
			myListener.addRecipeToDB(tfName.getText(), cookingTime, dsDescription.getText(), tempIngs, amounts, myAmountTypes, tempCats);
			myMainViewPage.successCloseMe("Recipe added successfully", 0);
		} catch(Exception e) {errors.setText(e.getMessage());}
	}

	@Override
	public void initData() {
		errors.setText("");
		// TODO Auto-generated method stub
		tempCats = new ArrayList<Category>();
		tempIngs = new ArrayList<Ingredient>();
		amounts = new ArrayList<Integer>();
		myAmountTypes = new ArrayList<AmountType>();
		
		try {
			cmbCategory.getItems().addAll(FXCollections.observableArrayList(myListener.showAllCategorysToView()));			
			cmbCategory.setConverter(new StringConverter<Category>() {
				public String toString(Category tempCat) {
					if (tempCat == null)
						return "Please Choose A Category";
					return tempCat.getCategoryName();
				}
				public Category fromString(String arg0) {
					return null;
				}
			});
			
			cmbIngredients.getItems().addAll(FXCollections.observableArrayList(myListener.showAllIngredientsToView()));
			cmbIngredients.setConverter(new StringConverter<Ingredient>() {
				public String toString(Ingredient tempIng) {
					if(tempIng == null)
						return "Please Choose An Ingredient";
					return tempIng.getName();
				}
				public Ingredient fromString(String arg0) {
					return null;
				}
			});
			
			cmbAmountType.getItems().setAll(AmountType.values());
			
		} catch (Exception e) {errors.setText(e.getMessage());}
		
	}

	@Override
	public void registerListener(UIeventsListener myListener, mainViewPageTemplate myMainPageViewTemplate) {
		this.myListener = myListener;
		this.myMainViewPage = myMainPageViewTemplate;
	}

}


