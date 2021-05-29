/**
 * A Door representation
 * Extends the Entity Class
 */

package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Door extends Entity {
	
	private BooleanProperty locked;
	private int id;
	
	/**
	 * Constructor for a door
	 * @param x The x-axis location as an integer
	 * @param y The y-axis location as an integer
	 * @param id the id as an integer
	 */
	public Door(int x, int y, int id) {
		super(x, y);
		this.id = id;
		this.locked = new SimpleBooleanProperty(true);
		setBlockable(true);
		setLockable(true);
	}
	
	/**
	 * Getter method for the id
	 * @return The id of the door as an integer
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter method for the locked status
	 * @return The locked status of the door as a boolean property
	 */
	public BooleanProperty isLocked() {
		return locked;
	}
	
	/**
	 * Gets the locked status as a boolean
	 * @return The locked status of the door as a boolean
	 */
	public boolean getLocked() {
		return locked.get();
	}
	
	/**
	 * This function sets the door lock to be false
	 */
	public void unlockDoor() {
		locked.set(false);
		setBlockable(false);
	}
	
	/**
	 * This function checks if the door blocks the player
	 * @param x the x coordinate of the player 
	 * @param y the y coordinate of the player
	 * @return returns whether the door blocks the player as a boolean
	 */
	public boolean blockPlayer(int x, int y) {
		if (locked.get()) {
			if (checkLocation(x, y)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This function checks if the player has the key
	 * and is opening the door
	 * @param x the x coordinate of the player
	 * @param y the y coordinate of the player
	 * @return whether the player has the key in the adjacent place as a boolean
	 */
	public boolean checkKey(int x, int y) {
		if (getX() == x && getY() + 1 == y) {
    		return true;
    	} else if (getX() == x && getY() - 1 == y) {
    		return true;
    	} else if (getX() - 1 == x && getY() == y) {
    		return true;
    	} else if (getX() + 1 == x && getY() == y) {
    		return true;
    	}
    	return false;
	}

}
