package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;
import org.junit.jupiter.api.BeforeEach;

class ExitTest {
	Dungeon dungeon;
	Player player;
	Level level;
	
	@BeforeEach
	public void initTest() {
		Subgoal exits = new Subgoal("exit");
        Goal goals = new Goal();
        goals.addSubgoal(exits);
        level = new Level();
        level.addGoal(goals);
        
		dungeon = new Dungeon(4, 4, level);
		
		player = new Player(dungeon, 1, 2);
		dungeon.setPlayer(player);
		
		
		Wall wall = new Wall(2, 1);
		dungeon.addEntity(wall);
		wall = new Wall(1, 1);
		dungeon.addEntity(wall);
		wall = new Wall(0, 1);
		dungeon.addEntity(wall);
		
		wall = new Wall(0, 2);
		dungeon.addEntity(wall);
		wall = new Wall(0, 3);
		dungeon.addEntity(wall);
		
		wall = new Wall(1, 3);
		dungeon.addEntity(wall);
		wall = new Wall(2, 3);
		dungeon.addEntity(wall);
		
		Exit exit = new Exit(0,0);
		dungeon.addEntity(exit);
	}
	
	@Test
	void exitTest() {
		
		assertFalse(level.getStatus(),"The level must not be complete if the player has not reached the exit");
		
		player.moveRight();
		player.moveRight();
		
		assertFalse(level.getStatus(),"The level must not be complete if the player has not reached the exit");
		
		player.moveUp();
		player.moveUp();
		
		assertFalse(level.getStatus(),"The level must not be complete if the player has not reached the exit");
		
		player.moveLeft();
		player.moveLeft();
		player.moveLeft();
		
		assertTrue(level.getStatus(),"The level must be complete if the player has  reached the exit");
		
	}

}
