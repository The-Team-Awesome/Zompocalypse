package gameWorld;

import java.awt.Graphics;

/**
 * A Character is a record of information about a particular character in the
 * game. There are essentially two kinds of characters: player controlled and
 * computer controlled.
 */
public abstract class Character implements Drawable{
	protected int realX; // real x-position
	protected int realY; // real y-position

	public Character(int realX, int realY) {
		this.realX = realX;
		this.realY = realY;
	}

	public int realX() {
		return realX;
	}

	public int realY() {
		return realY;
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
	public abstract void draw(Graphics g);
}
