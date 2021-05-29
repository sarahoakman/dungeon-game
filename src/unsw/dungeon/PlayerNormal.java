package unsw.dungeon;

public class PlayerNormal implements PlayerState {
	
	@Override
	public boolean collideEnemy(Enemy enemy, Dungeon dungeon) {
		return false;
	}

	@Override
	public boolean collideLeprechaun(Leprechaun leprechaun, Dungeon dungeon) {
		dungeon.releaseTreasure();
		return false;
	}

	@Override
	public boolean collideLaser(Laser laser, Dungeon dungeon,Player p) {
		//checks if the player has a shield
		if (p.checkShield()) {
			return true;
		}
		return false;
		
	}

	@Override
	public boolean collideWizard(Wizard wizard, Dungeon dungeon,Player p) {
		return false;
	}
	
	@Override
	public boolean collideFire() {
		return true;
	}

}
