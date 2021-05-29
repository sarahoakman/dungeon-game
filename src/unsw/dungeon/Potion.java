/**
 * A potion representation
 * Extends the Entity class and implements the PickUpStragy Interface
 */

package unsw.dungeon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Potion extends Entity implements PickUpStrategy {

	/**
	 * Constructor for the Potion
	 * @param x The x-axis location as an integer
	 * @param y The y-axis location as an integer
	 */
	public Potion(int x, int y) {
		super(x, y);
		setPickable(true);
	}

	@Override
	public Entity pickUp(Player player) {
		if (!player.checkPotion()) {
			//adds the potion to the inventory
			player.addToInventory(this);
			player.removeFromDungeon(this);
			//sets the enemy and leprechaun to move away from the player
		    EntityAway entityState = new EntityAway();
			player.setEnemyState(entityState);
			//sets the player to be invincible 
			player.setInvinciblePlayer();
			//removes the potion from the player's inventory
			removePotion(this, player);
			return this;
		}
		return null;
	}
	
	/**
	 * This function sets the state of the player to invincible for 10 seconds
	 * And allows the player to walk around the board to kill the enemy 
	 * @param potion the invincibility potion used by the player
	 * @param player the player playing the game
	 */
	private void removePotion(Potion potion, Player player) {
		//set a timer for the potion to be 10 seconds
		Timer potionTimer = new Timer(10000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//set the player back to normal
				player.setNormalPlayer();
				//remove potion from the inventory
				player.removefromInventory(potion);
				//change the state of the enemy to towards
				EntityTowards entityState = new EntityTowards();
				player.setEnemyState(entityState);
				// triggers the deletion of the potion image
				setExists(false);
			}
		});
		//the timer is not repeated
		potionTimer.setRepeats(false);
		potionTimer.start();
	}
}
