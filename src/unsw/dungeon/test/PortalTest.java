package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;
import org.junit.jupiter.api.BeforeEach;


class PortalTest {
	Dungeon dungeon;
	Player player;
	Portal portal1;
	Portal portal2;
	
	@BeforeEach
	public void initTests() {
		
		Goal goals = new Goal();
        Level level = new Level();
        level.addGoal(goals);
        
		dungeon = new Dungeon(6, 6, level);
		
		player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		Wall wall = new Wall(3, 0);
		dungeon.addEntity(wall);
		wall = new Wall(4, 0);
		dungeon.addEntity(wall);
		wall = new Wall(5, 0);
		dungeon.addEntity(wall);
		wall = new Wall(5, 1);
		dungeon.addEntity(wall);
		
		wall = new Wall(3, 5);
		dungeon.addEntity(wall);
		wall = new Wall(4, 5);
		dungeon.addEntity(wall);
		wall = new Wall(5, 5);
		dungeon.addEntity(wall);
		
		wall = new Wall(1, 2);
		dungeon.addEntity(wall);
		wall = new Wall(1, 3);
		dungeon.addEntity(wall);
		wall = new Wall(2, 2);
		dungeon.addEntity(wall);
		wall = new Wall(3, 2);
		dungeon.addEntity(wall);
		wall = new Wall(3, 3);
		dungeon.addEntity(wall);
		
		portal1 = new Portal(2,1,31);
		dungeon.addEntity(portal1);
		
		portal2 = new Portal(2,3,31);
		dungeon.addEntity(portal2);
	}
	
	@Test
	void portalTest() {
		player.moveRight();
		player.moveRight();
		
		//player walks on the portal location
		player.moveDown();
		
		//player teleports on to the adjacent square to the other portal 
		assertEquals(2, player.getX(), "Player must move adjacent to the other portal");
		assertEquals(4, player.getY(), "Player must move adjacent to the other portal");
		
		//player walks back onto portal location
		player.moveUp();
		//player teleports on to the adjacent square to the other portal 
		assertEquals(3, player.getX(), "Player must move adjacent on the empty right square to the other portal");
		assertEquals(1, player.getY(), "Player must move adjacent on the empty right square to the other portal");
				
	}

}
