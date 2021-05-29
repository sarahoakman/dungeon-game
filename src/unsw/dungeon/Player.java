package unsw.dungeon;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

    private Dungeon dungeon;
    private Inventory inventory;
    private PlayerState playerState;
    //For key pressed: 0 = up, 1 = down, 2 = left, 3 = right, 4 = space, 5 = enter
    private int	keyPressed;
    private BooleanProperty alive;
    private boolean exit;
    
    
    
    /**
     * The constructor creates a player positioned in square (x,y)
     * @param x the x coordinate of the player
     * @param y the y coordinate of the player
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.alive = new SimpleBooleanProperty(true);
        this.inventory = new Inventory();
        this.playerState = new PlayerNormal();
        setBlockable(true);
        setMovable(true);
        setKillable(true);
        setTeleportable(true);
        this.exit = false;
    }

	/**
	 * This function sets the state of the player depending on whether they have the invincibility potion
	 * @param newPlayerState the state of the player being changed into 
	 */
    public void setPlayerState (PlayerState newPlayerState) {
    	this.playerState = newPlayerState;
    }
    
    /**
     * This function sets the state of the player to invincible
     */
    public void setInvinciblePlayer() {
    	setPlayerState(new PlayerInvincible());
    	setInvincible(true);
    }
    
    /**
     * This function sets the state of the player to normal
     */
    public void setNormalPlayer() {
    	setPlayerState(new PlayerNormal());
    	setInvincible(false);
    }
    
    /**
     * This function sets the key which was pressed by the player for moving in a given direction
     * @param type the direction the player is moving towards
     */
    private void setKeyPressed(int type) {
    	this.keyPressed = type;
    }
    
    /**
     * This function gets which key was pressed by the player
     * @return the key which was pressed
     */
    public int getKeyPressed() {
    	return this.keyPressed;
    }
    
    /**
     * Getter method for the alive boolean property
     * @return the alive status as a boolean property
     */
	public BooleanProperty isAlive() {
		return this.alive;
	}
	
	/**
     * gets the alive status as a boolean
     * @return the alive status as a boolean
     */
	public boolean getAlive() {
		return this.alive.get();
	}
    
    /**
     * This function makes the player move up if the location is available on the board
     * and notifies the dungeon to check all the other entites on the board to act accordingly
     */
    public void moveUp() {
        if (getY() > 0 && checkMove(0)) {
            y().set(getY() - 1);
            setKeyPressed(0);
            dungeon.Notify();
        }
    }

    /**
     * This function makes the player move down if the location is available on the board
     * and notifies the dungeon to check all the other entites on the board to act accordingly
     */
    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1  && checkMove(1)) {
        	y().set(getY() + 1);
            setKeyPressed(1);
            dungeon.Notify();
    	}
    }
    
    /**
     * This function makes the player move left if the location is available on the board
     * and notifies the dungeon to check all the other entites on the board to act accordingly
     */
    public void moveLeft() {
        if (getX() > 0 && checkMove(2)) {
        	x().set(getX() - 1);
        	setKeyPressed(2);
        	dungeon.Notify();
        }
    }

    /**
     * This function makes the player move right if the location is available on the board
     * and notifies the dungeon to check all the other entites on the board to act accordingly
     */
    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1 && checkMove(3)) {
            x().set(getX() + 1);
            setKeyPressed(3);
            dungeon.Notify();
        }
    }
    
    /**
     * This function checks the movement of the player 
     * @param type the direction the player has to move in where 0 = up, 1 = down, 2 = right, 3 = left
     * @return whether the square is empty for the player to move into
     */
    public boolean checkMove(int type) {
    	int x = getX();
		int y = getY();
    	if (type == 0) {
    		 y--;
    	} else if (type == 1) {
    		y++;
    	} else if (type == 2) {
    		x--;
    	} else {
    		x++;
    	}
    	return dungeon.checkSquare(type, x, y);
    }
    
    /**
     * This function checks if the square adjacent to the player is empty
     * @param x the x coordinate of the location to be checked
     * @param y the y coordinate of the location to be checked
     * @return whether the adjacent square is empty or not
     */
    public boolean checkAdjacent(int x, int y) {
    	if (getX() + 1 == x && getY() == y) {
    		return true;
    	} else if (getX() - 1 == x && getY() == y) {
    		return true;
    	} else if (getX() == x && getY() + 1 == y) {
    		return true;
    	} else if (getX() == x && getY() - 1 == y) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Interact with entities when the space bar is pressed
     * Also notifies a dungeon when a change occurs
     */
    public Entity interact() {
    	// initialise an entity to null
    	Entity entity = null;
    	// set the space bar as being pressed
    	setKeyPressed(4);
    	// check what is in the square
    	PickUpStrategy pickable = dungeon.getPickableEntity(getX(), getY());
    	
    	// picks up or drops the key
    	Entity e = (Entity) pickable;
    	// no pickable item found on the square
    	if (e == null) {
    		if (inventory.checkKey()) {
    			entity = dropKey();
    		} else {
    			Sword s = inventory.getSword();
        		if (s != null) {
        			//the sword hits the enemy
        			boolean hitEntity = s.hitEnemy(this);
        			boolean hitWizards = s.hitWizard(this);
        			if (!hitEntity && !hitWizards) {
        				FlameThrower f = inventory.getFlameThrower();
        				if (f != null) {
        					entity = f;
        					f.shootFlame(this);
        				}
        			}
        		} else {
        			FlameThrower f = inventory.getFlameThrower();
    				if (f != null) {
    					entity = f;
    					f.shootFlame(this);
    				}
        		}
    		}
    	// pick up the item
    	} else {
    		entity = pickable.pickUp(this);
    	}
    	dungeon.Notify();
    	return entity;
    }

    /**
     * This function adds an entity to the dungeon
     * @param e the entity to be added
     */
    public void addToDungeon(Entity e) {
    	dungeon.addEntity(e);
    }
    
    /**
     * This function adds an entity to the inventory
     * @param e the entity to be added
     */
    public void addToInventory(Entity e) {
    	inventory.addEntity(e);
    }
    
    /**
     * This function removes an entity to the dungeon
     * @param e the entity to be removed
     */
    public void removeFromDungeon(Entity e) {
		dungeon.removeEntity(e);
	}
    
    /**
     * Checks if a sword is in the inventory
     * @return boolean
     */
    public boolean checkSword() {
    	return inventory.checkSword();
    }
    
    /**
     * Checks if a sword is in the inventory
     * @return boolean
     */
    public boolean checkPotion() {
    	return inventory.checkPotion();
    }
    
    /**
     * Checks if a sword is in the inventory
     * @return boolean
     */
    public boolean checkKey() {
    	return inventory.checkKey();
    }
    
    /**
     * Gets the key from the inventory and then drops it
     */
    private Entity dropKey() {
		Key key = inventory.getKey();
		key.drop(this);
		return key;
    }
    
    /**
     * This function updates the goals of the player
     */
    public void updateGoals() {
		dungeon.updateGoals();
	}
    
    /**
     * This function collides with the enemy and removes them or the player is killed depending on the player's state
     */
    public void collideEnemy(Enemy enemy) {
    	boolean status = playerState.collideEnemy(enemy, dungeon);
    	if (!status) {
    		this.alive.set(false);
    	}
    }
    

    public void collideLaser(Laser laser) {
    	boolean status = playerState.collideLaser(laser, dungeon, this);
    	if (!status) {
    		this.alive.set(false);
    	}
    }
    
    public void collideWizard(Wizard wizard) {
    	boolean status = playerState.collideWizard(wizard, dungeon,this);
    	if (!status) {
    		this.alive.set(false);
    	}
    }

    
    public void collideFire() {
    	if (playerState.collideFire()) {
    		this.alive.set(false);
    	}
    }
    
    /**
     * This function checks if the player is pushing a boulder when walking
     * @return whether the player is pushing a boulder
     */
    public boolean checkBoulder() {
    	MoveStrategy boulder = dungeon.getBoulder(getX(), getY());
    	if (boulder != null) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * This function updates all the switches in the dungeon
     */
    public void updateSwitches() {
    	dungeon.updateSwitches();
    }
    
    /**
     * This function sets the location of the player to the given coordinates
     * @param x the x coordinate where the player should move to
     * @param y the y coordinate where the player should move to
     */
    public void teleportPlayer(int x,int y) {
    	x().set(x);
    	y().set(y);
    }
    
   

	/**
	 * This function removes an entity from the player's inventory
	 * @param e the entity to be removed
	 */
	public void removefromInventory(Entity e) {
		inventory.removeEntity(e);
	}
    
	/**
	 * This function checks if a square is empty on the board
	 * @param x the x position of the entity to be moved
	 * @param y the y position of the entity to be moved
	 * @return whether that position in the dungeon is empty
	 */
	public boolean checkEmpty(int x, int y) {
		return dungeon.freeSquare(x, y);
	}
	
	/**
	 * This function allows the player to pickup different entities in the dungeon
	 * @param collectStrategy the object which the player has to pick up
	 */
    public void pickUp(PickUpStrategy collectStrategy) {
    	collectStrategy.pickUp(this);
    }
    
    /**
     * This function exits the game once the player has been killed
     */
    public void removePlayer() {
    	alive.set(false);
    }
    
    /**
     * This function sets the movement state of the enemy depending on 
     * whether the player is invincible or not
     * @param enemyState the movement state of the enemy
     */
	public void setEnemyState(MoveStrategy enemyState) {
		dungeon.setEnemyState(enemyState);
	}
	
	/**
	 * This function finds an entity in the inventory
	 * @param e the entity to be found
	 * @return whether the entity was found in the inventory
	 */
	public boolean findInventory(Entity e) {
		return inventory.itemFound(e);
	}
	
	/**
	 * This function finds an entity in the dungeon
	 * @param e the entity to be found
	 * @return whether the entity was found in the dungeon
	 */
	public boolean findDungeon(Entity e) {
		return dungeon.itemFound(e);
	}
	
	/**
	 * This function finds the closest enemy in the dungeon
	 * @return The enemy that was found
	 */
	public Enemy findEnemy() {
		return dungeon.findEnemy();
	}
	
	public Wizard findWizard() {
		return dungeon.findWizard();
	}
	
	/**
	 * This function checks whether all the treasure has been collected by the player
	 * @return whether there is any treasure left in the dungeon
	 */
	public boolean collectedAllTreasure() {
		return dungeon.noTreasureExist();
	}
	
	/**
	 * This function check if all the enemies in the dungeon are dead
	 * @return whether there are any enemies left
	 */
	public boolean checkNoEnemiesleft() {
		return dungeon.noEnemyExist();
	}

	/**
	 * This function checks if all the floor switches have been triggered
	 * @return whether the floor switches in the dungeon have a boulder on them
	 */
	public boolean completeSwitches() {
		return dungeon.allTriggeredSwitches();
	}
	
	/**
	 * This function checks if the player has reached the exit
	 * @return whether the player has reached the exit
	 */
	public boolean reachExit() {
		return dungeon.checkExit();
	}
	
	/**
	 * This function locates the other portal on the board
	 * @return whether a pair portal was found
	 */
	public Portal locatePortal(int id) {
		return dungeon.locatePortal(id);
	}
	
	/**
	 * This function checks if there is a Leprechaun on the board
	 * @return the Leprechaun on the board
	 */
	public Leprechaun checkLeprechaun() {
		return dungeon.checkLeprechaun();
	}
	
	/**
	 * This function removes all the treasure from the player's inventory
	 */
	public void releaseAllTreasure() {
		List<Entity> treasure = inventory.getTreasure();
		for (Entity t : treasure) {
			addToDungeon(t);
			removefromInventory(t);
		}
	}
	

	/**
	 * This function checks for treasure in the players inventory
	 * @return whether the player has any treasure
	 */
	public boolean checkTreasure() {
		return inventory.checkTreasure();
	}
	
	/**
	 * This function checks what happens when the player collided with the Leprechaun
	 * @param leprechaun the Leprechaun the player collided with
	 */
	public void collideLeprechaun(Leprechaun leprechaun) {
		playerState.collideLeprechaun(leprechaun, dungeon);
	}
	
	/**
	 * This function finds the Leprechaun on the board
	 * @return the Leprechaun found on the board
	 */
	public Leprechaun findLeprechaun() {
		return dungeon.findLeprechaun();
	}
	
	/**
	 * This function removes the Leprechaun from the dungeon
	 */
	public void removeLeprechaun() {
		dungeon.removeLeprechaun();
	}
	
	/**
	 * This function gets the number of turns left on the sword in the inventory
	 * @return the number of turns left
	 */
	public int getSwordTurns() {
		return inventory.getSwordTurns();
	}

	/**
	 * This function checks if the player has a shield
	 * @return whether the player has a shield
	 */
	public boolean checkShield() {
		return inventory.checkShield();
	}
	
	/**
	 * This function checks if the player has a flame thrower
	 * @return whether the player has a flame thrower
	 */
	public boolean checkFlameThrower() {
		return inventory.checkFlameThrower();
	}

	/**
	 * This function checks if there is fire on the board
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the location of the fire on the board
	 */
	public int findFireBlock(int x, int y) {
		return dungeon.findFireBlock(x, y);
	}
	
	/**
	 * This function finds the enemy next to the player to be killed
	 * @param x the x coordinate of the player
	 * @param y the y coordinate of the player
	 * @return the enemy to be killed
	 */
	public Enemy findKillableEnemy(int x, int y) {
		return dungeon.findKillableEnemy(x, y);
	}

	/**
	 * This function removes all the lasers from the board
	 * @param id the wizards id the lasers are associated with
	 */
	public void removeAllLasers(int id) {
		dungeon.removeAllLasers(id);
	}
	
	public void exitPlayer() {
		exit = true;
		removePlayer();
	}
	
	public boolean isExit() {
		return exit;
	}

}
