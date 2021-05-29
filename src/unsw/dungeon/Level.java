/**
 * A level representation
 */

package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Level {
	
	private List<Goal> goals;
	private BooleanProperty status;
	
	/**
	 * Constructor for a level
	 * @param goal The goal, may be single or a complex goal
	 */
	public Level() {
		this.goals = new ArrayList<Goal>();
		//level is not complete to begin with
		this.status = new SimpleBooleanProperty(false);
	}
	
	/**
	 * Add goals to the Goal list
	 * @param goal The goal the player has to complete of type Goal
	 */
	public void addGoal(Goal goal) {
		goals.add(goal);
	}
	
	/**
	 * Getter method for the status of the level
	 * @return The status as a boolean property
	 */
	public BooleanProperty isStatus() {
		return status;
	}

	/**
	 * This function checks if the level has been completed by the player by accomplishing the goals
	 * @param player the player playing the game
	 */
	public void checkLevel(Player player) {
		for (Goal goal : goals) {
			if (!goal.checkAccomplished(player)) {
				return;
			}
		}
		finishLevel();
	}
	
	/**
	 * This function sets the status of the level to be completed
	 */
	private void finishLevel() {
		status.set(true);
	}
	
	/**
	 * This function gets the status of the level
	 * @return the status of the level
	 */
	public boolean getStatus() {
		return this.status.get();
	}
}
