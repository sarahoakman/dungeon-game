/**
 * Boulder Representation
 * Extends the Entity class and implements the MoveStrategy
 */

package unsw.dungeon;

public class Boulder extends Entity implements MoveStrategy {

	/**
	 * Constructor for the Boulder 
	 * @param x The x-axis location as an integer
	 * @param y The y-axis location as an integer
	 */
	public Boulder(int x, int y) {
		super(x, y);
		setMovable(true);
		setBlockable(true);
	}
	
	/**
	 * This function sets the movement of the boulder according to 
	 * what direction the player moves in
	 * Here when: 
	 * type = 0 player moves up
	 * type = 1 player moves down
	 * type = 2 player moves left
	 * else player moves right
	 */
	@Override
	public void move(Player player, Entity entity) {
		int newX = player.getX();
		int newY = player.getY();
		int type = player.getKeyPressed();
		if (checkLocation(newX, newY)) {
			if (type == 0) {
				newY = newY - 1;
				y().set(newY);
			} else if (type == 1) {
				newY = newY + 1;
				y().set(newY);
			} else if (type == 2) {
				newX = newX - 1;
				x().set(newX);
			} else {
				newX = newX + 1;
				x().set(newX);
			}
		}
	}
}
