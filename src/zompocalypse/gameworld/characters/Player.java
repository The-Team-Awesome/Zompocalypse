package zompocalypse.gameworld.characters;

import java.awt.*;

import javax.swing.ImageIcon;

import com.sun.imageio.plugins.common.ImageUtil;

import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.world.World;
import zompocalypse.ui.rendering.ImageUtils;

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

	private String [] filenames;
	private ImageIcon[] images;
	private String imageName;
	private ImageIcon currentImage;

	public Player(int xCoord, int yCoord, Orientation orientation, int uid, int score, String playerName, String [] filenames) {
		super(xCoord, yCoord, orientation);
		this.score = score;
		this.uid = uid;
		this.filenames = filenames;
		this.health = PLAYER_HEALTH;
		this.speed = PLAYER_SPEED;
		this.strength = PLAYER_STRENGTH;

		ImageUtils imu = ImageUtils.getImageUtilsObject();
		this.images = imu.setupImages(filenames);
		this.currentImage = images[0];
		this.imageName = filenames[0];
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
			System.out.println(this.xCoord + "," + this.yCoord);

			ImageUtils imu = ImageUtils.getImageUtilsObject();
			currentImage = imu.getCurrentImageForOrientation(orientation, images);
		}
	}

	/**
	 * Draw the player that is yours to the screen different so you know which one is you
	 */
	public void drawOwn(Graphics g) {

	}

	@Override
	public String getFileName() {
		return this.imageName;
	}

	//draw
	/**
	 * Draw the player to the screen
	 */
	public void draw(int realx, int realy, Graphics g) {
		//g.setColor(Color.MAGENTA);
		//g.fillRect(realx, realy, 30, 30);

		g.drawImage(currentImage.getImage(), realx, realy - 20, null);
	}

	@Override
	public String toString() {
		return "Player [uid=" + uid + ", orientation=" + orientation
				+ ", score=" + score + ", health=" + health + ", speed="
				+ speed + ", strength=" + strength + ", filename=" + filenames
				+ "]";
	}
}