package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

/**main driver for loginForm scene, will probably end up being our main driver to start the program*/
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/view/LoginForm1.fxml"));
			
			Scene scene = new Scene(root,1280,780);
			/**Had lots a problem getting the window size to be adjustable, caused tears and mutation of images used, 
			 * so i set to unresizeable we can talk about setting a consistent scene size throughout, or change in specific areas.*/
			primaryStage.setResizable(false);
			primaryStage.setTitle("Let it Flow Journal");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
