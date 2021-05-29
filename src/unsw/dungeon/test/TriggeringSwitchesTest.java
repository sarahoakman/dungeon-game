package unsw.dungeon.test;

import unsw.dungeon.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TriggeringSwitchesTest {

	Dungeon dungeon;
	Player player;
	Boulder boulder;
	Switch floorSwitch;
	
	@BeforeEach
	public void initTests() {
		
		// set up a level to avoid null errors
		Subgoal treasure = new Subgoal("treasure");
		Goal goals = new Goal();
		goals.addSubgoal(treasure);
		Level level = new Level();
        level.addGoal(goals);
		        
		dungeon = new Dungeon(4, 1, level);
		
		floorSwitch = new Switch(2, 0);
		dungeon.addEntity(floorSwitch);
		
		boulder = new Boulder(1, 0);
		dungeon.addEntity(boulder);
		
		player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
	}
	
	@Test
	void TriggeringSwitchTest() {
		
		// check if the floor switch is not triggered
		assertFalse(floorSwitch.getTriggered(), "Floor switch is not triggered when the boulder is not on it");
		
		// move the boulder onto the floor switch
		player.moveRight();
		assertEquals(1, player.getX(), "Player should move towards the floor switch");
		assertEquals(0, player.getY(), "Player should move towards the floor switch");
		assertEquals(2, boulder.getX(), "Boulder should move onto the floor switch");
		assertEquals(0, boulder.getY(), "Boulder should move onto the floor switch");
		assertEquals(2, floorSwitch.getX(), "Floor switch should not move");
		assertEquals(0, floorSwitch.getY(), "Floor switch should not move");
		assertTrue(floorSwitch.getTriggered(), "Floor switch is triggered when the boulder is on it");
		
		// move the player shortcut
		player = new Player(dungeon, 3, 0);
		dungeon.setPlayer(player);
		
		// move the boulder off of the switch
		player.moveLeft();
		assertEquals(2, player.getX(), "Player should move onto the floor switch");
		assertEquals(0, player.getY(), "Player should move onto the floor switch");
		assertEquals(1, boulder.getX(), "Boulder should move off of the floor switch");
		assertEquals(0, boulder.getY(), "Boulder should move off of the floor switch");
		assertEquals(2, floorSwitch.getX(), "Floor switch should not move");
		assertEquals(0, floorSwitch.getY(), "Floor switch should not move");
		assertFalse(floorSwitch.getTriggered(), "Floor switch is not triggered when the boulder is not on it");
	}

}
