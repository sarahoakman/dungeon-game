package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class InstructionsController {
	@FXML
	private Pane instructions;
	
	@FXML 
	private Button backButton;
	
	public void backtoMain() throws IOException {
    	Stage ins = (Stage) instructions.getScene().getWindow();
		ins.close();
		Stage primaryStage = new Stage();
    	primaryStage.setTitle("The adventures of Billy the butcher");        
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreen.fxml"));
    	Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	@FXML
	public void backtoScreen(ActionEvent event) throws IOException {
		backtoMain();
	}
}
