/**
 * Switch representation
 * Extends the Entity class
 */

package unsw.dungeon;

public class Switch extends Entity {

	private boolean triggered;
	
	/**
	 * Constructor for a switch
	 * @param x The x-axis location as integer
	 * @param y The y-axis locaiton as an integer
	 */
	public Switch(int x, int y) {
		super(x, y);
		this.triggered = false;
		setTriggerable(true);
	}
	
	/**
	 * This function sets the floor switch to triggered
	 * This depends on if a boulder is on the floor switch
	 * @param triggered the status of floor switch
	 */
	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}
	
	/**
	 * This function gets the trigger status of the floor switch
	 * @return the trigger status of the floor switch
	 */
	public boolean getTriggered() {
		return this.triggered;
	}

	
}
