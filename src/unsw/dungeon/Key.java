/**
 * A key representation
 * Extends the Entity class and implements the PickUpStrategy Interface
 */

package unsw.dungeon;

public class Key extends Entity implements PickUpStrategy {
	private Door door;
	private int id;
	
	/**
	 * Constructor for the key
	 * @param x The x-axis location as an integer
	 * @param y The y-axis location as an integer
	 * @param id The id of the key as an integer
	 */
	public Key(int x, int y, int id) {
		super(x, y);
		this.id = id;
		setPickable(true);
		setLockable(true);
	}
	
	/**
	 * Setter method for the door
	 * @param door The related door
	 */
	public void setDoor(Door door) {
		this.door = door;
	}
	
	/**
	 * Getter method for the id
	 * @return The id as an integer
	 */
	public int getId() {
		return id;
	}
	
	@Override
	public Entity pickUp(Player player) {
		if (!player.checkKey()) {
			//adds the key to the inventory
			player.addToInventory(this);
			//removes the key from the dungeon
			player.removeFromDungeon(this);
			return this;
		}
		return null;
	}
	
	/**Drops a key to unlock a door or in order to get a new one
	 * @param player The player of the game
	 */
	public void drop(Player player) {
		//if the key is the one for the door
		if (door.checkKey(player.getX(), player.getY())) {
			//open the door to allow player to pass through
			door.unlockDoor();
			//removes the key from the inventory
			player.removefromInventory(this);
			//triggers the deletion of the image for the key
			setExists(false);
		// if the key needs to be dropped without unlocking a door
		} else {
			player.removefromInventory(this);
			player.addToDungeon(this);
			//sets the position of the key to be the same as the player's position
			x().set(player.getX());
			y().set(player.getY());
		}
	}

}
