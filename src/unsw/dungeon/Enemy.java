/**
 * An Enemy representation
 * Extends the Entity Class
 */

package unsw.dungeon;

public class Enemy extends Entity {

	private MoveStrategy enemyState;
	
	/**
	 * Constructor for the Enemy
	 * @param x The x-axis location as an integer
	 * @param y The y-axis location as an integer
	 */
	public Enemy(int x, int y) {
		super(x, y);
		setKillable(true);
		setBlockable(true);
		setMovable(true);
		//initially sets the enemy to move towards the player
		this.enemyState = new EntityTowards();
	}
	
	/**
	 * This function changes the state of the enemy movement
	 * @param enemyState the way the enemy should move
	 */
	public void setMoveStrategy(MoveStrategy enemyState) {
		this.enemyState = enemyState;
	}
	
	/**
	 * This function moves the enemy depending on the location of the player
	 * @param p the player playing the game
	 */
	public void move(Player p) {
		enemyState.move(p, this);
	}
	
	/**
	 * This function kills the enemy if the player has a sword
	 * @param p the player playing the game
	 */
	public void killEnemy(Player p) {
		p.removeFromDungeon(this);
		setExists(false);
	}
}
