package unsw.dungeon.test;

import unsw.dungeon.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlockBoulderMovementTest {
	
	Dungeon dungeon;
	Player player;
	Boulder boulder;
	Boulder boulderBlock;
	
	@BeforeEach
	public void initTests() {
		// set up a level to avoid null errors
		Subgoal treasure = new Subgoal("treasure");
        Goal goals = new Goal();
        goals.addSubgoal(treasure);
        Level level = new Level();
        level.addGoal(goals);
        
		dungeon = new Dungeon(7, 7, level);
		
		Wall wall = new Wall(2, 0);
		dungeon.addEntity(wall);
		wall = new Wall(3, 0);
		dungeon.addEntity(wall);
		wall = new Wall(4, 0);
		dungeon.addEntity(wall);
		wall = new Wall(5, 0);
		dungeon.addEntity(wall);
		wall = new Wall(5, 1);
		dungeon.addEntity(wall);
		wall = new Wall(6, 4);
		dungeon.addEntity(wall);
		wall = new Wall(7, 4);
		dungeon.addEntity(wall);
		wall = new Wall(7, 5);
		dungeon.addEntity(wall);
		wall = new Wall(7, 6);
		dungeon.addEntity(wall);
		
		Door door = new Door(1, 5, 0);
		dungeon.addEntity(door);
		
		boulderBlock = new Boulder(1, 2);
		dungeon.addEntity(boulderBlock);
		
	}
	
	@AfterEach
	public void resetTest() {
		dungeon.removeEntity(boulder);
	}
	
	@Test
	public void blockedByDungeonSizeTest() {
		initTestHelper(6, 0, 7, 0);
		player.moveRight();
		assertEquals(6, player.getX(), "Player should not move as they cannot move boulders off the board");
		assertEquals(0, player.getY(), "Player should not move as they cannot move boulders off the board");
		assertEquals(7, boulder.getX(), "Boulder should not move as they cannot go off the board");
		assertEquals(0, boulder.getY(), "Boulder should not move as they cannot go off the board");
	}
	
	@Test
	public void blockedByBoulderTest() {
		initTestHelper(3, 2, 2, 2);
		player.moveLeft();
		assertEquals(3, player.getX(), "Player should not move as they cannot move two boulders");
		assertEquals(2, player.getY(), "Player should not move as they cannot move two boulders");
		assertEquals(2, boulder.getX(), "Boulder should not move as players cannot move two boulders");
		assertEquals(2, boulder.getY(), "Boulder should not move as players cannot move two boulders");
		assertEquals(1, boulderBlock.getX(), "Boulder should not move as players cannot move two boulders");
		assertEquals(2, boulderBlock.getY(), "Boulder should not move as players cannot move two boulders");
	}
	
	@Test
	public void blockedByWallTest() {
		initTestHelper(2, 2, 2, 1);
		player.moveUp();
		assertEquals(2, player.getX(), "Player should not move as they cannot move the boulder");
		assertEquals(2, player.getY(), "Player should not move as they cannot move the boulder");
		assertEquals(2, boulder.getX(), "Boulder should not move as boulders cannot move through walls");
		assertEquals(1, boulder.getY(), "Boulder should not move as boulders cannot move through walls");
	}
	
	@Test
	public void blockedByDoorTest() {
		initTestHelper(1, 3, 1, 4);
		player.moveDown();
		assertEquals(1, player.getX(), "Player should not move as they cannot move the boulder");
		assertEquals(3, player.getY(), "Player should not move as they cannot move the boulder");
		assertEquals(1, boulder.getX(), "Boulder should not move as boulders cannot move through locked doors");
		assertEquals(4, boulder.getY(), "Boulder should not move as boulders cannot move through locked doors");
	}
	
	@Test
	public void blockedByEnemyTest() {
		Enemy enemy = new Enemy(6, 5);
		dungeon.addEntity(enemy);
		initTestHelper(4, 5, 5, 5);
		player.moveRight();
		assertEquals(6, enemy.getX(), "Enemy should not move due to walls and boulders");
		assertEquals(5, enemy.getY(), "Enemy should not move due to walls and boulders");
		assertEquals(4, player.getX(), "Player should not move as they cannot move the boulder");
		assertEquals(5, player.getY(), "Player should not move as they cannot move the boulder");
		assertEquals(5, boulder.getX(), "Boulder should not move as boulders cannot move through enemies");
		assertEquals(5, boulder.getY(), "Boulder should not move as boulders cannot move through enemies");
		
		dungeon.removeEntity(enemy);
	}
	
	private void initTestHelper(int playerX, int playerY, int boulderX, int boulderY) {
		boulder = new Boulder(boulderX, boulderY);
		dungeon.addEntity(boulder);
		player = new Player(dungeon, playerX, playerY);
		dungeon.setPlayer(player);
	}
}
