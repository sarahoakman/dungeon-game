/**
 * A treasure representation
 */

package unsw.dungeon;

public class Treasure extends Entity implements PickUpStrategy {

	/**
	 * Constructor for Treasure
	 * @param x The x-axis location 
	 * @param y The y-axis location
	 */
	public Treasure(int x, int y) {
		super(x, y);
		setPickable(true);
		setCollectable(true);
	}

	@Override
	public Entity pickUp(Player player) {
		//adds treasure to the player's inventory
		player.addToInventory(this);
		//removes treasure from the dungeon
		player.removeFromDungeon(this);
		return this;
	}
	
}
