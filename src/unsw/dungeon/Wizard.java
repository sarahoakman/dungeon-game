package unsw.dungeon;

public class Wizard extends Entity{
	private int id;
	public Wizard(int x, int y, int id) {
		super(x, y);
		this.id = id;
		setKillable(true);
		setBlockable(true);
		setFireable(true);
	}
	
	/**
	 * Gets the id of the wizard
	 * @return the id of the wizard
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * This function kills the wizard on the board
	 * @param p the player playing the game
	 */
	public void killWizard(Player p) {
		p.removeFromDungeon(this);
		p.removeAllLasers(this.id);
		setExists(false);
	}
	
}
