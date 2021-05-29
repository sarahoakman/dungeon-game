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

public class LevelController {
	
	@FXML
	private Pane levelPane;
	
	@FXML
	private Pane levelComplete;
	
	@FXML 
	private Pane levelFail;
	
	@FXML 
	private Pane playerDeath;
	
	@FXML 
	private Button nextLevelButton;
	
	@FXML 
	private Button selectLevelButton;
	
	@FXML 
	private Button replayButton;
	
	private static String level;
	
	public void setUpLevel() throws IOException {
		if (levelPane != null) {
			Stage level = (Stage) levelPane.getScene().getWindow();
			level.close();
		}

		int end = this.level.indexOf(".");
		Stage primaryStage = new Stage();
		primaryStage.setTitle(this.level.substring(0, end));
		

        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(this.level);

        DungeonController controller = dungeonLoader.loadController(this);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void setLevel(String level) {
		this.level = level;
	}
	
	private void setUpNextLevel() throws IOException {
		if (this.level.compareTo("level1.json") == 0) {
			startLevel2(null);
		}
		else if (this.level.compareTo("level2.json") == 0) {
			startLevel3(null);
		}
		else if (this.level.compareTo("level3.json") == 0) {
			startLevel4(null);
		}
		else if (this.level.compareTo("level4.json") == 0) {
			startLevel5(null);
		}
		else if (this.level.compareTo("level5.json") == 0) {
			startLevel6(null);
		}
		else if (this.level.compareTo("level6.json") == 0) {
			startLevel7(null);
		}
		else if (this.level.compareTo("level7.json") == 0) {
			startLevel8(null);
		}
		else if (this.level.compareTo("level8.json") == 0) {
			startLevel9(null);
		}
		else if (this.level.compareTo("level9.json") == 0) {
			startLevel10(null);
		}
		else if (this.level.compareTo("level10.json") == 0) {
			startLevel11(null);
		}
		else {
			startLevel12(null);
		}
	}
	
	@FXML
	public void startLevel1(ActionEvent event) throws IOException {
		setLevel("level1.json");
		setUpLevel();
	}
	
	@FXML
	public void startLevel2(ActionEvent event) throws IOException {
		setLevel("level2.json");
		setUpLevel();
	}
	@FXML
	public void startLevel3(ActionEvent event) throws IOException {
		setLevel("level3.json");
		setUpLevel();
	}
	@FXML
	public void startLevel4(ActionEvent event) throws IOException {
		setLevel("level4.json");
		setUpLevel();
	}
	@FXML
	public void startLevel5(ActionEvent event) throws IOException {
		setLevel("level5.json");
		setUpLevel();
	}
	@FXML
	public void startLevel6(ActionEvent event) throws IOException {
		setLevel("level6.json");
		setUpLevel();
	}
	@FXML
	public void startLevel7(ActionEvent event) throws IOException {
		setLevel("level7.json");
		setUpLevel();
	}
	@FXML
	public void startLevel8(ActionEvent event) throws IOException {
		setLevel("level8.json");
		setUpLevel();
	}
	@FXML
	public void startLevel9(ActionEvent event) throws IOException {
		setLevel("level9.json");
		setUpLevel();
	}
	@FXML
	public void startLevel10(ActionEvent event) throws IOException {
		setLevel("level10.json");
		setUpLevel();
	}
	@FXML
	public void startLevel11(ActionEvent event) throws IOException {
		setLevel("level11.json");
		setUpLevel();
	}
	@FXML
	public void startLevel12(ActionEvent event) throws IOException {
		setLevel("level12.json");
		setUpLevel();
	}
	
	@FXML
	public void replayLevel(ActionEvent event) throws IOException {
		if (levelFail != null) {
			Stage levelStatus = (Stage) levelFail.getScene().getWindow();
			levelStatus.close();
		} else if (playerDeath != null) {
			Stage levelStatus = (Stage) playerDeath.getScene().getWindow();
			levelStatus.close();
		} else {
			Stage levelStatus = (Stage) levelComplete.getScene().getWindow();
			levelStatus.close();
		} 
		setUpLevel();
	}
	
	@FXML
	public void selectLevel(ActionEvent event) throws IOException {
		if (levelFail != null) {
			Stage levelStatus = (Stage) levelFail.getScene().getWindow();
			levelStatus.close();
		} else if (playerDeath != null) {
			Stage levelStatus = (Stage) playerDeath.getScene().getWindow();
			levelStatus.close();
		} else if (levelComplete != null){
			Stage levelStatus = (Stage) levelComplete.getScene().getWindow();
			levelStatus.close();
		}
		DungeonApplication d = new DungeonApplication();
		d.startLevel(new Stage());
	}
	
	@FXML
	public void backToMain() throws IOException {
		setUpMain();
	}

	private void setUpMain() throws IOException {
		Stage ins = (Stage) levelPane.getScene().getWindow();
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
	public void nextLevel(ActionEvent event) throws IOException {
		Stage levelStatus = (Stage) levelComplete.getScene().getWindow();
		levelStatus.close();
		setUpNextLevel();
	}
	
	@FXML
    public void initialize() {
		// removes the play next level button as it's the last level
		if (levelComplete != null) {
			// change to actual last level
			if (this.level.compareTo("level12.json") == 0) {
				levelComplete.getChildren().remove(nextLevelButton);
				selectLevelButton.setTranslateY(-30);
				replayButton.setTranslateY(10);
			}
		}
	}
	
}
