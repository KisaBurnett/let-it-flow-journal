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


/**
 * New User Controller, user can create a new account or press cancel to move back to the login screen.
 * 
 * @author Royd Salinas
 */
public class NewUserController {

	@FXML
	private Button CreateButton;
	@FXML
	private Label User;
	
	@FXML
	private Label Pass;
	
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	
	@FXML
	private Button CancelButton;
	
	private Stage stage;
	
	private Scene scene;
	
	private String currString;
	
	@FXML
	private Label labelText;
	
	//if a create user button is pressed we call FileManager to set new userdata and is thrown to project folder.
	@FXML
	public void CreateUser(ActionEvent event) throws IOException, InterruptedException {
		currString = FileManager.NewUser(username.getText(),password.getText());
		
		if(currString.contentEquals("failure") || currString.contentEquals("Error: User already exists"))
		{
			labelText.setText("Username taken, please try again");
		}
		else
		{
			labelText.setText("Created User successfully");
		}
		if (currString.contentEquals("success"))
		{
			Thread.sleep(1000);
			Parent root = FXMLLoader.load(getClass().getResource("/application/view/LoginForm1.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	
	//cancel means we go back to the main login screen
	@FXML
	public void Cancel(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/application/view/LoginForm1.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	
	
}
