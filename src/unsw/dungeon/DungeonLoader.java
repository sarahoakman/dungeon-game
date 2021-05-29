package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;
    
    private String goalStr;
    private List<String> subgoalsStr;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
        this.goalStr = null;
        this.subgoalsStr = new ArrayList<String>();
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return the dungeon that was created
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Level level = setUpLevel();
        
        Dungeon dungeon = new Dungeon(width, height, level);
        
        JSONArray jsonEntities = json.getJSONArray("entities");
        
        // sort the entities to ensure certain images appear above or below others
        JSONArray sortedEntities = sortJsonEntities(jsonEntities);
        jsonEntities = sortedEntities;
        
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        
        // links corresponding keys and doors together
        dungeon.connectKeysAndDoors();
        
        return dungeon;
    }

    /**
     * This function sorted all the entities so that they appear on the board on the correct position
     * @param jsonEntities the entity array to be sorted
     * @return the sorted version of the array
     */
    private JSONArray sortJsonEntities(JSONArray jsonEntities) {
    	JSONArray sortedEntities = new JSONArray();
        for (int j = 0; j < jsonEntities.length(); j++) {
        	String type = jsonEntities.getJSONObject(j).getString("type");
        	//if the entity was a switch or a door
        	if (type.compareTo("switch") == 0 || type.compareTo("door") == 0) {
        		sortedEntities.put(jsonEntities.getJSONObject(j));
        	}
        }
        for (int j = 0; j < jsonEntities.length(); j++) {
        	String type = jsonEntities.getJSONObject(j).getString("type");
        	//if the entity is neither a switch nor a door
        	if (type.compareTo("switch") != 0 && type.compareTo("door") != 0) {
        		if (type.compareTo("player") != 0 && type.compareTo("enemy") != 0 && type.compareTo("leprechaun") != 0) {
        			sortedEntities.put(jsonEntities.getJSONObject(j));
        		}
        	}
        }
        for (int j = 0; j < jsonEntities.length(); j++) {
        	String type = jsonEntities.getJSONObject(j).getString("type");
        	//if the entity was an enemy or a leprechaun
        	if (type.compareTo("enemy") == 0 || type.compareTo("leprechaun") == 0) {
        		sortedEntities.put(jsonEntities.getJSONObject(j));
        	}
        }
        for (int j = 0; j < jsonEntities.length(); j++) {
        	String type = jsonEntities.getJSONObject(j).getString("type");
        	//if the entity was a player
        	if (type.compareTo("player") == 0) {
        		sortedEntities.put(jsonEntities.getJSONObject(j));
        	}
        }
        return sortedEntities;
    }

    /**
     * This function returns the main goal of the level
     * @return the main goal of the level
     */
    public String getMainGoal() {
    	return goalStr;
    }
    
    /**
     * This function gets all the subgoals of the level
     * @return the subgoals of the level
     */
    public List<String> getSubgoals() {
    	return subgoalsStr;
    }
    
	private Level setUpLevel() {
    	// gets the subgoals and makes them into a level  
        Level level;
        // gets the goal condition 
        JSONObject jsonLevel = (JSONObject) json.get("goal-condition");
        // checks if it has the subgoals key (more than one goal)
        if (jsonLevel.has("subgoals")) {
        	// create the goal with either 'AND' or 'OR'
        	Goal goal = new Goal(jsonLevel.getString("goal"));
        	this.goalStr = jsonLevel.getString("goal");
        	// gets the list of subgoal dictionaries and loops through
        	JSONArray subgoals = jsonLevel.getJSONArray("subgoals");
        	for (int i = 0; i < subgoals.length(); i++) {
        		JSONObject subgoal = subgoals.getJSONObject(i);
       
        		// gets subgoals within a subgoal
        		if (subgoal.has("subgoals")) {
        			Goal anotherGoal = new Goal(subgoal.getString("goal"));
        			JSONArray anotherSubgoalList = subgoal.getJSONArray("subgoals");
        			
     
        			// go throuugh subgoals within another subgoal and add to another goal 
        			for (int j = 0; j < anotherSubgoalList.length(); j++) {
        				Subgoal anotherS = new Subgoal(anotherSubgoalList.getJSONObject(j).getString("goal"));
        				anotherGoal.addSubgoal(anotherS);
        				subgoalsStr.add(anotherSubgoalList.getJSONObject(j).getString("goal"));
        			}
        			// add subgoals within a subgoal as a goal
        			goal.addSubgoal(anotherGoal);
        		}
        		else {
	        		// creates a subgoal with the specified goal e.g. 'boulders'
	                Subgoal s = new Subgoal(subgoal.getString("goal"));
	                // add the subgoals to the goal
	                goal.addSubgoal(s);
	                subgoalsStr.add(subgoal.getString("goal"));
        		}
            }
        	// create the level with the composite goal
        	level = new Level();
        	level.addGoal(goal);
        } else {
        	// create a goal
        	Goal goal = new Goal();
        	// create the subgoal (only one)
        	Subgoal s = new Subgoal(jsonLevel.getString("goal"));
        	this.goalStr = jsonLevel.getString("goal");
        	//System.out.println("HEY--" + this.goalStr);
        	// add the subgoal to the goal
        	goal.addSubgoal(s);
        	// create the level with the simple goal
        	level = new Level();
        	level.addGoal(goal);
        }
        return level;
    }
    
	/**
	 * This function loads all the entities into the dungeon
	 * @param dungeon the dungeon where the game is setup
	 * @param json the file being read to find the entities
	 */
    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int id;
        
        Entity entity = null;
        
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "key":
        	id = json.getInt("id");
        	Key key = new Key(x,y,id);
        	onLoad(key);
        	entity = key;
        	break;
        case "door":
        	id = json.getInt("id");
        	Door door = new Door(x,y,id);
        	onLoad(door);
        	entity = door;
        	break;
        case "exit":
        	Exit exit = new Exit(x,y);
        	onLoad(exit);
        	entity = exit;
        	break;
        case "sword":
        	Sword sword = new Sword(x,y);
        	onLoad(sword);
        	entity = sword;
        	break;
        case "enemy":
        	Enemy enemy = new Enemy(x,y);
        	onLoad(enemy);
        	entity = enemy;
        	break;
        case "boulder":
        	Boulder boulder = new Boulder(x,y);
        	onLoad(boulder);
        	entity = boulder;
        	break;
        case "switch":
        	Switch fswitch = new Switch(x,y);
        	onLoad(fswitch);
        	entity = fswitch;
        	break;
        case "invincibility":
        	Potion potion = new Potion(x,y);
        	onLoad(potion);
        	entity = potion;
        	break;
        case "treasure":
        	Treasure treasure = new Treasure(x,y);
        	onLoad(treasure);
        	entity = treasure;
        	break;
        case "portal":
        	id = json.getInt("id");
        	Portal portal = new Portal(x,y,id);
        	onLoad(portal);
        	entity = portal;
        	break;
	    case "leprechaun":
	    	Leprechaun l = new Leprechaun(x,y);
	    	onLoad(l);
	    	entity = l;
	    	break;
	    case "wizard":
	    	id = json.getInt("id");
	    	Wizard w = new Wizard(x,y,id);
	    	onLoad(w);
	    	entity = w;
	    	break;
	    case "laser":
	    	id = json.getInt("id");
	    	Laser la = new Laser(x,y,id);
	    	onLoad(la);
	    	entity = la;
	    	break;
	    case "shield":
	    	Shield sh = new Shield(x,y);
	    	onLoad(sh);
	    	entity = sh;
	    	break;
	    case "flameThrower":
	    	FlameThrower f = new FlameThrower(x,y);
	    	onLoad(f);
	    	entity = f;
	    	break;
	    }

        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Entity player);
    public abstract void onLoad(Wall wall);
    public abstract void onLoad(Key key);
    public abstract void onLoad(Door door);
    public abstract void onLoad(Exit exit);
    public abstract void onLoad(Sword sword);
    public abstract void onLoad(Enemy enemy);
    public abstract void onLoad(Boulder boulder);
    public abstract void onLoad(Switch fswitch);
    public abstract void onLoad(Potion potion);
    public abstract void onLoad(Treasure treasure);
    public abstract void onLoad(Portal portal);
    public abstract void onLoad(Leprechaun leprechaun);
    public abstract void onLoad(Wizard wizard);
    public abstract void onLoad(Laser laser);
    public abstract void onLoad(Shield shield);
    public abstract void onLoad(FlameThrower flameThrower);
}
