package application.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/** Serves as the controller for the edit entry view of the program.
 * Allows user to compose and edit an entry, as well as save it to a file.
 * Also provides option for motivational pop-up, and allows user to log out or quit the program.
 * 
 * @author Kisa Burnett */
public class EntryController implements Initializable{
	@FXML
	private Button mainButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button motivateButton;
	@FXML
	private TextField entryText;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
