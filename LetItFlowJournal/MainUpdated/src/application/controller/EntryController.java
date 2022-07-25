package application.controller;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/** Serves as the controller for the edit entry view of the program.
 * Allows user to compose and edit an entry, as well as save it to a file.
 * Also provides option for motivational pop-up, and allows user to log out or quit the program.
 * 
 * @author Kisa Burnett */
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
	private String entryList;
	private String currentEntry;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	/** Sets the fileName variable.
	 * @param fileName String name of the file to be worked on by this controller.*/
	public void editEntry(String fileName, String allEntries) {
		String entryContents = new String();
		entryList = allEntries;
		currentEntry = fileName;
		
		/** Temporary method to load a test .txt file with an existing entry. */
		try {
			File file = new File("TestFiles/" + currentEntry + ".txt");
			Scanner scan = new Scanner(file);
			
			while(scan.hasNextLine()) {
				entryContents += scan.nextLine();
				entryContents += "\n";
			}
			
			scan.close();
		}
		catch (IOException e) {
			System.out.println("Error: Entry name file not found. Please check the name and try again.");
		}
		
		
		/** Place previously saved entry in the editable text areas.*/
		entryTitle.setText(currentEntry);
		entryText.setText(entryContents);
	}
	
	/**Switches to the main menu scene, passing the file with the list of journal entries back.
	 * */
	public void backToMain(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/MainScreen.fxml"));
		root = loader.load();
		
		MenuController menuController = loader.getController();
		menuController.switchToMainMenu(entryList);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/* TO-DO:
	 * Save function
	 * Log out
	 * Quit
	 * Generate motivational quote from motivation class*/
}
