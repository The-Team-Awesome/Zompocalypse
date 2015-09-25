package gameWorld;

import java.util.Map;

/**
 * Represents a Tile in the world of Zompocalypse.
 * A Tile is a single location in a 2D array of tiles representing the game world.
 * 
 * @author Kieran Mckay, 300276166
 */
public interface Tile extends Drawable{

	/**
	 * Returns the X co-ordinate of this tile's position in the 2D map of the world
	 */
	public int getX();

	/**
	 * Returns the Y co-ordinate of this tile's position in the 2D map of the world
	 */
	public int getY();

	/**
	 * Set the occupiable status of the Tile
	 */
	public void setOccupiable(boolean bool);

	/**
	 * If this tile is able to be occupied.
	 */
	public boolean occupiable();

	/**
	 * Return the code to represent this tile in CSV.
	 * @param textTileMap
	 */
	public String getCSVCode(Map<String, String> textTileMap);
}
