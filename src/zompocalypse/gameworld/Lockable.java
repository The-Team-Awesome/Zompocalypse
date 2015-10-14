package zompocalypse.gameworld;

/**
 * An interface implemented by any Game Object which can be locked. Locked items
 * require keys to be unlocked.
 *
 * @author Kieran Mckay, 300276166
 */
public interface Lockable {
	/**
	 * Returns a boolean determining whether this object is locked or not.
	 *
	 * @return true if locked, false if unlocked
	 */
	boolean isLocked();

	/**
	 * Attempt to unlock the current object, requires a key to successfully
	 * unlock
	 *
	 * @param hasKey
	 *            - boolean whether or not the player trying to unlock this
	 *            object has a Key or not
	 * @return true if successfully unlocked, false if object could not be
	 *         unlocked, or was already unlocked
	 */
	boolean unlock(boolean hasKey);
}
