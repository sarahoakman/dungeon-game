package unsw.dungeon;

public class Laser extends Entity{
	private int id;
	public Laser(int x, int y, int id) {
		super(x, y);
		this.id = id;
		setKillable(true);
		setTriggerable(true);
	}
	
	/**
	 * This function gets the id of the laser
	 * @return the id of the laser
	 */
	public int getId() {
		return this.id;
	}
	
}
