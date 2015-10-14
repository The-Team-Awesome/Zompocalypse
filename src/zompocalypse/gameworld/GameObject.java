package zompocalypse.gameworld;

/**
 * An interface representing all game objects which can exist in the game world.
 *
 * @author Kieran Mckay, 300276166
 */
public interface GameObject extends Drawable, Comparable<GameObject> {

	/**
	 * @return true if another GameObject can occupy this GameObjects location,
	 *         else false
	 */
	public boolean occupiable();

	/**
	 * Compares two GameObjects to determine which should be drawn on top of the
	 * other
	 */
	public int compareTo(GameObject o);
}
