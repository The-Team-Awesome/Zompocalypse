package gameWorld;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import userInterface.appWindow.World;

public final class Player extends MovingCharacter {
	private final int uid;
	private int score;
	private int health;
	private int speed;
	private int strength;

	public Player(int realX, int realY, int dir, int uid, int score) {
		super(realX,realY,dir);
		this.score = score;
		this.uid = uid;

		this.health = 100;
		this.speed = 5;
		this.strength = 20;
	}

	/**
	 * Get this players unique identifier.
	 */
	public int uid() {
		return uid;
	}

	/**
	 * Get this players score.
	 */
	public int score() {
		return score;
	}

	/**
	 * Get this players remaining health
	 */
	public int health() {
		return health;
	}

	/**
	 * Get this players strength
	 */
	public int strength() {
		return strength;
	}

	/**
	 * Get this players speed
	 */
	public int speed() {
		return speed;
	}

	/**
	 * Check if this player is dead.
	 */
	public boolean isDead() {
		return health > 0;
	}

	/**
	 * Add to this players score.
	 */
	public void addScore(int points) {
		score += points;
	}

	public void tick(World game) {
		if(!isDead()){
			//do things here
		}
	}

	public void toOutputStream(DataOutputStream dout) throws IOException {
		dout.writeShort(realX);
		dout.writeShort(realY);
		dout.writeByte(uid);
		dout.writeByte(direction);
		dout.writeShort(score);

	}

	/**
	 * Construct a player from an input stream.
	 */
	public static Player fromInputStream(int rx, int ry, DataInputStream din) throws IOException {
		int uid = din.readByte();
		int dir = din.readByte();
		int score = din.readShort();
		return new Player(rx, ry, dir, uid, score);
	}

	/**
	 * Draw the player to the screen
	 */
	public void draw(Graphics g) {

	}

	/**
	 * Draw the player that is yours to the screen different so you know which one is you
	 */
	public void drawOwn(Graphics g) {

	}
}