/**
 * A subgoal representation
 */

package unsw.dungeon;

public class Subgoal implements GoalCompose {

	private String title;
	
	/**
	 * Constructor for a subgoal
	 * @param title The title of the goal as a string
	 */
	public Subgoal(String title) {
		this.title = title;
	}
	
	@Override
	public boolean checkAccomplished(Player p) {
		//checks if the subgoal is to kill all enemies
		if (title.compareTo("enemies") == 0) {
			if (p.checkNoEnemiesleft()) {
				return true;
			}
		//checks if the subgoal is to reach the exit
		} else if (title.compareTo("exit") == 0) {
			if (p.reachExit()) {
				return true;
			}
		//checks if the subgoal is to trigger all floor switches
		} else if (title.compareTo("boulders") == 0) {
			if (p.completeSwitches()) {
				return true;
			}
		//checks if the subgoal is to collect all the treasure
		} else if (title.compareTo("treasure") == 0) {
			if (p.collectedAllTreasure()) {
				return true;
			}
			
		}
		return false;
	}

}
