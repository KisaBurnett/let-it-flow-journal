package application.controller;


import javafx.event.ActionEvent;
import java.io.IOException;

import application.model.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
		
		@FXML
		private Button newUserButton;
		
		private Scene scene;
		
		private Stage stage;
		
		private Parent root;
		
		
		
		
		
		/** method is called when a user presses "log in" button
		 * @throws InterruptedException */
		@FXML
		public void userLogin(ActionEvent event) throws IOException, InterruptedException {
			
			//object filemanager created with username and password
			FileManager CurrUser = new FileManager(username.getText(),password.getText());
			
			//check to see if user exists and goes to main menu if successful
			if (FileManager.UserExists(username.getText())) {
				
				wrongLogin.setText("Successful log in!");
				//throwing object of user to main menu.
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/MainScreen.fxml"));
				root = loader.load();
				MenuController Controller = loader.getController();
				Controller.setData(CurrUser);
				Controller.switchToMainMenu();
				//not sure if we need this yet
				//root = FXMLLoader.load(getClass().getResource("/application/view/MainScreen.fxml"));		
				stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
				
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
			
			else {
				wrongLogin.setText("Wrong username or password!");
			}
		}
		
		
		@FXML
		public void newUser(ActionEvent event) throws IOException {
			root = FXMLLoader.load(getClass().getResource("/application/view/LoginForm2.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		
		

}
	
