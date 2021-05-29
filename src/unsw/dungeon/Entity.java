/**
 * An Entity representation
 * Implements the Entity Observer
 */
package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class Entity implements EntityObserver {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    
    private boolean movable;
    private boolean pickable;
	private boolean collectable;
    private boolean exitable;
    private boolean blockable;
    private boolean killable;
    private boolean triggerable;
    private boolean lockable;
    private boolean teleportable;
    private boolean protectable;
    private boolean fireable;
    private BooleanProperty invincible;
    private BooleanProperty exists;
    
    /**
     * Create an entity positioned in square (x,y)
     * @param x the x coordinate of the entity
     * @param y the y coordinate of the entity
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.blockable = false;
        this.collectable = false;
        this.killable = false;
        this.exitable = false;
        this.triggerable = false;
        this.teleportable = false;
        this.pickable = false;
        this.movable = false;
        this.lockable = false;
        this.protectable = false;
        this.fireable = false;
        this.invincible = new SimpleBooleanProperty(false);
        this.exists = new SimpleBooleanProperty(true); 
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    /**
     * Getter method for y as an integer
     * @return y as an integer
     */
    public int getY() {
        return y().get();
    }

    /**
     * Getter method for x as an integer
     * @return x as an integer
     */
    public int getX() {
        return x().get();
    }
	
    public boolean isProtectable() {
		return protectable;
	}
    
    public void setProtectable(boolean protectable) {
		this.protectable = protectable;
	}
    
    /**
     * This function checks if the entity can allow teleportation
     * @return whether the entity is teleportable
     */
	public boolean isTeleportable() {
		return teleportable;
	}
	
	/**
	 * This function sets the teleporting status of this entity
	 * @param teleportable whether this entity can allow teleportation
	 */
	public void setTeleportable(boolean teleportable) {
		this.teleportable = teleportable;
	}
	
	/**
     * This function checks if the entity can be picked up
     * @return whether the entity can be picked up
     */
	public boolean isPickable() {
		return pickable;
	}
	
	/**
	 * This function sets the picking status of this entity
	 * @param pickable whether this entity can be picked up
	 */
	public void setPickable(boolean pickable) {
		this.pickable = pickable;
	}
	
	/**
     * This function checks if the entity can be collected by the player
     * @return whether the entity can be collected
     */
	public boolean isCollectable() {
		return collectable;
	}
	
	/**
	 * This function sets the collection status of this entity
	 * @param collectable whether this entity can be collected
	 */
	public void setCollectable(boolean collectable) {
		this.collectable = collectable;
	}

	/**
     * This function checks if the entity can be used for exiting
     * @return whether the entity is can  be used for exit
     */
	public boolean isExitable() {
		return exitable;
	}
	
	/**
	 * This function sets the exit status of this entity
	 * @param exitable whether this entity can allow player to exit
	 */
	public void setExitable(boolean exitable) {
		this.exitable = exitable;
	}
	
	/**
     * This function checks if the entity can block the player's movement
     * @return whether the entity is able to block the player
     */
	public boolean isBlockable() {
		return blockable;
	}
	
	/**
	 * This function sets the blocking status of this entity
	 * @param blockable whether this entity can block the player's movement
	 */
	public void setBlockable(boolean blockable) {
		this.blockable = blockable;
	}

	/**
     * This function checks if the entity can be killed
     * @return whether the entity is killable
     */
	public boolean isKillable() {
		return killable;
	}
	
	/**
	 * This function sets the kill status of this entity
	 * @param killable whether this entity can be killed by the player
	 */
	public void setKillable(boolean killable) {
		this.killable = killable;
	}
	
	/**
     * This function checks if the entity can be moved
     * @return whether the entity is able to move
     */
	public boolean isMovable() {
		return movable;
	}
	
	/**
	 * This function sets the moving status of this entity
	 * @param movable whether this entity can move around the dungeon
	 */
	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	/**
     * This function checks if the entity can be triggered
     * @return whether the entity can be triggered
     */
	public boolean isTriggerable() {
		return triggerable;
	}
	
	/**
	 * This function sets the trigger status of this entity
	 * @param triggerable whether this entity can be triggered by the player
	 */
	public void setTriggerable(boolean triggerable) {
		this.triggerable = triggerable;
	}
	
	/**
     * This function checks if the entity can be locked
     * @return whether the entity can be locked
     */
	public boolean isLockable() {
		return lockable;
	}

	/**
	 * This function sets the lockable status of this entity
	 * @param lockable whether this entity can unlock or be unlocked
	 */
	public void setLockable(boolean lockable) {
		this.lockable = lockable;
	}
	
	/**
     * This function checks if the entity can be killed with fire
     * @return whether the entity can be killed with fire
     */
	public boolean isFireable() {
		return fireable;
	}
	
	/**
	 * This function sets the fireable status of this entity
	 * @param lockable whether this entity can be killed by fire
	 */
	public void setFireable(boolean fireable) {
		this.fireable = fireable;
	}
	
	
    /**
     * This function checks if the entity is invincible
     * @return whether the entity is invincible
     */
	public BooleanProperty isInvincible() {
		return invincible;
	}
	
	/**
	 * This function sets the invincible status of this entity
	 * @param invincible whether this entity is invincible
	 */
	public void setInvincible(boolean invincible) {
		this.invincible.set(invincible);;
	}
	/**
	* This function checks if the entity is on the board still 
    * @return whether the entity exists
    */
	public BooleanProperty isExists() {
		return exists;
	}
	
	/**
	 * This function sets the exists status of this entity
	 * @param exists whether this entity is on the board
	 */
	public void setExists(boolean exists) {
		this.exists.set(exists);;
	}

	@Override
    public void Update(Player player) {
    	boolean sameLocation = checkLocation(player.getX(), player.getY());
    	//checks if the player is on the exit
    	if (sameLocation && isExitable()) {
    		player.exitPlayer();
    	}
    	//checks if the entity is an enemy and allows the enemy to move around and collide with the player
	    if (isKillable() && isMovable() && !sameLocation) {
	   		Enemy e = (Enemy) this;
	   		e.move(player);
	   	}
	    //checks if the boulder is being pushed by the player and moves it's position
	    if (isMovable() && !isKillable() && sameLocation) {
	    	Boulder b = (Boulder) this;
	    	b.move(player, null);
	    	player.updateSwitches();
	    }
	    
	    //checks if the player is on the portal and teleports them
	    if (isTeleportable() && !isMovable() && sameLocation) {
	    	Portal p = (Portal) this;
	    	p.teleport(player);
	    }
	    //checks if the entity is a leprechaun and moves it accordingly or checks if the player has collided
	    if (isCollectable() && isBlockable() && !sameLocation) {
	    	Leprechaun l = (Leprechaun) this;
	    	//if (l.checkPlayer(player.getX(), player.getY())) {
	    		l.move(player);
	    	///}
	    }
	    
	    //updates the goals
	    player.updateGoals();
    }

    /**
     * This function if two entities are on the same square in the dungeon
     * @param x the x coordinate of another entity
     * @param y the y coordinate of another entity
     * @return whether two entities are on the same square
     */
	public boolean checkLocation(int x, int y) {
		return getX() == x && getY() == y;
	}
	
	/**
	 * This function checks if the id of two items in the dungeon matches
	 * @param id2 the id of the other item
	 * @return whether the two id's match
	 */
	public boolean checkId(int id2) {
		return false;
	}

}
