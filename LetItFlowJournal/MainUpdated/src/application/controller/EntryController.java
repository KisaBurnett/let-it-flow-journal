package application.controller;

import java.io.IOException;
import java.util.Optional;
import application.model.FileManager;
import application.model.Motivation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/** Serves as the controller for the edit entry view of the program.
 * Allows user to compose and edit an entry, as well as save it to a file.
 * Also provides option for motivational pop-up, and allows user to log out or quit the program.
 * 
 * @author Kisa Burnett
 * @author Matthew Darragh */
public class EntryController {
	@FXML
	private Button mainButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button motivateButton;
	@FXML
	private TextField entryTitle;
	@FXML
	private TextArea entryText;
	@FXML
	private Label titleError;
	@FXML
	private Label bodyError;
	@FXML
	private MenuItem fileClose;
	@FXML
	private Menu menuFile;
	@FXML
	private MenuBar menuBar;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private FileManager CurrUser;
	
	/** Pulls up an existing entry for the user to view and edit.
	 * @param fileName String name of the file to be worked on by this controller.*/
	public void editEntry(String fileName) {
		/** Place previously saved entry in the editable text areas.*/
		entryTitle.setText(fileName);
		entryText.setText(CurrUser.ReadNote(fileName));
	}
	
	/** Pulls up a blank form for the user to work in and save. */
	public void newEntry() {
		/** Empties form for new composition by user. */
		entryTitle.setText("");
		entryText.setText("");
	}
	
	/**Switches to the main menu scene from button, passing the file with the list of journal entries back.
	 * Separate from backToMainMenuBar because the menu item elements must be pulled from the menu bar. 
	 * @param event ActionEvent the event triggering the function (in this case, a mouse click). 
	 * @throws IOException if the file doesn't exist. 
	 * @throws InterruptedException if the scene switch fails. */
	public void backToMain(ActionEvent event) throws IOException, InterruptedException {
		if (checkForSave()) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/MainScreen.fxml"));
			root = loader.load();
		
			MenuController Controller = loader.getController();
			Controller.setData(CurrUser);
			Controller.switchToMainMenu();	
			stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();	
		}
	}
	
	/**Switches to the main menu scene from menu bar, passing the file with the list of journal entries back.
	 * Separate from backToMain because the menu item elements must be pulled from the menu bar. 
	 * @param event ActionEvent the event triggering the function (in this case, a mouse click). 
	 * @throws IOException if the file doesn't exist. */
	public void backToMainMenuBar(ActionEvent event) throws IOException {		
		if (checkForSave()) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/MainScreen.fxml"));
			root = loader.load();
		
			MenuController Controller = loader.getController();
			Controller.setData(CurrUser);
			Controller.switchToMainMenu();		
			stage = (Stage)menuBar.getScene().getWindow(); 
		
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	
	/**Saves the current form contents to the files containing entry titles and entry text. 
	 * @param event ActionEvent the event triggering the function (in this case, a mouse click). 
	 * @throws IOException if the file doesn't exist. */
	public void saveEntry(ActionEvent event) throws IOException{
		/** If the entry title field is not blank, proceed to performing checks to save file. */
		if(!entryTitle.getText().equals("")) {
			titleError.setText("");
			
			/** If the entry title is not already in the list, create a new note and save it. */
			if(!CurrUser.DoesFileExist(entryTitle.getText(), "txt")) {
				CurrUser.NewNote(entryTitle.getText());
				CurrUser.SaveNote(entryTitle.getText(), entryText.getText());
			}
			/** Else if the entry already exists in the list, confirm if the user wants to save, and overwrite the .txt file if they do. */
			else if(CurrUser.DoesFileExist(entryTitle.getText(), "txt")) {
				/** Alert to confirm that user wants to overwrite prior entry. */
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Overwrite File");
				alert.setContentText("Are you sure you want to save over your existing file?");
				Optional<ButtonType> result = alert.showAndWait();
				
				if (result.isPresent() && result.get() == ButtonType.OK ) {
					CurrUser.SaveNote(entryTitle.getText(), entryText.getText());
				}
			}
		}
		/** Else, if the entry title field is blank, display an error message.*/
		else {
			titleError.setText("Error: Title must contain at least one character.");
		}
	}
	
	/**Passes the user data to this scene from another scene.
	 * @param user FileManager object containing the current user's entry information. */
	public void setEntryData(FileManager user) {
		this.CurrUser = user;
	}
	
	/**Pulls a motivational quote from a Motivation object and displays it to the user. 
	 * @param event ActionEvent the event triggering the function (in this case, a mouse click).
	 * @throws IOException if the file doesn't exist. */
	public void motivateEdit(ActionEvent event) throws IOException  {
		Motivation motivateQuotes = new Motivation();
		String quoteToDisplay = motivateQuotes.pickQuote();
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Let's Get Inspired");
		alert.setHeaderText(null);
		alert.setContentText(quoteToDisplay);
		
		alert.showAndWait();
	}
	
	/** Checks if the entry text has changed since the last save and pops up an alert if it has.
	 * @return boolean whether or not the text has changed, and if it should be saved. */
	public boolean checkForSave() {
		if (CurrUser.DoesFileExist(entryTitle.getText(), "txt") && CurrUser.ReadNote(entryTitle.getText()).contentEquals(entryText.getText())) {
			return true;	
		}
		else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Save File");
			alert.setContentText("Are you sure you want to go back to the Main Menu without saving?");
			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.isPresent() && result.get() == ButtonType.OK ) {
				return true;
			}
			return false;
		}
	}
}
