/**
 * PlayerState Interface
 */

package unsw.dungeon;

public interface PlayerState {
	public boolean collideEnemy(Enemy enemy, Dungeon dungeon);
	public boolean collideLeprechaun(Leprechaun leprechaun, Dungeon dungeon);
	public boolean collideLaser(Laser laser, Dungeon dungeon,Player p);
	public boolean collideWizard(Wizard wizard, Dungeon dungeon,Player p);
	public boolean collideFire();
}
