package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/** TEST SCENE ONLY. WILL BE REMOVED PRIOR TO LAUNCH.
 * @author Kisa Burnett*/

public class TestLaunchController {
	@FXML
	private Button openButton;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	/** Switches to the main menu of the app and passes the file name needed to access the list of entries.
	 * Would be best to start with an empty file, but the error check in MenuController should handle cases
	 * where the file is not found, too. Haven't tested this extensively yet. */
	public void switchToMain(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/MainScreen.fxml"));
		root = loader.load();
		
		MenuController menuController = loader.getController();
		menuController.switchToMainMenu("TestFiles/entryNames.csv");
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
