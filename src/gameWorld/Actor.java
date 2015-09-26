package gameWorld;

import java.awt.Graphics;

/**
 * A Character is a record of information about a particular character in the
 * game. There are essentially two kinds of characters: player controlled and
 * computer controlled.
 *
 * @author Kieran Mckay, 300276166
 */
public abstract class Actor implements GameObject {
	protected int xCoord; // real x-position
	protected int yCoord; // real y-position

	/**
	 * Constructor taking an X and Y co-ordinate for the current character
	 */
	public Actor(int xCoord, int yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	/**
	 * The current X coordinate for this character
	 * @return X co-ordinate
	 */
	public int getX() {
		return xCoord;
	}

	/**
	 * The current y coordinate for this character
	 * @return Y co-ordinate
	 */
	public int getY() {
		return yCoord;
	}

	/**
	 * The following method is provided to allow characters to take actions on
	 * every clock tick; for example, ghosts may choose new directions to move
	 * in.
	 */
	public abstract void tick(World game);

	/**
	 * This method enables characters to draw themselves onto a given canvas.
	 */
	//public abstract void draw(Graphics g);
}
