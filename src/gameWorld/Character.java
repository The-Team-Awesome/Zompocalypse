package gameWorld;

import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A Character is a record of information about a particular character in the
 * game. There are essentially two kinds of characters: player controlled and
 * computer controlled.
 */
public abstract class Character {
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

	/**
	 * The following method is provided to simplify the process of writing a
	 * given character to the output stream.
	 */
	public abstract void toOutputStream(DataOutputStream dout)
			throws IOException;

	// Character type constants
	public static final int PLAYER = 0;
	public static final int ZOMBIE = 1;
	public static final int NPC = 2;
	/**
	 * The following constructs a character given a byte array.
	 */
	public static Character fromInputStream(DataInputStream din) throws IOException {
		int type = din.readByte();
		int rx = din.readShort();
		int ry = din.readShort();

		if(type == Character.PLAYER) {
			return Player.fromInputStream(rx,ry,din);
		} else if(type == Character.ZOMBIE) {
			//TODO return ZombieImpl.fromInputStream(rx,ry,din);
			return null;
		} else if(type == Character.NPC) {
			//TODO return NPCImpl.fromInputStream(rx,ry,din);
			return null;
		} else {
			throw new IllegalArgumentException("Unrecognised character type: " + type);
		}
	}
}
