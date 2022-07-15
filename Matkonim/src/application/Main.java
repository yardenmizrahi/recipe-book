package application;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.RecipeBook;
import mvc.controller;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private static Stage stg; 
	
	public void start(Stage primaryStage) {
		//		try {
		//			BorderPane root = new BorderPane();
		//			Scene scene = new Scene(root,400,400);
		//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		//			primaryStage.setScene(scene);
		//			primaryStage.show();
		//		} catch(Exception e) {
		//			e.printStackTrace();
		//		}
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(getClass().getResource("/MainPage.fxml"));
//			Parent root = loader.load();
//			//Parent root = FXMLLoader.load(getClass().getResource("/ShowAllRecipeBy2.fxml"));
//
//			primaryStage.setTitle("Recipe ~ Book");
//			primaryStage.setScene(new Scene(root, 1400, 750));
//			primaryStage.setMinHeight(730);
//			primaryStage.setMinWidth(1470);
//			primaryStage.show();
//			
//			MyMainView view = loader.getController();
//			RecipeBook model = new RecipeBook();
//			controller controll123zoobi = new controller(model, view);
//			
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, "" + e.getMessage(), "Main Error!", JOptionPane.ERROR_MESSAGE);
//			e.printStackTrace();
//		}	
		
		
		stg = primaryStage;
		Image iconImg = new Image(getClass().getResourceAsStream("/img/background.jpg"));
		primaryStage.getIcons().add(iconImg);
		
		try {
		Parent root = FXMLLoader.load(getClass().getResource("/LoginPageEng.fxml"));
		primaryStage.setTitle("Recipe ~ Book");
		primaryStage.setScene(new Scene(root, 877, 232));
		primaryStage.setMinHeight(268);
		primaryStage.setMinWidth(950);
		primaryStage.show();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "" + e.getMessage(), "Main Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}	
	}
	
	public void changeSceneToMain(String fxml, double x, double y, int currentUserId) throws IOException, ClassNotFoundException, SQLException {
		//input: fxml file
		//output: function opens new fxml file
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml));
		Parent pane = loader.load();
		stg.getScene().setRoot(pane);
		stg.setWidth(x);
		stg.setHeight(y);
		stg.setMinHeight(y - 20);
		stg.setMinWidth(x - 20);

		MyMainView view = loader.getController();
		RecipeBook model = new RecipeBook();
		controller controll123zoobi = new controller(model, view, currentUserId);
	}
	
	public void changeSceneToLogin(String fxml, double x, double y) throws IOException, ClassNotFoundException, SQLException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml));
		Parent pane = loader.load();
		stg.getScene().setRoot(pane);
		stg.setWidth(x);
		stg.setHeight(y);
		stg.setMinHeight(y - 20);
		stg.setMinWidth(x - 20);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
