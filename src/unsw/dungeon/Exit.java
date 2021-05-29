/**
 * An Exit representation
 * Extends the Entity class
 */

package unsw.dungeon;

public class Exit extends Entity {

	/**
	 * Constructor for the exit
	 * @param x The x-axis location as an integer
	 * @param y The y-axis locaiton as an integer
	 */
	public Exit(int x, int y) {
		super(x, y);
		setExitable(true);
	}
}
