package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.model.FileManager;
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
	/* ArrayList that will store the returned fileNames from Load() */
	/* Currently Load() returns an empty the empty ArrayList<String> error */
	private ArrayList<String> entries = FileManager.Load("RSal92", "1234");
	/* Generates the list view of saved entries on the menu view. */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/* Check to see if the user has any files saved onto their account */
		if (entries.isEmpty()) {
			savedEntries.getItems().add("You have no saved files.");
		}
		/* If entries has fileNames, add those entries to the ListView */
		else {
			savedEntries.getItems().addAll(entries);
		}
	}

}
