/**
 * A portal representation
 * Extends the Entity class
 */

package unsw.dungeon;

public class Portal extends Entity{
	
	private int id;

	/**
	 * Constructor for a portal
	 * @param x The x-axis location as an integer
	 * @param y The y-axis location as an integer
	 * @param id The id of the portal
	 */
	public Portal(int x, int y, int id) {
		super(x, y);
		this.id = id;
		setTeleportable(true);
	}
	
	/**
	 * Getter method for the id
	 * @return The id as an integer
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * This function moves the player from the position of the portal they are next to
	 * to another portal on the board
	 * @param p The player of the game
	 */
	public void teleport(Player p) {
		//locates the other portal on the board
		Portal pair = p.locatePortal(this.id);
		int pairX = pair.getX();
		int pairY = pair.getY();
		if (pair != null) {
			//if the square to the right of portal is empty
			if (p.checkEmpty(pairX + 1,pairY)) {
				p.teleportPlayer(pairX  + 1,pairY);
			//if the square to the left of portal is empty
			} else if (p.checkEmpty(pairX  - 1, pairY)) {
				p.teleportPlayer(pairX  - 1 ,pairY);
			//if the square below the portal is empty
			} else if (p.checkEmpty(pairX , pairY + 1)) {
				p.teleportPlayer(pairX ,pairY + 1);
			//if the square above the portal is empty
			} else if (p.checkEmpty(pairX , pairY - 1)) {
				p.teleportPlayer(pairX ,pairY - 1);
			} 	
		}		
	}
	
	@Override
	public boolean checkId(int id2) {
		//the method from the entity overwritten to check if the portal id's match
		return (this.id == id2);
	}
	
	
}
