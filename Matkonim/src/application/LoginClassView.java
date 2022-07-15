package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class LoginClassView {
	private Connection conn = null;
	private final String PASSWORD = "DOGcute111";
	private final String USERNAME = "root";
	private final String DBURL = "jdbc:mysql://localhost:3306/my_recipe_book";

	public LoginClassView() {

	}

	@FXML protected Button login;
	@FXML protected Label errors;
	@FXML protected TextField username;
	@FXML protected PasswordField password;

	public void userLogin(ActionEvent event) {
		errors.setTextFill(Color.RED);
		errors.setText("");
		checkLogin();
	}

	private void checkLogin()  {
		Main m = new Main();
		int tempUserId;
		try {
			if (username.getText().isEmpty() || password.getText().isEmpty()) 
				errors.setText("Please Enter Data");
			else {
				tempUserId = checkDetails();
				//System.out.println("key= " + tempUserId);
				if(tempUserId != -1)
				{
					System.out.println("yes");
					errors.setTextFill(Color.GREEN);
					errors.setText("Success!");
					m.changeSceneToMain("/MainPage.fxml", 1600, 750, tempUserId); //logges you in
				}
				else
					errors.setText("Wrong Information");
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			errors.setText("" + e.getMessage());
		}
	}

	private int checkDetails() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String dbURL = DBURL; //85.250.112.134:3306
			conn = DriverManager.getConnection(dbURL, USERNAME, PASSWORD);

			//System.out.println("username: " + username.getText() + " passw: " + password.getText());
			Statement stmt = conn.createStatement();
			ResultSet rs2 = stmt.executeQuery("SELECT userID FROM user_account " +
					"WHERE username like '" + username.getText() +"' AND password like '"+ password.getText()+"';");

			if(rs2.next()) {
				int id = rs2.getInt("userID");
				return id; //return userid TODO
			}
		} catch (ClassNotFoundException | SQLException e) {errors.setText("" + e.getMessage());}
		return -1;
	}
}

