package model;

import java.util.ArrayList;

public class UserAccount {
	private int userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private ArrayList<Recipe> allRecipes;
	
	public UserAccount() {
		
	}

	public UserAccount(int userId, String userName, String firstName, String lastName, String password ,String email) {
		this.userId = userId;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		allRecipes = new ArrayList<Recipe>();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Recipe> getAllRecipes() {
		return allRecipes;
	}

	public void setAllRecipes(ArrayList<Recipe> allRecipes) {
		this.allRecipes = allRecipes;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserAccount [userId=" + userId + ", userName=" + userName + ", firstName=" + firstName + ", lastName="
				+ lastName + ", password=" + password + ", email=" + email + ", allRecipes=" + allRecipes + "]";
	}
	
	

}
