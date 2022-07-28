package application.controller;
import application.model.FileManager;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

/** Serves as the controller for the main menu of the program.
 * Allows user to create a new entry, and view, edit, and delete existing entries.
 * Also provides option for motivational pop-up, and allows user to log out or quit the program.
 * 
 * @author Kisa Burnett
 * @author Matthew Darragh */
public class MenuController {
	

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
	@FXML
	private Menu menuFile;
	@FXML
	private MenuBar menuBar;
	@FXML 
	private Button delete;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private FileManager CurrUser;
	
	/** Generates the list view of saved entries on the menu view. */
	public void switchToMainMenu() {		
		/** Check to see if the user has any files saved onto their account */
		if (CurrUser.Load().isEmpty()) {
			savedEntries.getItems().add("You have no saved files.");
			openButton.setDisable(true);
			delete.setDisable(true);
		}
		/** If there are entries in the user's .csv file, add those entries to the ListView */
		else {
			savedEntries.getItems().addAll(CurrUser.Load());
			
			openButton.setDisable(true);
			delete.setDisable(true);
			
			savedEntries.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if(!CurrUser.Load().isEmpty()) {
						openButton.setDisable(false);
						delete.setDisable(false);
					}
				}
				
			});
		}
	}

	/** Switches to the LoginForm scene so user can log in as a different user. */
	public void switchToLoginForm1(ActionEvent event) throws IOException {
		/** Alert to confirm that user wants to log out. */
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Log Out");
		alert.setContentText("Do you want to log out?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.isPresent() && result.get() == ButtonType.OK ) {
			root = FXMLLoader.load(getClass().getResource("/application/view/LoginForm1.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	
	/** Switches to the EditEntry scene so user can edit an entry. */
	public void switchToEditEntry(ActionEvent event) throws IOException {
		String fileName = savedEntries.getSelectionModel().getSelectedItem();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/EditEntry.fxml"));
		root = loader.load();
		
		EntryController entryController = loader.getController();
		/** Passes in the name of the entry file, and the name of the file containing all entry names. */
		entryController.setEntryData(CurrUser);
		entryController.editEntry(fileName);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/** Switches to EditEntry and sets up blank form for user to compose and save a new entry. */
	public void switchToNewEntry(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/EditEntry.fxml"));
		root = loader.load();
		
		EntryController entryController = loader.getController();
		/** Passes in the name of the file containing all entry names. */
		entryController.setEntryData(CurrUser);
		entryController.newEntry();
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/** Deletes Notes from users saved data, and from the GUI */
	public void deleteEntry(ActionEvent event) throws IOException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete File");
		alert.setContentText("Are you sure you want to delete this file?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.isPresent() && result.get() == ButtonType.OK ) {
			ObservableList<String> selectedFile = savedEntries.getSelectionModel().getSelectedItems();
			
			CurrUser.DeleteNote(selectedFile.get(0));
			savedEntries.getItems().clear();
			switchToMainMenu();
		}
	}

	/** Quits the application */
	public void quitApplication(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Quit Let It Flow Journal");
		alert.setContentText("Are you sure you want to quit?");
		Optional<ButtonType> result = alert.showAndWait();
	
		if (result.isPresent() && result.get() == ButtonType.OK ) {
			Platform.exit();
			System.exit(0);
		}
	}
	
	public void setData(FileManager user) {
		this.CurrUser = user;
	}
	
	
	/*TO-DO:
	 * Generate motivational quote from motivation class*/
}
