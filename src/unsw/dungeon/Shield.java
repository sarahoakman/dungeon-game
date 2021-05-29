package unsw.dungeon;

public class Shield extends Entity implements PickUpStrategy{
	
	public Shield(int x, int y) {
		super(x, y);
		//initially sets the sword to 5
		setKillable(true);
		setProtectable(true);
		setPickable(true);
		//this.turns = 5;
	}

	@Override
	public Entity pickUp(Player player) {
		if (!player.checkShield()) {
			//adds the shield to the player's inventory
			player.addToInventory(this);
			//removes the shield from the dungeon
			player.removeFromDungeon(this);
			return this;
		}
		return null;
	}
}
