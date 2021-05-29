package unsw.dungeon;



public class FlameThrower extends Entity implements PickUpStrategy{
	
	private int maxX;
	private int minX;
	private int pY;
	
	public FlameThrower(int x, int y) {
		super(x, y);
		setPickable(true);
		setTriggerable(true);
		setExists(true);
		
	}

	@Override
	public Entity pickUp(Player player) {
		if (!player.checkFlameThrower()) {
			//adds the sword to the player's inventory
			player.addToInventory(this);
			//removes the sword from the dungeon
			player.removeFromDungeon(this);
			return this;
		}
		return null;
	}
	
	/**
	 * Gets the maximum range of the flame thrower
	 * @return the x value the flame thrower can shoot to
	 */
	public int getMaxX() {
		return maxX;
	}
	
	/**
	 * Gets the minimum range of the flame thrower
	 * @return the x value the flame thrower shoots from
	 */
	public int getMinX() {
		return minX;
	}
	
	/**
	 * Gets the y location of the player
	 * @return the location of the player
	 */
	public int getPlayerY() {
		return pY;
	}
	
	/**
	 * This function shoots the flame thrower according to where the player is standing
	 * @param p the position of the player
	 */
	public void shootFlame(Player p) {
		this.pY = p.getY();
		this.minX = p.getX();
		this.maxX = p.findFireBlock(p.getX(), pY);
		for (int x = p.getX() + 1; x < maxX; x++) {
			Enemy e = p.findKillableEnemy(x, pY);
			if (e != null) {
				e.killEnemy(p);
				p.removefromInventory(this);
				setExists(false);
			}
		}
		
	}
}
