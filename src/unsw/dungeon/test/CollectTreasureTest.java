package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;
import org.junit.jupiter.api.BeforeEach;

class CollectTreasureTest {
	
	Dungeon dungeon;
	Player player;
	Level level;
	Treasure treasure1;
	Treasure treasure2;
	Treasure treasure3;
	Treasure treasure4;
	
	@BeforeEach
	public void initTest() {
		
		Subgoal treasures = new Subgoal("treasure");
        Goal goals = new Goal();
        goals.addSubgoal(treasures);
        level = new Level();
        level.addGoal(goals);
        
		dungeon = new Dungeon(5, 5, level);
		
		player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		
		Wall wall = new Wall(4, 1);
		dungeon.addEntity(wall);
		wall = new Wall(4, 2);
		dungeon.addEntity(wall);
		wall = new Wall(4, 3);
		dungeon.addEntity(wall);
		
		wall = new Wall(1, 2);
		dungeon.addEntity(wall);
		wall = new Wall(2, 2);
		dungeon.addEntity(wall);
		
		wall = new Wall(0, 3);
		dungeon.addEntity(wall);
		wall = new Wall(0, 4);
		dungeon.addEntity(wall);
		wall = new Wall(1, 4);
		dungeon.addEntity(wall);
		
		treasure1 = new Treasure(0,2);
		dungeon.addEntity(treasure1);
		
		treasure2 = new Treasure(1,1);
		dungeon.addEntity(treasure2);
		
		treasure3 = new Treasure(4,0);
		dungeon.addEntity(treasure3);
		
		treasure4 = new Treasure(3,3);
		dungeon.addEntity(treasure4);
		
	}
	
	@Test
	void collectTreasureTest() {
		player.moveDown();
		player.moveDown();
		
		assertFalse(level.getStatus(),"The level must not be complete if any treasure is on the board");
		assertFalse(player.findInventory(treasure1),"Player must not find this treasure in inventory before pickup");
		assertTrue(player.findDungeon(treasure1),"Player must find this treasure in dungeon before pickup");
	
		//player picks up the treasure on the same square
		player.interact();
		
		assertTrue(player.findInventory(treasure1),"Player must find treasure in inventory after pickup");
		assertFalse(player.findDungeon(treasure1),"Player must not find this treasure in dungeon after pickup");
		assertFalse(level.getStatus(),"The level must not be complete if any treasure is on the board");
		
		player.moveUp();
		player.moveRight();
		
		assertFalse(player.findInventory(treasure2),"Player must not find this treasure in inventory before pickup");
		assertTrue(player.findDungeon(treasure2),"Player must find this treasure in dungeon before pickup");
		
		//player picks up the treasure on the same square
		player.interact();
		
		assertTrue(player.findInventory(treasure2),"Player must find treasure in inventory after pickup");
		assertFalse(player.findDungeon(treasure2),"Player must not find this treasure in dungeon after pickup");
		assertFalse(level.getStatus(),"The level must not be complete if any treasure is on the board");
		
		player.moveUp();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		
		assertFalse(player.findInventory(treasure3),"Player must not find this treasure in inventory before pickup");
		assertTrue(player.findDungeon(treasure3),"Player must find this treasure in dungeon before pickup");
		
		//player picks up the treasure on the same square
		player.interact();
		
		assertTrue(player.findInventory(treasure3),"Player must find treasure in inventory after pickup");
		assertFalse(player.findDungeon(treasure3),"Player must not find this treasure in dungeon after pickup");
		assertFalse(level.getStatus(),"The level must not be complete if any treasure is on the board");
		
		player.moveLeft();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		
		assertFalse(player.findInventory(treasure4),"Player must not find this treasure in inventory before pickup");
		assertTrue(player.findDungeon(treasure4),"Player must find this treasure in dungeon before pickup");
		
		//player picks up the treasure on the same square
		player.interact();
		
		assertTrue(player.findInventory(treasure4),"Player must find treasure in inventory after pickup");
		assertFalse(player.findDungeon(treasure4),"Player must not find this treasure in dungeon after pickup");
		assertTrue(level.getStatus(),"The level must be complete as all treasure has been picked up");
		
		
	}

}
