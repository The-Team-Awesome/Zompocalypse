package zompocalypse.gameworld;

/**
 * The Stackable interface declares an object to be stackable within a Players
 * inventory. If it is stackable, it should be able to return a count of how
 * many items are in that stack.
 *
 * @author Sam Costigan
 */
public interface Stackable {

	/**
	 * A Stackable Item should have a count which it can return.
	 *
	 * @return - The count of the Stackable Item
	 */
	public int getCount();

}
