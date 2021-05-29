/**
 * A sword representation
 * Extends the Entity class
 */

package unsw.dungeon;

public class Sword extends Entity implements PickUpStrategy {
	
	private int turns;
	
	/**
	 * Constructor for a sword
	 * @param x The x-axis location as an integer
	 * @param y The y-axis location as an integer
	 */
	public Sword(int x, int y) {
		super(x, y);
		//initially sets the sword to 5
		setKillable(true);
		setPickable(true);
		this.turns = 5;
	}
	
	/**
	 * This function gets the number of turns left in a sword
	 * @return the number of turns left in a sword
	 */
	public int getTurns() {
		return this.turns;
	}
			
	@Override
	public Entity pickUp(Player player) {
		if (!player.checkSword()) {
			//adds the sword to the player's inventory
			player.addToInventory(this);
			//removes the sword from the dungeon
			player.removeFromDungeon(this);
			return this;
		}
		return null;
	}
	
	/**
	 * This function hits the enemy
	 * @param p the player playing the game
	 */
	public boolean hitEnemy(Player p) {
		//finds the enemy closest to the player
		Enemy e = p.findEnemy();
		if (e != null) {
			//kills the enemy 
			e.killEnemy(p);
			//reduces the number of turns the sword can be used
			this.turns--;
			checkTurns(p);
			return true;
		}
		return false;
	}
	
	public boolean hitWizard(Player p) {
		//finds the wizard closest to the player
		Wizard w = p.findWizard();
		if (w != null) {
			//kills the enemy 
			w.killWizard(p);
			//reduces the number of turns the sword can be used
			this.turns--;
			checkTurns(p);
			return true;
		}
		return false;
	}
	
	/**
	 * This function checks if there are turns left on the sword
	 * @param p the player playing the game
	 */
	public void checkTurns(Player p) {
		if (this.turns == 0) {
			//removes the sword from the player
			p.removefromInventory(this);
			//triggers the deletion of the sword image
			setExists(false);
		}
	}
	

}
