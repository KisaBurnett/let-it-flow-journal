package application.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

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
	
	private String entryList;
	private String currentEntry;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	/** Pulls up an existing entry for the user to view and edit.
	 * @param fileName String name of the file to be worked on by this controller.
	 * @param allEntries String name of .csv list of entry names. */
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
	
	/** Pulls up a blank form for the user to work in and save.
	 * @param allEntries String name of .csv list of entry names. */
	public void newEntry(String allEntries) {
		entryList = allEntries;
		
		/** Empties form for new composition by user. */
		entryTitle.setText("");
		entryText.setText("");
	}
	
	/**Switches to the main menu scene from button, passing the file with the list of journal entries back.
	 * Separate from backToMainMenuBar because the menu item elements must be pulled from the menu bar. */
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
	
	/**Switches to the main menu scene from menu bar, passing the file with the list of journal entries back.
	 * Separate from backToMain because the menu item elements must be pulled from the menu bar. */
	public void backToMainMenuBar(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/MainScreen.fxml"));
		root = loader.load();
		
		MenuController menuController = loader.getController();
		menuController.switchToMainMenu(entryList);
		
		stage = (Stage)menuBar.getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/**Saves the current form contents to the files containing entry titles and entry text. */
	public void saveEntry(ActionEvent event) throws IOException{
		String toWrite = new String();
		ArrayList<String> entries = new ArrayList<String>();
		Boolean alreadyInList = false;
		
		/** Temporary method to load a test .csv file with a list of journal entry names. */
		try {
			File file = new File(entryList);
			Scanner scan = new Scanner(file);
			
			while(scan.hasNextLine()) {
				entries.add(scan.nextLine());
			}
			
			scan.close();
		}
		catch (IOException e) {
			System.out.println("Error: Entry name file not found. Please check the name and try again.");
		}
		
		/** Check and see if the title already exists in the list of entries. */
		for(int ndx = 0; ndx < entries.size(); ndx++) {
			if(entries.get(ndx).equals(entryTitle.getText())) {
				alreadyInList = true;
			}
		}
		
		/** If the entry title field is not blank, proceed to performing checks to save file. */
		if(!entryTitle.getText().equals("")) {
			titleError.setText("");
			
			/** If there are existing entries and the entry title is not already in the list, append the new title and write to .csv file. */
			if(entries.size() > 0 && !entries.get(0).equals("") && !alreadyInList) {
				/** Save updated list of entries. */
				try {
					File entryListFile = new File(entryList);
					FileWriter printer = new FileWriter(entryListFile, false);
					
					for(int ndx = 0; ndx < entries.size(); ndx++) {
						toWrite = toWrite + entries.get(ndx) + "\n";
					}
					
					toWrite = toWrite + entryTitle.getText();
					printer.write(toWrite);
					
					printer.close();
				} catch (IOException e) {
					System.out.println("Error: Entry name file not found. Please check the name and try again.");
				}
				
				/** Save  a new .txt file */
				try {
					File entryContents = new File("TestFiles/" + entryTitle.getText() + ".txt");
					boolean isFileCreated = entryContents.createNewFile();
					System.out.println(isFileCreated);
					System.out.println("TestFiles/" + entryTitle.getText() + ".txt");
					FileWriter printer = new FileWriter(entryContents, false);
					
					printer.write(entryText.getText());
					
					printer.close();
				} catch (IOException e) {
					System.out.println("Error: Entry text file not found. Please check the name and try again.");
				}
			}
			/** Else if the file is empty or only contains a blank line, overwrite the file with the entry title. */
			else if(entries.size() == 0 || entries.get(0).equals("")) {
				/** Save updated list of entries. */
				try {
					File entryListFile = new File(entryList);
					FileWriter printer = new FileWriter(entryListFile, false);
					
					toWrite = entryTitle.getText();
					printer.write(toWrite);
					
					printer.close();
				} catch (IOException e) {
					System.out.println("Error: Entry name file not found. Please check the name and try again.");
				}
				
				/** Save a new .txt file */
				try {
					File entryContents = new File("TestFiles/" + entryTitle.getText() + ".txt");
					boolean isFileCreated = entryContents.createNewFile();
					System.out.println(isFileCreated);
					System.out.println("TestFiles/" + entryTitle.getText() + ".txt");
					FileWriter printer = new FileWriter(entryContents, false);
					
					printer.write(entryText.getText());
					
					printer.close();
				} catch (IOException e) {
					System.out.println("Error: Entry text file not found. Please check the name and try again.");
				}
			}
			/** Else if the entry already exists in the list, confirm if the user wants to save, and overwrite the .txt file if they do. */
			else if(alreadyInList) {
				/** Alert to confirm that user wants to overwrite prior entry. */
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Overwrite File");
				alert.setContentText("Are you sure you want to save over your existing file?");
				Optional<ButtonType> result = alert.showAndWait();
				
				if (result.isPresent() && result.get() == ButtonType.OK ) {
					/** Save updated .txt file */
					try {
						File entryContents = new File("TestFiles/" + entryTitle.getText() + ".txt");
						FileWriter printer = new FileWriter(entryContents, false);
						
						printer.write(entryText.getText());
						
						printer.close();
					} catch (IOException e) {
						System.out.println("Error: Entry text file not found. Please check the name and try again.");
					}
				}
			}
		}
		/** Else, if the entry title field is blank, display an error message.*/
		else {
			titleError.setText("Error: Title must contain at least one character.");
		}
	}
	
	/* TO-DO:
	 * Generate motivational quote from motivation class*/
}
