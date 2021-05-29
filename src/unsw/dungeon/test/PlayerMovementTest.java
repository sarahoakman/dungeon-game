package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;
//import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


public class PlayerMovementTest {
	Dungeon dungeon;
	Player player;
	
	@BeforeEach
	public void initTests() {
		
        Goal goals = new Goal();
        Level level = new Level();
        level.addGoal(goals);
        
		dungeon = new Dungeon(6, 6, level);
		
		player = new Player(dungeon, 4, 0);
		dungeon.setPlayer(player);
		
		Wall wall = new Wall(0, 0);
		dungeon.addEntity(wall);
		wall = new Wall(1, 0);
		dungeon.addEntity(wall);
		wall = new Wall(2, 0);
		dungeon.addEntity(wall);
		
		wall = new Wall(2, 2);
		dungeon.addEntity(wall);
		wall = new Wall(2, 3);
		dungeon.addEntity(wall);
		
		wall = new Wall(0, 4);
		dungeon.addEntity(wall);
		wall = new Wall(0, 5);
		dungeon.addEntity(wall);
		wall = new Wall(1, 5);
		dungeon.addEntity(wall);
		
		wall = new Wall(3, 5);
		dungeon.addEntity(wall);
		wall = new Wall(4, 5);
		dungeon.addEntity(wall);
		wall = new Wall(5, 4);
		dungeon.addEntity(wall);
		wall = new Wall(5, 5);
		dungeon.addEntity(wall);
		
		wall = new Wall(5, 3);
		dungeon.addEntity(wall);
		wall = new Wall(4, 3);
		dungeon.addEntity(wall);
		wall = new Wall(4, 2);
		dungeon.addEntity(wall);
		wall = new Wall(4, 1);
		dungeon.addEntity(wall);
	}
	
	@Test
	void playerMovement() {
		
		player.moveRight();
		assertEquals(5, player.getX(), "Player must move on the x-axis when moving right");
		assertEquals(0, player.getY(), "Player must not move on the y-axis when moving right");
		
		player.moveRight();
		assertEquals(5, player.getX(), "Player must not move if blocked by edge");
		assertEquals(0, player.getY(), "Player must not move if blocked by edge");
		
		player.moveLeft();
		assertEquals(4, player.getX(), "Player must move on the x-axis when moving left");
		assertEquals(0, player.getY(), "Player must not move on the y-axis when moving left");
		
		
		player.moveLeft();
		assertEquals(3, player.getX(), "Player must move on the x-axis when moving left");
		assertEquals(0, player.getY(), "Player must not move on the y-axis when moving left");
		
		player.moveLeft();
		assertEquals(3, player.getX(), "Player must not move if blocked");
		assertEquals(0, player.getY(), "Player must not move if blocked");
		
		player.moveDown();
		assertEquals(3, player.getX(), "Player must not move on the x-axis when moving down");
		assertEquals(1, player.getY(), "Player must move on the y-axis when moving down");
		
		player.moveRight();
		assertEquals(3, player.getX(), "Player must not move if blocked");
		assertEquals(1, player.getY(), "Player must not move if blocked");
		
		player.moveDown();
		assertEquals(3, player.getX(), "Player must not move on the x-axis when moving down");
		assertEquals(2, player.getY(), "Player must move on the y-axis when moving down");
		
		player.moveDown();
		assertEquals(3, player.getX(), "Player must not move on the x-axis when moving down");
		assertEquals(3, player.getY(), "Player must move on the y-axis when moving down");
		
		player.moveUp();
		assertEquals(3, player.getX(), "Player must not move on the x-axis when moving up");
		assertEquals(2, player.getY(), "Player must move on the y-axis when moving up");
		
		player.moveDown();
		assertEquals(3, player.getX(), "Player must not move on the x-axis when moving down");
		assertEquals(3, player.getY(), "Player must move on the y-axis when moving down");
		
		player.moveDown();
		assertEquals(3, player.getX(), "Player must not move on the x-axis when moving down");
		assertEquals(4, player.getY(), "Player must move on the y-axis when moving down");
		
		player.moveRight();
		assertEquals(4, player.getX(), "Player must move on the x-axis when moving right");
		assertEquals(4, player.getY(), "Player must not move on the y-axis when moving right");
		
		player.moveRight();
		assertEquals(4, player.getX(), "Player must not move if blocked");
		assertEquals(4, player.getY(), "Player must not move if blocked");
		
	
	}
	
	
}