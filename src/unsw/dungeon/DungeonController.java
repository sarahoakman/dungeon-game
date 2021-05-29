package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;
    
    @FXML 
    private Button replayButton;
    
    @FXML 
    private Button levelButton;
    
    @FXML
    private BorderPane border;
    
    @FXML
    private GridPane goalsBox;
    
    private FlowPane inventory;
    
    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;
    
    private LevelController level;
    
    private int time;
    
    private Text timer;
    
    private Text turns;

	private String goal;

	private List<String> subgoals;
	
	private int goalCount = 1;
    
    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, LevelController level, String goal, List<String> subgoals) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.level = level;
        this.goal = goal;
        this.subgoals = subgoals;
    }
    
    @FXML
    public void initialize() {
        Image ground = new Image("/dirt_0_new.png");

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }
        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);
        addFlowPane();
        border.setRight(inventory);
        
        addLevelListener(dungeon.getLevel());
        addPlayerDeathListener(dungeon.getPlayer(), dungeon.getLevel());
        listenerReplayButton();
        listenerLevelButton();
        showgoals();
    }
    
    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            player.moveUp();
            updateLeprechaun();
            break;
        case DOWN:
            player.moveDown();
            updateLeprechaun();
            break;
        case LEFT:
            player.moveLeft();
            updateLeprechaun();
            break;
        case RIGHT:
            player.moveRight();
            updateLeprechaun();
            
            break;
        case SPACE:
        	Entity e = player.interact();
        	pickUpEntity(e);
        	updateSwordTurns();
        	checkFlameThrower(e);
        	break;
        default:
            break;
        }
        
    }
    
    /**
     * This function shows what goals have to be accomplished in the level
     * @param s the string saved in the json file
     */
    private void compareGoal(String s) {
    	//if multiple goals have to be completed
    	if (s.compareTo("AND") == 0) {

    		Text goal = new Text("Complete all the following goals:");
    		goal.setFont(Font.font("Georgia", 16));
    		goal.setFill(Color.WHITE);
        	
    		
        	TextFlow goalPane = new TextFlow();
        	goalPane.getChildren().add(goal);
        	goalsBox.add(goalPane, 0, goalCount);
        	goalCount++;

		//if one or more goals have to be completed
    	} else if (s.compareTo("OR") == 0) {

    		Text goal = new Text("Complete one of the following goals:");
    		goal.setFont(Font.font("Georgia", 16));
    		goal.setFill(Color.WHITE);
        	
    		
        	TextFlow goalPane = new TextFlow();
        	goalPane.getChildren().add(goal);
        	goalsBox.add(goalPane, 0, goalCount);
        	goalCount++;
        
        
        //If the goal is to collect all treasure	
    	} else if (s.compareTo("treasure") == 0) {
    		Text goal = new Text("Collect all treasure");
    		goal.setFont(Font.font("Georgia", 15));
    		goal.setFill(Color.WHITE);
        	
    		Image pic = new Image("/treasure.gif");
    		ImageView view = new ImageView(pic);
    		
        	TextFlow goalPane = new TextFlow();
        	goalPane.getChildren().add(view);
        	goalPane.getChildren().add(goal);
        	goalsBox.add(goalPane, 0, goalCount);
        	goalCount++;

        	
        //if the goal is to kill all enemies
    	} else if (s.compareTo("enemies") == 0) {
    		Text goal = new Text("Kill all enemies");
    		goal.setFont(Font.font("Georgia", 15));
    		goal.setFill(Color.WHITE);
    		
    		Image pic = new Image("/deep_elf_master_archer.png");
    		ImageView view = new ImageView(pic);
    		
        	TextFlow goalPane = new TextFlow();
        	goalPane.getChildren().add(view);
        	
        	goalPane.getChildren().add(goal);
        	
        	goalsBox.add(goalPane, 0, goalCount);
        	goalCount++;
        	
        //if the goal is to cover all floor switches with boulders
    	} else if (s.compareTo("boulders") == 0) {
    		Text goal = new Text("Push all boulders onto switches");
    		goal.setFont(Font.font("Georgia", 15));
    		goal.setFill(Color.WHITE);
    		
    		Image pic = new Image("/pressure_plate.png");
    		ImageView view = new ImageView(pic);
    		
        	TextFlow goalPane = new TextFlow();
        	goalPane.getChildren().add(view);
        	
        	goalPane.getChildren().add(goal);
        	goalsBox.add(goalPane, 0, goalCount);
        	goalCount++;
        	
        //if the goal is to reach the exit
    	} else if (s.compareTo("exit") == 0) {
    		Text goal = new Text("Reach the exit");
    		goal.setFont(Font.font("Georgia", 15));
    		goal.setFill(Color.WHITE);
        	
    		Image pic = new Image("/exit.png");
    		ImageView view = new ImageView(pic);
    		
        	TextFlow goalPane = new TextFlow();
        	goalPane.getChildren().add(view);
        	
        	goalPane.getChildren().add(goal);
        	goalsBox.add(goalPane, 0, goalCount);
        	goalCount++;
    	}
    }
    
    /**
     * Goes through all the goals in the level and shows them on screen
     */
    private void showgoals() {
    	
    	if (subgoals.size() == 0) {
    		compareGoal(goal);
    	} else {
    		compareGoal(goal);
    		for (String s: subgoals) {
    			compareGoal(s);
    		}
    	}
    }

    /**
     * This function sets up the timer for the potion for 10 seconds and shows it next to the potion
     * @param i the image of the potion
     */
    private void potionTimer(ImageView i) {
    	this.time = 10;
    	this.timer = new Text(String.valueOf(time));
    	timer.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
    	timer.setFill(Color.WHITE);
    	
    	StackPane timerPane = new StackPane();
    	timerPane.getChildren().add(i);
    	timerPane.getChildren().add(timer);
    	timerPane.setAlignment(Pos.BOTTOM_RIGHT);
    	inventory.getChildren().add(timerPane);
    	// sets the duration to 1 seconds
        Duration duration = Duration.millis(1000);
        // create the event handler which decrements time 
        EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	// decrement time every 10 seconds
                time--;
                timer.setText(String.valueOf(time));
                if (time == 0) {
                	timer.setText(null);
                }
            }
        };
        // create the key frame
        KeyFrame keyFrame = new KeyFrame(duration, onFinished);
        // create the timeline and set it to repeat indefinitely 
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(10);
        timeline.play();
	}
    
    /**
     * This function adds the number of turns next to the sword
     * @param i the image of the sword turns need to be added next to
     */
    private void swordTurnCount(ImageView i) {
    	this.turns = new Text(String.valueOf(time));
    	turns.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
    	turns.setFill(Color.WHITE);
    	
    	StackPane turnPane = new StackPane();
    	turnPane.getChildren().add(i);
    	turnPane.getChildren().add(turns);
    	turnPane.setAlignment(Pos.BOTTOM_RIGHT);
    	inventory.getChildren().add(turnPane);
    	updateSwordTurns();
    	
    }
    
    /**
     * This function gets the number of turns left per sword
     */
    private void updateSwordTurns() {
    	//if there are no swords currently setup in the inventory
    	if (turns == null) {
    		return;
    	}
    	int turn = player.getSwordTurns();
    	turns.setText(String.valueOf(turn));
    	if (turn == 0) {
        	turns.setText(null);
        }
    }

	
	/**
	 * This function adds the inventory to the dungeon screen
	 */
	private void addFlowPane() {
	    this.inventory = new FlowPane();
	    inventory.setVgap(4);
	    inventory.setHgap(4);
	    inventory.setPrefWrapLength(75); 
	    inventory.setStyle("-fx-background-image: url('/inven.jpg');");
	}
	
	/**
	 * This function takes the user back to the level select page
	 */
    private void listenerLevelButton() {
    	levelButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	try {
					Stage dungeon = (Stage) squares.getScene().getWindow();
					dungeon.close();
					level.selectLevel(null);;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    }
		});
	}
    
    /**
     * This function allows the user to replay the level after clicking the button 
     */
	private void listenerReplayButton() {
    	replayButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	try {
					Stage dungeon = (Stage) squares.getScene().getWindow();
					dungeon.close();
					level.setUpLevel();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    }
		});
    }
    
	/**
	 * This function checks if a flame thrower is fired and adds the images to the dungeon
	 * @param e the entity picked up by the player
	 */
    private void checkFlameThrower(Entity e) {
    	if (e != null && !e.isExists().get()) {
    		//if the entity is a flame thrower
    		if (e.isPickable() && e.isTriggerable()) {
    			FlameThrower f = (FlameThrower) e;
    			Image fireImage = new Image("/fire.gif");
	    		for (int x = f.getMinX() + 1; x < f.getMaxX(); x++) {
	    			dungeon.addEntity(new Fire(x, f.getPlayerY()));
	    			ImageView fire = new ImageView();
	        		fire.setImage(fireImage);
	        		fire.setId("fire");
	        		GridPane.setColumnIndex(fire, x);
	        		GridPane.setRowIndex(fire, f.getPlayerY());
	        		squares.getChildren().add(fire);
	    		}
	    		playerToFront();
    		}
		}
	}

    /**
     * This function allows users to pickup an entity from the board
     * @param e the entity to be picked up
     */
	private void pickUpEntity(Entity e) {
    	// if an entity was picked up add it to the inventory
    	if (e != null) {
    		ImageView i = getImageViewDungeon(e);
    		if (i != null) {
    			if (i.getId().compareTo("sword") == 0) {
    				swordTurnCount(i);
    			}
    			else if (i.getId().compareTo("potion") == 0) {
    				potionTimer(i);
    			} else if (i.getId().compareTo("key") == 0) {
    				if (player.findInventory(e)) {
    					inventory.getChildren().add(i);
    				} else {
    					dropKey(i, e);
    				}
    			} else {
    				inventory.getChildren().add(i);
    			}
    		} else {
    			if (e.isPickable() && e.isLockable()) {
    				dropKey(i, e);
    			}
    		}
    	}
	}
    
	/**
	 * This function allows the player to drop a key 
	 * @param i the image of the key
	 * @param e the key to be dropped
	 */
	private void dropKey(ImageView i, Entity e) {
		i = getImageViewInventory(e);
		inventory.getChildren().remove(i);
		// add to board if the door is still unlocked 
		Door door = dungeon.findDoorWithId(((Key) e).getId());
		if (door.getLocked()) { 
			squares.getChildren().add(i);
			fireToFront();
			playerToFront();
		}
	}

	/**
	 * This function finds the node corresponding with the entity picked up in dungeon
	 * @param e the entity to be picked up
	 * @return the image of the entity to be picked
	 */
    public ImageView getImageViewDungeon(Entity e) {
    	for (Node node : squares.getChildren()) {
    		if (GridPane.getColumnIndex(node) == e.getX() && GridPane.getRowIndex(node) == e.getY()) {
    			if (node.getId() != null && node.getId().compareTo("sword") == 0) {
    				return (ImageView) node;
    			} else if (node.getId() != null && node.getId().compareTo("key") == 0) {
    				return (ImageView) node;
    			} else if (node.getId() != null && node.getId().compareTo("treasure") == 0) {
    				return (ImageView) node;
    			} else if (node.getId() != null && node.getId().compareTo("potion") == 0) {
    				return (ImageView) node;
    			} else if (node.getId() != null && node.getId().compareTo("shield") == 0) {
    				return (ImageView) node;
    			} else if (node.getId() != null && node.getId().compareTo("flameThrower") == 0) {
    				return (ImageView) node;
    			} 
    		}
    	}
    	return null;
    }
    
 // function to find the node corresponding with the entity in the inventory
    public ImageView getImageViewInventory(Entity e) {
    	for (Node node : inventory.getChildren()) {
    		if (node.getId() != null && node.getId().compareTo("key") == 0) {
    			return (ImageView) node;
    		}
    	}
    	return null;
    }
    
    /**
     * This function closes the dungeon
     */
    private void endLevel() {
    	Stage dungeon = (Stage) squares.getScene().getWindow();
    	dungeon.close();
    }
    
    /**
     * This function checks if the level is completed or not
     * @param level the level currently being played
     */
    private void addLevelListener(Level level) {
    	level.isStatus().addListener(e -> {
    		if (level.getStatus()) {
    			DungeonApplication d = new DungeonApplication();
    			try {
    				endLevel();
    				d.endLevel(new Stage(), level.getStatus(), false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    		}
    	});
    }
    
    /**
     * This function checks if the player has been killed
     * @param player the player of the game
     * @param level the level being played
     */
    private void addPlayerDeathListener(Player player, Level level) {
    	
    	player.isAlive().addListener(e -> {
    		boolean death = true;
    		if (player.isExit()) {
    			death = false;
    		} 
    		if (!player.getAlive()  && !level.getStatus()) {
    			DungeonApplication d = new DungeonApplication();
    			try {
    				// uncomment the line below when the functions finished
					//d.endLevel(new Stage(), level.getStatus());
    				endLevel();
    				d.endLevel(new Stage(), false, death);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    		}
    	});
	}
    
    /**
     * This function updates the image of the leprachaun according to the player
     */
    private void updateLeprechaun() {
    	if (player.checkLeprechaun() != null) {
	    	List<ImageView> i = new ArrayList<ImageView>();
	    	for (Node node : inventory.getChildren()) {
	    		if (node.getId() != null && node.getId().compareTo("treasure") == 0) {
					i.add((ImageView) node);
				} 
	    	}
	    	if (i.size() != 0) {
	    		for (ImageView view : i) {
	    			squares.getChildren().add(view);
	    			fireToFront();
	    			playerToFront();
	    		}
	    	}
	    }
    }
    
    /**
     * This function moves player to front, called after entities are added to the squares
     */
   	private void playerToFront() {
   		Node player = null;
   		for (Node node : squares.getChildren()) {
       		if (node.getId() != null && node.getId().compareTo("player") == 0) {
   				player = node;
   				break;
   			} 
       	}
   		squares.getChildren().remove(player);
   		squares.getChildren().add(player);
   	}
   	
   	/**
     * This function moves fire to front, called after entities are added to the squares
     */
   	private void fireToFront() {
   		List<Node> fire = new ArrayList<Node>();
   		for (Node node : squares.getChildren()) {
       		if (node.getId() != null && node.getId().compareTo("fire") == 0) {
   				fire.add(node);
   				break;
   			} 
       	}
   		for (Node node : fire) {
   			squares.getChildren().remove(node);
   	   		squares.getChildren().add(node);
   		}
   	}

}

