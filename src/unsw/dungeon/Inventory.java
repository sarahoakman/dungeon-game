/**
 * An inventory representation
 */

package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
	
	private List<Entity> inventory;
	
	/**
	 * Constructor for the inventory
	 */
	public Inventory() {
		inventory = new ArrayList<Entity>();
	}
	
	/**
	 * This function adds an entity to the inventory
	 * @param e the entity to be added
	 */
	public void addEntity(Entity e) {
		inventory.add(e);
	}
	
	/**
	 * This function removes a given entity from the inventory
	 * @param e the entity to be removed
	 */
	public void removeEntity(Entity e) {
		inventory.remove(e);
	}
	
	/**
	 * This function checks if a sword is in the inventory
	 * @return boolean whether a sword is found as a boolean
	 */
	public boolean checkSword() {
		for (Entity e : inventory) {
			if (e.isKillable() && !e.isProtectable()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This function gets the sword which is stored in the player's inventory
	 * @return the sword which the player has
	 */
	public Sword getSword() {
		for (Entity e : inventory) {
			if (e.isKillable() && !e.isProtectable()) {
				return (Sword) e;
			}
		}
		return null;
	}
	
	/**
	 * This function checks if a key is in the inventory
	 * @return boolean whether a key is found as a boolean
	 */
	public boolean checkKey() {
		for (Entity e : inventory) {
			if (e.isLockable()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This function gets the key in the inventory
	 * @return Key the key found in the inventory of type Key
	 */
	public Key getKey() {
		for (Entity e : inventory) {
			if (e.isLockable()) {
				return (Key) e;
			}
		}
		return null;
	}
	
	/**
	 * This function checks if a potion is in the inventory
	 * @return boolean whether a potion is found as a boolean
	 */
	public boolean checkPotion() {
		for (Entity e : inventory) {
			if (!e.isLockable() && !e.isKillable() && !e.isCollectable() && !e.isTriggerable()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This function checks if a particular entity is in the inventory
	 * @return boolean whether the entity was found 
	 */
	public boolean itemFound(Entity e) {
		return inventory.contains(e);
	}
	
	public boolean checkTreasure() {
		for (Entity e : inventory) {
			if (e.isCollectable()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This function gets the number of turns that are left in the sword in the inventory
	 * @return the number of turns left in the sword
	 */
	public int getSwordTurns() {
		for (Entity e: inventory) {
			if (!e.isProtectable() && e.isKillable()) {
				return ((Sword)e).getTurns();
			}
		}
		return 0;
	}
	
	/**
	 * This function gets all the treasure which is available in the player inventory
	 * @return the treasure available in the inventory
	 */
	public List<Entity> getTreasure() {
		List<Entity> t = new ArrayList<Entity>();
		for (Entity e : inventory) {
			if (e.isCollectable() && e.isPickable()) {
				t.add(e);
			}
		}
		return t;
	}

	/**
	 * This function checks if the player has a shield
	 * @return whether the player has a shield
	 */
	public boolean checkShield() {
		for (Entity e : inventory) {
			if (e.isProtectable()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This function checks if the player has a flamethrower
	 * @return whether the player has a flamethrower
	 */
	public boolean checkFlameThrower() {
		for (Entity e : inventory) {
			if (e.isTriggerable()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This function gets the  flamethrower the player has in the inventory
	 * @return the flamethrower in the inventory
	 */
	public FlameThrower getFlameThrower() {
		for (Entity e : inventory) {
			if (e.isTriggerable()) {
				return (FlameThrower) e;
			}
		}
		return null;
	}
}
