package gameWorld;

import java.awt.*;
import userInterface.renderWindow.Orientation;

public final class Player extends MovingCharacter implements Drawable {
	private final int PLAYER_HEALTH = 100;
	private final int PLAYER_SPEED = 5;
	private final int PLAYER_STRENGTH = 20;

	private final int uid;
	private Orientation orientation;
	private int score;
	private int health;
	private int speed;
	private int strength;

	private String filename;


	public Player(int realX, int realY, int dir, int uid, int score, String playerName, String filename) {
		super(realX,realY,dir);
		this.score = score;
		this.uid = uid;
		this.filename = filename;
		this.orientation = Orientation.NORTH;
		this.health = PLAYER_HEALTH;
		this.speed = PLAYER_SPEED;
		this.strength = PLAYER_STRENGTH;
	}

	/**
	 * Get this players unique identifier.
	 */
	public int getUID() {
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
	@Override
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

	@Override
	public void tick(World game) {
		if(!isDead()){
			//do things here
		}
	}

	/**
	 * Draw the player to the screen
	 */
	@Override
	public void draw(Graphics g) {

	}

	/**
	 * Draw the player that is yours to the screen different so you know which one is you
	 */
	public void drawOwn(Graphics g) {

	}

	@Override
	public String getFileName() {
		return filename;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	@Override
	public void draw(int x, int y, Graphics g) {
		// TODO Auto-generated method stub

	}
}