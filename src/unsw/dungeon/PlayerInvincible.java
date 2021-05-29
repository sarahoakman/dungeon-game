/**
 * A PlayerInvincible representation
 */

package unsw.dungeon;

public class PlayerInvincible implements PlayerState {
	
	@Override
	public boolean collideEnemy(Enemy enemy, Dungeon dungeon) {
		//removes the enemy from the board
		dungeon.removeEntity(enemy);
		enemy.setExists(false);
		return true;
	}

	@Override
	public boolean collideLeprechaun(Leprechaun leprechaun, Dungeon dungeon) {
		dungeon.releaseTreasure();
		return false;
	}

	@Override
	public boolean collideFire() {
		return false;
	}
	
	@Override
	public boolean collideLaser(Laser laser, Dungeon dungeon,Player p) {
		return true;
	}

	@Override
	public boolean collideWizard(Wizard wizard, Dungeon dungeon,Player p) {
		int id = wizard.getId();
		//kills the wizard on the board
		wizard.killWizard(p);
		return true;
	}
	
	

}
