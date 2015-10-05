package zompocalypse.gameworld;

import java.awt.Graphics;
import java.io.Serializable;

/**
 * Drawable is an interface representing any class in the game which is able to be rendered in the graphics window.
 *
 * @author Kieran Mckay, 300276166
 */
public interface Drawable extends Serializable {

	/**
	 * Returns the file name of the sprite associated with this object
	 */
	public String getFileName();

	/**
	 * Draws the current object on the graphics object at a specified X,Y coordinate
	 *
	 * @param x - X coordinate to draw this item at
	 * @param y - Y coordinate to draw this item at
	 * @param g - Graphics object upon which to draw this item
	 * @param worldOrientation
	 */
	public void draw(int x, int y, Graphics g, Orientation worldOrientation);
}
