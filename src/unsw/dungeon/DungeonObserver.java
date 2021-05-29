/**
 * DungeonObserver Interface
 */

package unsw.dungeon;

public interface DungeonObserver {
	public void Notify();
	public void Attach(EntityObserver e);
	public void Detatch(EntityObserver e);
}
