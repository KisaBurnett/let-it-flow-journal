package application.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/* Serves as the controller for the main menu of the program.
 * Allows user to create a new entry, and view, edit, and delete existing entries.
 * Also provides option for motivational pop-up, and allows user to log out or quit the program.
 * 
 * @author Kisa Burnett */
public class MenuController implements Initializable{
	@FXML
	private ListView<String> savedEntries;
	
	/* Below String[] will be updated to pull entry titles from the saved entries and place them in an array.  */
	String[] entries = {"Entry 1", "Entry 2", "Entry 3"};
	
	/* Generates the list view of saved entries on the menu view. */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		savedEntries.getItems().addAll(entries);		
	}

}
