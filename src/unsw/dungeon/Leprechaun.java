package unsw.dungeon;

public class Leprechaun extends Entity {
	
	private MoveStrategy moveStrategy;
	
	public Leprechaun(int x, int y) {
		super(x, y);
		this.moveStrategy = new EntityTowards();
		// check if this interferes with enemy is statuses
		setBlockable(true);
		setCollectable(true);
		setExists(true);
	}
	
	public void move(Player player) {
		moveStrategy.move(player, this);
	}
}

