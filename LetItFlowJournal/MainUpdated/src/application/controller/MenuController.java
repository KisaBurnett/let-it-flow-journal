package application.controller;

import java.io.IOException;
import java.io.File;
import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import application.model.FileManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/** Serves as the controller for the main menu of the program.
 * Allows user to create a new entry, and view, edit, and delete existing entries.
 * Also provides option for motivational pop-up, and allows user to log out or quit the program.
 * 
 * @author Kisa Burnett
 * @author Matthew Darragh */
public class MenuController implements Initializable{
	@FXML
	private ListView<String> savedEntries;
	@FXML
	private Button logOut;
	@FXML
	private Button createEntry;
	@FXML
	private Button openButton;
	@FXML
	private Button motivationBtn;
	
	/* ArrayList that will store the returned fileNames from Load() */
	/* Currently Load() returns an empty the empty ArrayList<String> error */
	private ArrayList<String> entries;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	/** Generates the list view of saved entries on the menu view. */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		entries = new ArrayList<String>();
		
		/** Temporary method to load a test .csv file with a list of journal entry names. */
		try {
			File file = new File("TestFiles/entryNames.csv");
			Scanner scan = new Scanner(file);
			
			while(scan.hasNextLine()) {
				entries.add(scan.nextLine());
			}
			
			scan.close();
		}
		catch (IOException e) {
			System.out.println("Error: Entry name file not found. Please check the name and try again.");
		}

		
		/** Check to see if the user has any files saved onto their account */
		if (entries.isEmpty()) {
			savedEntries.getItems().add("You have no saved files.");
		}
		/** If entries has fileNames, add those entries to the ListView */
		else {
			savedEntries.getItems().addAll(entries);
			
			savedEntries.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					openButton.setDisable(false);
				}
				
			});
		}
	}

	/** Switches to the LoginForm scene so user can log in as a different user. */
	public void switchToLoginForm1(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/application/view/LoginForm1.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/** Switches to the EditEntry scene so user can compose a new entry. */
	public void switchToEditEntry(ActionEvent event) throws IOException {
		String fileName = savedEntries.getSelectionModel().getSelectedItem();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/EditEntry.fxml"));
		root = loader.load();
		
		EntryController entryController = loader.getController();
		entryController.editEntry(fileName);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
