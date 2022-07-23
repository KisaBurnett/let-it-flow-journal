package application.controller;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
	
	/** Sets the fileName variable.
	 * @param fileName String name of the file to be worked on by this controller.*/
	public void editEntry(String fileName) {
		String entryContents = new String();
		
		/** Temporary method to load a test .txt file with an existing entry. */
		try {
			File file = new File("TestFiles/" + fileName + ".txt");
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
		entryTitle.setText(fileName);
		entryText.setText(entryContents);
	}
}
