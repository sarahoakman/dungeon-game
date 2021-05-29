package unsw.dungeon.test;

import unsw.dungeon.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsingKeysAndDoorsTest {

	Dungeon dungeon;
	Player player;
	Door door;
	Key key, anotherKey;
	Sword sword;

	@BeforeEach
	public void initTests() {
		
		// set up a level to avoid null errors
		Subgoal treasure = new Subgoal("treasure");
		Goal goals = new Goal();
        goals.addSubgoal(treasure);
        Level level = new Level();
        level.addGoal(goals);
		        
		dungeon = new Dungeon(4, 4, level);
		
		door = new Door(3, 3, 0);
		dungeon.addEntity(door);
		
		key = new Key(1, 0, 0);
		dungeon.addEntity(key);
		
		anotherKey = new Key(2, 0, 1);
		dungeon.addEntity(anotherKey);
		
		Door anotherDoor = new Door(0, 3, 1);
		dungeon.addEntity(anotherDoor);
		
		sword = new Sword(1, 1);
		dungeon.addEntity(sword);
		
		dungeon.connectKeysAndDoors();
		
		player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
	}
	
	
	@Test
	void noKeyToDropTest() {
		assertFalse(player.checkKey(), "Player must not have a key before picking up");
		player.interact();
		assertFalse(player.checkKey(), "Player must not have a key before picking up");
	}
	
	@Test
	void pickingUpKeyTest() {
		// picking up one key
		assertFalse(player.checkKey(), "Player must not have a key before picking up");
		player.moveRight();
		assertTrue(player.findDungeon(key), "Player must find the key in the dungeon to pickup");
		assertTrue(player.checkLocation(key.getX(), key.getY()), "Player and key must be on the same square to pick up");
		player.interact();
		assertTrue(player.findInventory(key), "Player must have the key in the inventory after pickup");
		assertFalse(player.findDungeon(key), "Player must not find the key in the dungeon after pickup");
	}
	
	@Test
	void pickingMultipleKeysTest() {
		// already have one key
		pickingUpKeyTest();
		assertTrue(player.checkKey(), "Player has picked up a key already");
		player.moveRight();
		assertTrue(player.findDungeon(anotherKey), "Player must find the key in the dungeon to pickup");
		assertTrue(player.checkLocation(anotherKey.getX(), anotherKey.getY()), "Player and key must be on the same square to pick up");
		player.interact();
		assertFalse(player.findInventory(anotherKey), "Player must not have multiple keys in the inventory");
		assertTrue(player.findDungeon(anotherKey), "Player must find the key remains in the dungeon");
	}
	
	@Test
	void droppingOnOccupiedSquare() {
		// pick up a key
		pickingUpKeyTest();
		assertTrue(player.checkKey(), "Player has picked up a key already");
		// unsuccessful drop
		player.moveDown();
		assertTrue(player.checkLocation(sword.getX(), sword.getY()), "Player is on the same square as the sword");
		player.interact();
		assertTrue(player.findInventory(key), "Player must have the key in the inventory after unsuccessful drop");
		assertFalse(player.findDungeon(key), "Player must not find the key in the dungeon after unsuccessful drop");
	}
	
	@Test
	void droppingKeyWithoutDoor() {
		
		// pick up a key
		pickingUpKeyTest();
		assertTrue(player.checkKey(), "Player has picked up a key already");
		player.moveLeft();
		assertFalse(door.checkKey(player.getX(), player.getY()), "Player is not adjacent to the door");
		// drop the key in the wrong place
		player.interact();
		assertFalse(player.findInventory(key), "Player must not have the key in the inventory after dropping");
		assertTrue(player.findDungeon(key), "Player must find the key in the dungeon after dropping without a door");
		// check if the player cannot walk through the door 
	}
	
	@Test
	void droppingKeyAndUnlockingDoor() {
		// pick up a key
		pickingUpKeyTest();
		assertTrue(player.checkKey(), "Player has picked up a key already");
		player.moveRight();
		player.moveRight();
		player.moveDown();
		player.moveDown();
		int x = player.getX();
		int y = player.getY();
		assertTrue(door.checkKey(player.getX(), player.getY()), "Player is adjacent to the door");
		player.moveDown();
		assertEquals(x, player.getX(), "Player cannot move through a locked door");
		assertEquals(y, player.getY(), "Player cannot move through a locked door");
		// drop the key in the correct place
		player.interact();
		assertFalse(player.findInventory(key), "Player must not have the key in the inventory after dropping");
		assertFalse(player.findDungeon(key), "Player must not find the key in the dungeon after dropping with a door");
		// check if the player can walk through the door
		player.moveDown();
		assertEquals(x, player.getX(), "Player does not move in the x-axis when moving down");
		assertEquals(y + 1, player.getX(), "Player moves down, through a locked door");
	}
}
