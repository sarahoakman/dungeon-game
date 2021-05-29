package unsw.dungeon.test;

import unsw.dungeon.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LevelCompletionTest {
	
	Dungeon dungeon;
	Player player;
	Level level;
	Exit exit;
	Treasure treasure;
	Boulder boulder;
	Switch floorSwitch;
	
	@BeforeEach
	public void initTest() {
		dungeon = new Dungeon(4, 4, level);
		exit = new Exit(0, 1);
		dungeon.addEntity(exit);
		treasure = new Treasure(1, 0);
		dungeon.addEntity(treasure);
		boulder = new Boulder(2, 0);
		dungeon.addEntity(boulder);
		floorSwitch = new Switch(3, 0);
		dungeon.addEntity(floorSwitch);
		Wall wall = new Wall(0, 3);
		dungeon.addEntity(wall);
		wall = new Wall(0, 2);
		dungeon.addEntity(wall);
		wall = new Wall(1, 2);
		dungeon.addEntity(wall);
		wall = new Wall(2, 2);
		dungeon.addEntity(wall);
	}
	
	@Test
	void enemyGoalTest() {
		// set up the level
		Subgoal enemyGoal = new Subgoal("enemies");
		Goal goals = new Goal();
		goals.addSubgoal(enemyGoal);
		level = new Level();
        level.addGoal(goals);
		dungeon.setLevel(level);
		Sword sword = new Sword(2, 3);
		dungeon.addEntity(sword);
		Enemy enemy = new Enemy(0, 1);
		dungeon.addEntity(enemy);
		player = new Player(dungeon, 1, 3);
		dungeon.setPlayer(player);
		
		// check the level is not passed yet
		assertFalse(level.getStatus(), "Level should not be passed until no enemies are present");
		// get a sword and kill the enemy
		player.moveRight();
		assertTrue(player.checkLocation(sword.getX(), sword.getY()), "The player must be on the sword to pick it up");
		player.interact();
		assertTrue(player.findInventory(sword), "The player must have the sword when it is picked up successfully");
		assertFalse(player.findDungeon(sword), "The player can not find the sword on the board after it has been picked up");
		player.moveRight();
		player.moveUp();
		player.interact();
		// check the enemy is gone and the level is complete
		assertFalse(player.findDungeon(enemy), "The enemy can not be found on the board after it is hit");
		assertTrue(player.findInventory(sword), "The sword should remain in the inventory as it still has hits left");
		assertTrue(level.getStatus(), "The level is complete as all enemies are gone");
	}

	@Test
	void exitGoalTest() {
		// set up the level
		Subgoal exitGoal = new Subgoal("exit");
		Goal goals = new Goal();
		goals.addSubgoal(exitGoal);
		level = new Level();
        level.addGoal(goals);
		dungeon.setLevel(level);
		player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		assertFalse(level.getStatus(), "Level should not be passed until player reaches exit");
		player.moveDown();
		assertTrue(player.checkLocation(exit.getX(), exit.getY()), "The player is standing on the exit");
		assertTrue(level.getStatus(), "Level is complete as the player has reached the exit");
	}
	
	@Test
	void treasureGoalTest() {
		// set up the level
		Subgoal treasureGoal = new Subgoal("treasure");
		Goal goals = new Goal();
		goals.addSubgoal(treasureGoal);
		level = new Level();
        level.addGoal(goals);
		dungeon.setLevel(level);
		Treasure anotherTreasure = new Treasure(1, 1);
		dungeon.addEntity(anotherTreasure);
		player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		assertFalse(level.getStatus(), "Level should not be passed until all treasure collected");
		player.moveRight();
		assertTrue(player.checkLocation(treasure.getX(), treasure.getY()), "The player is standing on the treasure");
		player.interact();
		assertTrue(player.findInventory(treasure), "The treasure must be in the inventory after picking it up");
		assertFalse(player.findDungeon(treasure), "The treasure must not be in the dungeon after picking up");
		// still need to find another treasure
		assertFalse(level.getStatus(), "Level should not be passed until all treasure collected");
		player.moveDown();
		assertTrue(player.checkLocation(anotherTreasure.getX(), anotherTreasure.getY()), "The player is standing on the treasure");
		player.interact();
		assertTrue(player.findInventory(anotherTreasure), "The treasure must be in the inventory after picking it up");
		assertFalse(player.findDungeon(anotherTreasure), "The treasure must not be in the dungeon after picking up");
		assertTrue(level.getStatus(), "Level is complete as the player has collected all treasure");
	}
	
	@Test
	void boulderAndSwitchTest() {
		// set up the level
		Subgoal boulderGoal = new Subgoal("boulders");
		Goal goals = new Goal();
		goals.addSubgoal(boulderGoal);
		level = new Level();
        level.addGoal(goals);
		dungeon.setLevel(level);
		Boulder anotherBoulder = new Boulder(3, 2);
		dungeon.addEntity(anotherBoulder);
		Switch anotherSwitch = new Switch(3, 3);
		dungeon.addEntity(anotherSwitch);
		player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		assertFalse(floorSwitch.getTriggered(), "Floor switch is not triggered when no boulder is on it");
		assertFalse(anotherSwitch.getTriggered(), "Floor switch is not triggered when no boulder is on it");
		assertFalse(level.getStatus(), "Level should not be passed until all switches are triggered");
		// putting a boulder onto the switch
		player.moveRight();
		player.moveRight();
		assertTrue(floorSwitch.getTriggered(), "Floor switch is triggered when the boulder is on it");
		assertFalse(level.getStatus(), "Level should not be passed until all switches are triggered");
		// putting another boulder on another switch
		player.moveDown();
		player.moveRight();
		player.moveDown();
		assertTrue(anotherSwitch.getTriggered(), "Floor switch is triggered when the boulder is on it");
		assertTrue(level.getStatus(), "Level is complete as the player has collected all treasure");
	}
	
	@Test
	void exitOrTest() {
		// set up the level
		Subgoal treasureGoal = new Subgoal("treasure");
		Subgoal exitGoal = new Subgoal("exit");
		Goal goals = new Goal("OR");
		goals.addSubgoal(treasureGoal);
		goals.addSubgoal(exitGoal);
		level = new Level();
        level.addGoal(goals);
		dungeon.setLevel(level);
		player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		assertFalse(level.getStatus(), "Level should passed if player gets treasure or reaches exit");
		player.moveDown();
		assertTrue(player.checkLocation(exit.getX(), exit.getY()), "The player is standing on the exit");
		assertTrue(level.getStatus(), "Level is complete as the player reached the exit without getting treasure");
	}
	
	@Test
	void failANDtest() {
		// set up the level
		Subgoal treasureGoal = new Subgoal("treasure");
		Subgoal exitGoal = new Subgoal("exit");
		Goal goals = new Goal("AND");
		goals.addSubgoal(treasureGoal);
		goals.addSubgoal(exitGoal);
		level = new Level();
        level.addGoal(goals);
		dungeon.setLevel(level);
		player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		assertFalse(level.getStatus(), "Level should not be passed until player gets treasure and reaches exit");
		player.moveDown();
		assertTrue(player.checkLocation(exit.getX(), exit.getY()), "The player is standing on the exit");
		assertFalse(level.getStatus(), "Level is not complete as the player reached the exit without getting treasure");
	}
	
	@Test
	void ANDtest() {
		// set up the level
		Subgoal treasureGoal = new Subgoal("treasure");
		Subgoal exitGoal = new Subgoal("exit");
		Goal goals = new Goal("AND");
		goals.addSubgoal(treasureGoal);
		goals.addSubgoal(exitGoal);
		level = new Level();
        level.addGoal(goals);
		dungeon.setLevel(level);
		player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		// treasure goal
		assertFalse(level.getStatus(), "Level should not be passed until treasure collected");
		player.moveRight();
		assertTrue(player.checkLocation(treasure.getX(), treasure.getY()), "The player is standing on the treasure");
		player.interact();
		assertTrue(player.findInventory(treasure), "The treasure must be in the inventory after picking it up");
		assertFalse(player.findDungeon(treasure), "The treasure must not be in the dungeon after picking up");
		assertFalse(level.getStatus(), "Level should not be passed until second goal is completed");
		
		// exit goal
		assertFalse(level.getStatus(), "Level should not be passed until player gets treasure and reaches exit");
		player.moveDown();
		player.moveLeft();
		assertTrue(player.checkLocation(exit.getX(), exit.getY()), "The player is standing on the exit");
		assertTrue(level.getStatus(), "Level is complete as the player reached the exit and got treasure");
	}
}
