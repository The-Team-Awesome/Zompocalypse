package gameWorld.characters;

import gameWorld.Orientation;
import gameWorld.world.World;

import java.awt.*;

/**
 * Player is a human played character in the game.
 *
 * @author Kieran Mckay, 300276166
 */
public final class Player extends MovingCharacter{
	private final int PLAYER_HEALTH = 100;
	private final int PLAYER_SPEED = 5;
	private final int PLAYER_STRENGTH = 20;

	private final int uid;
	private int score;
	private int health;
	private int speed;
	private int strength;

	private String filename;


	public Player(int xCoord, int yCoord, Orientation orientation, int uid, int score, String playerName, String filename) {
		super(xCoord, yCoord, orientation);
		this.score = score;
		this.uid = uid;
		this.filename = filename;
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
		return health <= 0;
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
			super.tick(game);
		}
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

	/**
	 * Draw the player to the screen
	 */
	@Override
	public void draw(int x, int y, Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "Player [uid=" + uid + ", orientation=" + orientation
				+ ", score=" + score + ", health=" + health + ", speed="
				+ speed + ", strength=" + strength + ", filename=" + filename
				+ "]";
	}
}