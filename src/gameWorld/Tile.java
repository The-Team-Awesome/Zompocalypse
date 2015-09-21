package gameWorld;

/**
 * Represents a Tile in the world of Zompocalypse.
 * A Tile is a single location in a 2D array of tiles representing the game world.
 */
public interface Tile {

	/**
	 * Returns the X co-ordinate of this tile's position in the 2D map of the world
	 */
	public int getX();

	/**
	 * Returns the Y co-ordinate of this tile's position in the 2D map of the world
	 */
	public int getY();

	/**
	 * Returns the file name of the sprite associated with this tile.
	 */
	public String getFileName();

	/**
	 * Set the occupiable status of the Tile
	 * @return
	 */
	public void setOccupiable(boolean bool);

	/**
	 * If this tile is able to be occupied.
	 */
	public boolean occupiable();

	/**
	 * Return the code to represent this tile in CSV.
	 */
	public String getCSVCode();
}
