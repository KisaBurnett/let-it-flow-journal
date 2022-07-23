package application.controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	
	/** Controller LoginForm1 controls the loginform scene we will need to call FileManager to look for a 
 	* username/password and allow users to create a new username/pw.
 	* @author Royd Alex Salinas
 	*/
	public LoginController() {
		
	}
	
	/**
	 * variables for elements in
	 * fxml file file loginform scene
	 * button is the login button
	 * wrongLogin displays when something went wrong
	 * username is username
	 * password is password!
	 * notice that i placed an @FXML on each item, you need to do that to connect the elements to the 
	 * corresponding element in the fxml file
	 */
	@FXML
	private Button button;
	@FXML
	private Label wrongLogin;
	
	@FXML
	private Label correctLogin;
	
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	
	
	/** method is called when a user presses "log in" button*/
	@FXML
	public void userLogin(ActionEvent event) throws IOException {
		checkLogin();
	}
	
	/** method is called to check login info, its all placeholders right now but just using import methods from fx to see possibilities */
	@FXML
	private void checkLogin() throws IOException {
		Main m = new Main();
		if(username.getText().toString().equals("RSal92") && password.getText().toString().equals("1234")) {
			correctLogin.setText("Successful log in!");
			//m.changeScene("what comes after.fxml");	
		}
		else if (username.getText().isEmpty() && password.getText().isEmpty()) {
			wrongLogin.setText("Please enter a valid username and password!!");
		}
		
		else {
			wrongLogin.setText("Wrong username or password!");
		}
	}

}
	
