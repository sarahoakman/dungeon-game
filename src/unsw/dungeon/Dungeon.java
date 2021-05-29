/**
 * A Dungeon Representation 
 * Implements the DungeonObserver
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon implements DungeonObserver {

    private int width, height;
    private List<Entity> entities;
    private List<EntityObserver> observers;
    private Player player;
    private Level level;

    /**
     * Constructor for the Dungeon
     * @param width The width of the board as an integer
     * @param height The height of the board as an integer
     * @param level The level of the player is trying to complete of type Level
     */
    public Dungeon(int width, int height, Level level) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.observers = new ArrayList<EntityObserver>();
        this.player = null;
        this.level = level;
    }
    
    /**
     * This function gets the width of the dungeon 
     * @return The width of the dungeon as an integer 
     */
    public int getWidth() {
        return width;
    }

    /**
     * This function gets the height of the dungeon 
     * @return The height of the dungeon as an integer
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * This function gets the player of the game
     * @return The player of the game of type Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * This function sets the player of the game
     * @param player the player of the game
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * This function sets the level the player is on. 
     * Each level has different type of entities on the dungeon.
     * @param level the level the player is player
     */
    public void setLevel(Level level) {
    	this.level = level;
    }
    
    /**
     * This function adds the entity to the dungeon
     * @param entity the object being added to the dungeon
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
        //attaches the observer for the entity
        Attach((EntityObserver)entity);
    }

    /**
     * This function removes the entity from the dungeon
     * @param entity the entity to be removed
     */
    public void removeEntity(Entity entity) {
    	entities.remove(entity);
    	//removes the observer for the entity
    	Detatch(entity);
    }
    
	@Override
	public void Attach(EntityObserver e) {
		this.observers.add(e);
	}

	@Override
	public void Detatch(EntityObserver e) {
		this.observers.remove(e);
	}
	
	@Override
	public void Notify() {
		//if the SPACEBAR was pressed
		if (player.getKeyPressed() > 3) {
			updateGoals();
		} else {
			//go through all the entity observers and update them
			for (EntityObserver e: observers) {
				e.Update(player);
			}
			collideEntities();
			
			updateGoals();
		}
	}
	
	private void collideEntities() {
		//since the enemies could not be deleted in the above loop
		//finds if any enemies have been killed and deletes them afterwards
		Enemy enemyDelete = null;
		Leprechaun leprechaunCollide = null;
		Laser laserCollide = null;
		Wizard wizardCollide = null;
		Fire fireCollide = null;
		for (Entity e : entities) {
			if (e.checkLocation(player.getX(), player.getY())) {
				if (e.isKillable() && e.isMovable() && !e.isTeleportable()) {
					enemyDelete = (Enemy) e;
		    	} else if (e.isCollectable() && e.isBlockable()) {
			    	leprechaunCollide = (Leprechaun) e;
			    } else if (e.isTriggerable() && e.isBlockable() && e.isKillable()) {
			    	fireCollide = (Fire) e;
			    } else if (e.isTriggerable() && e.isKillable()) {
			    	laserCollide = (Laser) e;
			    } else if (e.isKillable() && e.isBlockable() && e.isFireable()) {
			    	wizardCollide = (Wizard) e;
			    } 
			}
		}
		if (enemyDelete != null) {
			player.collideEnemy(enemyDelete);
		}
		if (leprechaunCollide != null) {
			player.collideLeprechaun(leprechaunCollide);
		}
		if (fireCollide != null) {
			player.collideFire();
		}
		
		if (laserCollide != null) {
			player.collideLaser(laserCollide);
		}
		
		if (wizardCollide != null) {
			player.collideWizard(wizardCollide);
		}
	}

	/**
	 * This function checks if the goals are met
	 */
	public void updateGoals() {
		level.checkLevel(player);
	}
    
    /**
     * Links corresponding keys and doors together by their matching id
     */
    public void connectKeysAndDoors() {
    	Door door = null;
    	Key key = null;
    	for (Entity e : entities) {
    		// shows that it is a key
    		if (e.isPickable() && e.isLockable()) {
    			key = (Key) e;
    			// set a door to it's key
    			door = findDoorWithId(key.getId());
    			key.setDoor(door);
    		}
    	}
    }
    
    /**
     * Finds the door with the matching id to a key
     * @param id The id of the key as an integer
     * @return The corresponding door of type Door
     */
    public Door findDoorWithId(int id) {
    	for (Entity e : entities) {
    		if (e.isLockable() && !e.isPickable()) {
    			Door door = (Door) e;
    			if (door.getId() == id) {
    				return door;
    			}
    		}
    	}
    	return null;
    }
    
    /**
     * This function checks if the player is able to move to the empty spot
     * @param type the direction the player moves in
     * @param x the x coordinate the player wants to move to 
     * @param y the y coordinate the player wants to move to 
     * @return whether the player can move into that spot as a boolean
     */
    public boolean checkSquare(int type, int x, int y) {
    	for (Entity e : entities) {
    		//if the entity is a wall
    		if (e.isBlockable() && !e.isLockable() && !e.isKillable() && !e.isMovable() && !e.isCollectable()) {
    			Wall w = (Wall) e;
    			if (!w.blockPlayer(x, y)) {
    				return false;
    			}
    		//if the entity is a door
    		} else if (e.isBlockable() && e.isLockable()) {
    			Door d = (Door) e;
    			if (!d.blockPlayer(x, y)) {
    				return false;
    			}
    		//if the entity is a boulder, more checks required
    		} else if (e.isBlockable() && e.isMovable() && !e.isKillable()) {
	    		if (e.checkLocation(x, y) && !checkBoulder(x, y, type)) {
	    			return false;
	    		}
    		}
    		//if the entity is a leprechaun who is on a piece of treasure
    		else if (e.isBlockable() && e.isCollectable() && e.checkLocation(x, y)) {
    			// check if the leprechaun is on a piece of treasure
    			if (getPickableEntity(x, y) != null) {
    				return false;
    			}
    		}
    	}
    	return true;
    }

	/**
     * This function checks if the boulder can move in a particular direction
     * @param x the x coordinate of the boulder 
     * @param y the y coordinate of the boulder
     * @param type the direction the boulder is moving towards as an integer
     * @return whether the boulder can move to a particular location as a boolean
     */
    public boolean checkBoulder(int x, int y, int type) {
		//calculates and checks the intended new position of the boulder
    	if (type == 0) {
			y = y - 1;
			if (!freeBoulderSquare(x, y)) {
				return false;
			}
		} else if (type == 1) {
			y = y + 1;
			if (!freeBoulderSquare(x, y)) {
				return false;
			}
		} else if (type == 2) {
			x = x - 1;
			if (!freeBoulderSquare(x, y)) {
				return false;
			}
		} else {
			x = x + 1;
			if (!freeBoulderSquare(x, y)) {
				return false;
			}
		}
    	return true;
    }
    
    /**
     * This function checks if a square is empty and on the board for a boulder to move to
     * @param x the x coordinate the entity wants to move to
     * @param y the y coordinate the entity wants to move to
     * @return whether the square was free on the board as a boolean
     */
    public boolean freeBoulderSquare(int x, int y) {
    	// checks the boulder remains on the board
    	if (x >= width || x < 0 || y >= height || y < 0) {
    		return false;
    	}
    	// checks whether the boulder's new location is occupied
    	for (Entity e : entities) {
    		if (!e.isTriggerable() && !(e.isLockable() && !e.isPickable() && !((Door) e).getLocked())) {
    			if (e.checkLocation(x, y)) {
    				return false;
    			}
    		}
    		if (e.isTriggerable() && e.isKillable() && e.checkLocation(x, y)) {
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * This function checks if a square is empty of the board for an entity to move to
     * @param x the x coordinate the entity wants to move to
     * @param y the y coordinate the entity wants to move to
     * @return whether the square was free on the board
     */
    public boolean freeSquare(int x, int y) {
    	if (x >= width || x < 0 || y >= height || y < 0) {
    		return false;
    	}
    	for (Entity e : entities) {
    		if (e.isBlockable() || e.isTeleportable()) {
	    		if (e.checkLocation(x, y)) {
	    			return false;
	    		}
    		}
    	}
    	return true;
    }
       
    /**
     * This function gets the boulder from a given location
     * @param x the x coordinate where the boulder will be checked
     * @param y the y coordinate where the boulder will be checked
     * @return the boulder at the given location of type MoveStrategy
     */
    public MoveStrategy getBoulder(int x, int y) {
    	for (Entity e : entities) {
    		//checks if it's a boulder
    		if (e.isBlockable() && e.isMovable() && !e.isKillable()) {
	    		if (e.checkLocation(x, y)) {
	    			return (MoveStrategy) e;
	    		}
    		}
    	}
    	return null;
    }
    
    /**
     * This function gets all the boulders in the dungeon
     * @return a list of all the boulders on the board
     */
    private List <Boulder> getBoulders() {
    	List <Boulder> boulders = new ArrayList<Boulder>();
    	for (Entity e : entities) {
    		if (e.isBlockable() && e.isMovable() && !e.isKillable()) {
    			boulders.add((Boulder) e);
    		}
    	}
    	return boulders;
    }
    
    /**
     * This function gets all the switches in the dungeon
     * @return a list of all the switches on the board
     */
    private List <Switch> getSwitches() {
    	List <Switch> switches = new ArrayList<Switch>();
    	for (Entity e : entities) {
    		if (e.isTriggerable() && !e.isPickable() && !e.isKillable()) {
    			switches.add((Switch) e);
    		}
    	}
    	return switches;
    }
    
    /**
     * This function updates the status of all the switches
     */
    public void updateSwitches() {
    	//gets all the boulders on the dungeon
    	List <Boulder> boulders = getBoulders();
    	//gets all the switches on the dungeon
    	List <Switch> switches = getSwitches();
    	for (Switch s : switches) {
    		boolean check = false;
    		for (Boulder b : boulders) {
    			//if the boulder location is same as the switch location
    			if (b.checkLocation(s.getX(), s.getY())) {
    				//sets the switch to triggered
    				s.setTriggered(true);
    				check = true;
    				break;
    			}
    		}
    		if (check == false) {
    			//sets the switch to not be triggered
    			s.setTriggered(false);
    		}
    	}
    }
    
    /**
     * This function checks if all the switches in the dungeon have a boulder on them
     * @return whether all the floor switches are triggered
     */
    public boolean allTriggeredSwitches() {
    	for (Entity e: entities) {
    		if (e.isTriggerable() && !e.isKillable() && !e.isPickable()) {
    			//if the switch is not triggered
    			if (!((Switch)e).getTriggered()) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    /**
     * This function locates a other portal on the board
     * @return the portal that the player has to be teleport to of type Portal
     */
    public Portal locatePortal(int id) {
    	for (Entity e: entities) {
    		//if the entity allows player to teleport
    		if (e.isTeleportable() && !e.checkLocation(player.getX(), player.getY()) && e.checkId(id)) {
    			return (Portal) e;
    		}
    	}
    	return null;
    }
	

	/**
	 * Finds an enemy which is next to the player
	 * @return the enemy that was found
	 */
	public Enemy findEnemy() {
		
		for (Entity e:entities) {
			if (e.isKillable() && e.isMovable() && player.checkAdjacent(e.getX(),e.getY())) {
				return (Enemy) e;
    		}
		}
		return null;
	}
	
	/**
     * This function sets the state of the all the enemies on the board
     * depending on if the player has the invincibility potion 
     * @param enemyState the state the enemy is to be set to
     */
    public void setEnemyState(MoveStrategy entityState) {
    	for (Entity e : entities) {
    		if (e.isKillable() && e.isMovable() && !e.isTeleportable()) {
    			((Enemy) e).setMoveStrategy(entityState);
    		}
    	}
    }
    
    /**
	 * This function checks if there are enemies left on the board
	 * @return whether there is an enemy left on the board
	 */
	public boolean noEnemyExist() {
		for (Entity e : entities) {
			//if an enemy was found
			if (e.isKillable() && e.isMovable() && !e.isCollectable() && !e.isTeleportable()) {
				return false;
			}
			if (e.isKillable() && e.isFireable()) {
				return false;
			}
		}
		return true;
	}
    
    /**
     * This function checks if the player has reached the exit
     * @return whether the player is near the exit
     */
    public boolean checkExit() {
    	for (Entity e : entities) {
    		if (e.isExitable() && e.checkLocation(player.getX(), player.getY())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * This function checks if the entity on a given location can be collected
     * @param x the x coordinate of the location to be checked
     * @param y the y coordinate of the location to be checked
     * @return the entity which can be collected by the player
     */
    public PickUpStrategy getPickableEntity(int x, int y) {
    	for (Entity e : entities) {
    		if (e.isPickable() && e.checkLocation(x, y)) {
    			return (PickUpStrategy) e;
    		}
    	}
    	return null;
    }
    
    /**
	 * This function checks if there is treasure left on the board
	 * @return whether there is a treasure entity left on the board
	 */
	public boolean noTreasureExist() {
		for (Entity e : entities) {
			//if the treasure was found
			if (e.isCollectable() && e.isPickable()) {
				return false;
			}
		}
		return true;
	}
    
	/**
	 * Checks if the item is found within the dungeon, mainly used for testing
	 * @param e The entity to check whether it's in the dungeon
	 * @return whether the entity was found as a boolean
	 */
    public boolean itemFound(Entity e) {
		return entities.contains(e);
	}

    // function that gets the level so that we can make a listener for whether the level is passed in DungeonController
	public Level getLevel() {
		return this.level;
	}
	
	/**
	 * This function checks if the Leprechaun is closest to the player
	 * @return the Leprechaun which was closest to the player
	 */
	public Leprechaun checkLeprechaun() {
		for (Entity e : entities) {
			if (e.isCollectable() && e.isBlockable() && e.checkLocation(player.getX(), player.getY())) {
				return (Leprechaun)e;
			}
		}
		return null;
	}
	
	
	/**
	 * This function removes all the treasure from the player's inventory
	 */
	public void releaseTreasure() {
		player.releaseAllTreasure();
	}
	
	/*
	 * This function finds the leprechaun adjacent to the player
	 */
	public Leprechaun findLeprechaun() {
		for (Entity e:entities) {
			if (e.isCollectable() && e.isBlockable() && player.checkAdjacent(e.getX(),e.getY())) {
				return (Leprechaun) e;
    		}
		}
		return null;
	}
	
	/**
	 * This function finds the Leprechaun closest to the player and removes it
	 */
	public void removeLeprechaun() {
		//finds a particular enemy adjacent to the player
		Leprechaun e = findLeprechaun();
		if (e != null) {
			entities.remove(e);
			
		}
	}
	
	/**
	 * This function finds the maximum location of the fireblock on the board
	 * @param x the x coordinate of the player
	 * @param y the y coordinate of the player
	 * @return the maximum location the fire can be thrown to
	 */
	public int findFireBlock(int x, int y) {
		int max = width - 1;
		for (Entity e : entities) {
			if (e.getY() == y && e.getX() > x) {
				if (e.isBlockable() && !e.isCollectable() && !e.isKillable()) {
					if (e.getX() < max) {
						max = e.getX();
					}
				}
			}
		}
		return max;
	}
	
	/**
	 * This function finds the enemy which is killable and is closest to the player
	 * @param x the x coordinate to check
	 * @param y the y coordinate to check
	 * @return the enemy at the given position
	 */
	public Enemy findKillableEnemy(int x, int y) {
		for (Entity e : entities) {
			if (e.isKillable() && e.isMovable() && e.checkLocation(x,  y)){
				return (Enemy) e;
			}
		}
		return null;
	}
	
	/**
	 * This function removes all the lasers from the board once the player is killed
	 * @param id the id of the wizard the lasers are connected to
	 */
	public void removeAllLasers(int id) {
		List<Entity> lasers = getLasers(id);
		for (Entity e : lasers) {
			removeEntity(e);
			e.setExists(false);
		}
	}
	
	/**
	 * This function gets the list of all the lasers to be deleted
	 * @param id the id of the wizard the lasers are linked to
	 * @return the list of all the lasers
	 */
	public List<Entity> getLasers(int id) {
		List<Entity> lasers = new ArrayList<Entity>();
		for (Entity e:entities) {
			if (e.isTriggerable() && e.isKillable() && ((Laser)e).getId() == id) {
				lasers.add(e);
			}
		}
		return lasers;
	}
	
	/**
	 * This function finds the wizard in the dungeon the player is closest to
	 * @return the wizard next to the player
	 */
	public Wizard findWizard() {
		for (Entity e:entities) {
			if (e.isKillable() && e.isBlockable() && e.isFireable() && player.checkAdjacent(e.getX(),e.getY())) {
				return (Wizard) e;
    		}
		}
		return null;
	}
}