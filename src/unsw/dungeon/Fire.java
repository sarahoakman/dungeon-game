package unsw.dungeon;

public class Fire extends Entity {

	public Fire(int x, int y) {
		super(x, y);
		setBlockable(true);
		setKillable(true);
		setTriggerable(true);
	}

}
