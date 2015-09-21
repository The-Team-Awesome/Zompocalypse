package gameWorld;

import java.awt.Graphics;

public interface Drawable {

	/**
	 * Returns the file name of the sprite associated with this object
	 */
	public String getFileName();

	/**
	 * This object draws itself as appropriate.
	 */
	public void draw(Graphics g);
}
