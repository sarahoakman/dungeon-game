/**
 * A wall representation
 * Extends the Entity class
 */

package unsw.dungeon;

public class Wall extends Entity {

	/**
	 * Constructor for a wall
	 * @param x The x-axis location as an integer
	 * @param y The y-axis location as an integer
	 */
    public Wall(int x, int y) {
        super(x, y);
        setBlockable(true);
    }
    
    /**
     * This function checks if the wall blocks the player
     * @param x the x coordinate of the player
     * @param y the y coordinate of the player
     * @return whether the wall is blocking the player
     */
    public boolean blockPlayer(int x, int y) {
    	if (checkLocation(x, y)) {
    		return false;
    	}
    	return true;
    }

}
