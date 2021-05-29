package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {
	
    private List<ImageView> entities;

    //Images
    private Image playerImage;
    private Image wallImage;

    private Image keyImage;
    private Image doorImage;
    private Image exitImage;
    private Image swordImage;
    private Image enemyImage;
    private Image boulderImage;
    private Image switchImage;
    private Image potionImage;
    private Image treasureImage;
    private Image portalImage;
    private Image leprechaunImage;
    private Image wizardImage;
    private Image laserImage;
    private Image shieldImage;
	private Image flameThrowerImage;

    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image("/human_new.png");
        wallImage = new Image("/brick_brown_0.png");
        
        keyImage = new Image("/key.png");
        doorImage = new Image("/closed_door.png");
        exitImage = new Image("/exit.png");
        swordImage = new Image("/greatsword_1_new.png");
        enemyImage = new Image("/deep_elf_master_archer.png");
        boulderImage = new Image("/boulder.png");
        switchImage = new Image("/pressure_plate.png");
        potionImage = new Image("/brilliant_blue_new.png");
        treasureImage = new Image("/treasure.gif");
        portalImage = new Image("/portal.png");
        leprechaunImage = new Image("/leprechaun.png");
        wizardImage = new Image("/wizard.png");
        laserImage = new Image("/laser.png");
        shieldImage = new Image("/shield.png");
        flameThrowerImage = new Image("/gun.png");
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        view.setId("player");
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        view.setId("key");
        addEntity(key, view);
    }
    
    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorImage);
        addEntity(door, view);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        view.setId("sword");
        addEntity(sword, view);
    }

    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
    }
    
    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }

    @Override
    public void onLoad(Switch fswitch) {
        ImageView view = new ImageView(switchImage);
        addEntity(fswitch, view);
    }
    @Override
    public void onLoad(Potion potion) {
        ImageView view = new ImageView(potionImage);
        view.setId("potion");
        addEntity(potion, view);
    }
    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        view.setId("treasure");
        addEntity(treasure, view);
    }
    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }
    
    @Override
    public void onLoad(Leprechaun leprechaun) {
        ImageView view = new ImageView(leprechaunImage);
        addEntity(leprechaun, view);
    }
    
    @Override
    public void onLoad(Laser laser) {
        ImageView view = new ImageView(laserImage);
        addEntity(laser, view);
    }
    
    @Override
    public void onLoad(Wizard wizard) {
        ImageView view = new ImageView(wizardImage);
        addEntity(wizard, view);
    }

    @Override
    public void onLoad(Shield shield) {
        ImageView view = new ImageView(shieldImage);
        view.setId("shield");
        addEntity(shield, view);
    }
    
    public void onLoad(FlameThrower flameThrower) {
        ImageView view = new ImageView(flameThrowerImage);
        view.setId("flameThrower");
        addEntity(flameThrower, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity the entity to be tracked
     * @param node the location the entity is in
     */
    private void trackPosition(Entity entity, Node node) {
    	// track the position
    	GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
        //listens for invincible players
        entity.isInvincible().addListener(e -> {
        	ImageView i = (ImageView) node;
        	if (entity.isInvincible().get()) {
            	i.setImage(new Image("/human_new_invincible.png"));
        		//i.setImage(new Image("/gnome.png"));
            } else {
            	i.setImage(new Image("/human_new.png"));
            }
        });
        // listens for entities disappearing
        entity.isExists().addListener(e -> {
        	ImageView i = (ImageView) node;
        	if (!entity.isExists().get()) {
        		i.setImage(null);
            } 
        });
        // listens for unlocking the door
        if (entity.isLockable() && !entity.isPickable()) {
        	Door door = (Door) entity;
        	door.isLocked().addListener(e -> {
        		ImageView i = (ImageView) node;
        		i.setImage(new Image("/open_door.png"));
        	});
        }
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController(LevelController level) throws FileNotFoundException {
        return new DungeonController(load(), entities, level, this.getMainGoal(), this.getSubgoals());
    }


}