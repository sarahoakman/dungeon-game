/**
 * A Goal representation
 * Implements GoalCompose interface
 */

package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Goal implements GoalCompose {

	private String title;
	private List<GoalCompose> subgoals;
	
	/**
	 * Constructor for single goals
	 */
	public Goal() {
		this.subgoals = new ArrayList<>();
		this.title = "SINGLE";
	}
	
	/**
	 * Constructor for complex goals
	 * @param title Specifies the relationship, either AND or OR
	 */
	public Goal(String title) {
		this();
		this.title = title;
	}
	
	/**
	 * This function adds a subgoal to the list
	 * @param subgoal a list of subgoals the player has to complete
	 */
	public void addSubgoal(GoalCompose subgoal) {
		subgoals.add(subgoal);
	}
	
	@Override
	public boolean checkAccomplished(Player p) {
		//makes sure every goal is completed
		if (title.compareTo("AND") == 0) {
			for (GoalCompose s: subgoals) {
				if (!s.checkAccomplished(p)) {
					return false;
				}
			}
			return true;
		//makes sure at least one goal is completed
		} else {
			for (GoalCompose s: subgoals) {
				if (s.checkAccomplished(p)) {
					return true;
				}
			}
		}
		return false;
	}
}
