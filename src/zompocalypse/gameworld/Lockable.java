package zompocalypse.gameworld;

public interface Lockable {
	boolean isLocked();
	boolean unlock(boolean hasKey);
}
