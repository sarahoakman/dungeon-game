package unsw.dungeon.test;

import unsw.dungeon.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovingBouldersTest {
	Dungeon dungeon;
	Player player;
	Boulder boulder;
	
	@BeforeEach
	public void initTests() {
		// set up a level to avoid null errors
		Subgoal treasure = new Subgoal("treasure");
        Goal goals = new Goal();
        goals.addSubgoal(treasure);
        Level level = new Level();
        level.addGoal(goals);
        
		dungeon = new Dungeon(4, 4, level);
	}
	
	@AfterEach
	public void resetTest() {
		dungeon.removeEntity(boulder);
	}
	
	@Test
	void movingBoulderDownTest() {
		initTestHelper(0, 0, 0, 1);
		player.moveDown();
		assertEquals(0, player.getX(), "Player must not move on the x-axis when moving down");
		assertEquals(1, player.getY(), "Player must move down on the y-axis when moving down");
		assertEquals(0, boulder.getX(), "Boulder must not move on the x-axis when the player moves down");
		assertEquals(2, boulder.getY(), "Boulder must move down on the y-axis when the player moves down");
	}
	
	@Test
	void movingBoulderUpTest() {
		initTestHelper(0, 2, 0, 1);
		player.moveUp();
		assertEquals(0, player.getX(), "Player must not move on the x-axis when moving up");
		assertEquals(1, player.getY(), "Player must move up on the y-axis when moving up");
		assertEquals(0, boulder.getX(), "Boulder must not move on the x-axis when the player moves up");
		assertEquals(0, boulder.getY(), "Boulder must move up on the y-axis when the player moves up");
	}
	
	@Test
	void movingBoulderLeftTest() {
		initTestHelper(2, 1, 1, 1);
		player.moveLeft();
		assertEquals(1, player.getX(), "Player must move right on the x-axis when moving right");
		assertEquals(1, player.getY(), "Player must not move on the y-axis when moving right");
		assertEquals(0, boulder.getX(), "Boulder must move right on the x-axis when the player moves right");
		assertEquals(1, boulder.getY(), "Boulder must not move on the y-axis when the player moves right");
	}
	
	@Test
	void movingBoulderRightTest() {
		initTestHelper(0, 1, 1, 1);
		player.moveRight();
		assertEquals(1, player.getX(), "Player must move left on the x-axis when moving left");
		assertEquals(1, player.getY(), "Player must not move on the y-axis when moving left");
		assertEquals(2, boulder.getX(), "Boulder must move left on the x-axis when the player moves left");
		assertEquals(1, boulder.getY(), "Boulder must not move on the y-axis when the player moves left");
	}
	
	// helper function
	public void initTestHelper(int playerX, int playerY, int boulderX, int boulderY) {
		player = new Player(dungeon, playerX, playerY);
		dungeon.setPlayer(player);
		boulder = new Boulder(boulderX, boulderY);
		dungeon.addEntity(boulder);
	}
}
