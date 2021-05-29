package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainScreenController {
	@FXML
	private Pane mainScreen;
	
	@FXML 
	private Button playButton;
	
	
	@FXML
	private Button instructionsButton;
	
	private void setUpLevelScreens() throws IOException {
		Stage main = (Stage) mainScreen.getScene().getWindow();
		main.close();
		DropShadow shadow = new DropShadow();
		
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Dungeon Level Selector");
	
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("Level.fxml"));
	    
	    Parent root = loader.load();
	    Scene scene = new Scene(root);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    
	    playButton.setOnMouseEntered(e -> playButton.setEffect(shadow));
	    playButton.setOnMouseExited(e -> playButton.setEffect(null));
	}
	    
    @FXML
	public void startGame(ActionEvent event) throws IOException {
    	setUpLevelScreens();
	}
    
    private void showInstructions() throws IOException {
    	Stage main = (Stage) mainScreen.getScene().getWindow();
		main.close();
		Stage primaryStage = new Stage();
    	primaryStage.setTitle("How to play");
    
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Instructions.fxml"));
    	Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @FXML
	public void showGameInstructions(ActionEvent event) throws IOException {
    	showInstructions();
	}
	

}
