package unsw.dungeon.test;

import unsw.dungeon.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsingPotionsTest {

	Dungeon dungeon;
	Player player;
	Enemy enemy;
	Enemy anotherEnemy;
	Potion potion;
	
	@BeforeEach
	public void initTest() {
		
		// set up a level to avoid null errors
		Subgoal treasure = new Subgoal("treasure");
		Goal goals = new Goal();
		goals.addSubgoal(treasure);
		Level level = new Level();
        level.addGoal(goals);
				        
		dungeon = new Dungeon(4, 4, level);
		potion = new Potion(1, 0);
		dungeon.addEntity(potion);
		enemy = new Enemy(2, 3);
		dungeon.addEntity(enemy);
		anotherEnemy = new Enemy(3, 3);
		dungeon.addEntity(anotherEnemy);
		Wall wall = new Wall(0, 2);
		dungeon.addEntity(wall);
		wall = new Wall(0, 1);
		dungeon.addEntity(wall);
		player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
	}
	
	@Test
	void pickingUpPotionsTest() {
		
		// no pick up as the player isn't on the potion
		player.interact();
		assertFalse(player.findInventory(potion), "The player must not have the potion before being on the same square and picking up");
		assertTrue(player.findDungeon(potion), "The player must find the potion on the board in order to pick it up");
		
		// move to the potion and pick it up
		player.moveRight();
		assertTrue(player.checkLocation(potion.getX(), potion.getY()), "The player must be on the potion to pick it up");
		player.interact();
		assertTrue(player.findInventory(potion), "The player must have the potion when it is picked up successfully");
		assertFalse(player.findDungeon(potion), "The player can not find the potion on the board after it has been picked up");
		
		// checking if the enemy moves away
		player.moveRight();
		assertEquals(0, enemy.getX(), "The enemy moves away from the player");
		assertEquals(3, enemy.getY(), "The enemy moves away from the player");
		player.moveRight();
		assertEquals(0, enemy.getX(), "The enemy remains as far as possible away from the player");
		assertEquals(3, enemy.getY(), "The enemy remains as far as possible away from the player");
		
		// cornering the enemy into the walls
		player.moveDown();
		player.moveDown();
		player.moveLeft();
		player.moveLeft();
		player.moveDown();
		player.moveLeft();
		
		// finally able to collide and kill the enemy
		assertFalse(player.findDungeon(enemy), "Enemy is removed from the dungeon if an invincible player collides");
	}
	
	

}
