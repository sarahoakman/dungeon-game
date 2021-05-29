package unsw.dungeon;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
    	//loads the main screen of the game
    	startGame(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * The main screen of the game
     * @param primaryStage the stage the window is shown at
     * @throws IOException the exception thrown if the window is not loaded successfully
     */
    public void startGame(Stage primaryStage) throws IOException {
    	primaryStage.setTitle("The Adventures of Billy the Butcher");
    
        
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreen.fxml"));
    	Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        

    }
    
    /**
     * The level selector page of the game
     * @param primaryStage the stage the window is shown at
     * @throws IOException the exception thrown if the window is not loaded successfully
     */
    public void startLevel (Stage primaryStage) throws IOException {
    	primaryStage.setTitle("Select Level");
    	        
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Level.fxml"));
    	Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The window which shows the status after the level
     * @param levelStatus whether the player successfully completed the level or not
     * @param death whether the player died or not
     * @param primaryStage the stage the window is shown at
     * @throws IOException the exception thrown if the window is not loaded successfully
     */
    
    public void endLevel(Stage primaryStage, boolean levelStatus, boolean death) throws IOException {
    	primaryStage.setTitle("Level Complete");
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("LevelComplete.fxml"));
        if (!levelStatus && !death) {
        	primaryStage.setTitle("Goals Failed");
        	loader = new FXMLLoader(getClass().getResource("LevelFail.fxml"));
        } else if (!levelStatus && death) {
        	primaryStage.setTitle("Player Death");
        	loader = new FXMLLoader(getClass().getResource("PlayerDeath.fxml"));
        }
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
}
