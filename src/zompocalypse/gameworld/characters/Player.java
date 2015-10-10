package zompocalypse.gameworld.characters;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.ImageIcon;

import zompocalypse.gameworld.Direction;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.items.Item;
import zompocalypse.gameworld.items.Key;
import zompocalypse.gameworld.items.Torch;
import zompocalypse.gameworld.items.Weapon;
import zompocalypse.gameworld.world.World;
import zompocalypse.ui.rendering.ImageUtils;

/**
 * Player is a human played character in the game.
 *
 * @author Kieran Mckay, 300276166
 */
public final class Player extends MovingCharacter {

	private List<Item> inventory;

	private final int PLAYER_HEALTH = 100;
	private final int PLAYER_SPEED = 5;
	private final int PLAYER_STRENGTH = 20;

	private final int uid;
	private int score;
	private int health;
	private int speed;
	private int strength;

	// This is the currently equipped Item
	private Weapon equipped;

	private String[] filenames;
	private ImageIcon[] images;
	private String imageName;
	private ImageIcon currentImage;
	private Orientation orientation;

	public Player(int xCoord, int yCoord, Orientation orientation, int uid,
			int score, String playerName, String[] filenames, World game) {
		super(game, xCoord, yCoord, orientation);
		this.score = score;
		this.uid = uid;
		this.filenames = filenames;
		this.health = PLAYER_HEALTH;
		this.speed = PLAYER_SPEED;
		this.strength = PLAYER_STRENGTH;

		this.inventory = new ArrayList<Item>();
		this.orientation = Orientation.NORTH;

		// TODO: This is just temporary, adding objects to the Players
		// inventory so something is visible when viewing their backpack
		inventory.add(new Key("gold_key_inv.png", 1));
		inventory.add(new Key("gold_key_inv.png", 2));
		inventory.add(new Torch("torch.png", 3));
		equipped = new Weapon("sword_1.png", "A curved blade. Vicious!", 4, 5);

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
	 * Returns a reference to this players game world
	 *
	 * @return the World object this player is acting on
	 */
	public World getWorld() {
		return game;
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
		return strength + equipped.getStrength();
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
		if (!isDead()) {
			super.tick(game);

		}
	}

	/**
	 * Draw the player that is yours to the screen different so you know which
	 * one is you
	 */
	public void drawOwn(Graphics g) {

	}

	@Override
	public String getFileName() {
		return this.imageName;
	}

	/**
	 * Draw the player to the screen
	 */
	public void draw(int realx, int realy, Graphics g,
			Orientation worldOrientation) {
		ImageUtils imu = ImageUtils.getImageUtilsObject();
		Orientation ord = Orientation.getCharacterOrientation(queued, worldOrientation);
		currentImage = imu.getCurrentImageForOrientation(ord,
				images);

		g.drawImage(currentImage.getImage(), realx, realy - 20, null);
	}

	@Override
	public String toString() {
		return "Player [uid=" + uid + ", orientation=" + orientation
				+ ", score=" + score + ", health=" + health + ", speed="
				+ speed + ", strength=" + strength + ", filename=" + filenames
				+ "]";
	}

	public PriorityQueue<GameObject> getObjectsHere() {
		int myX = getX();
		int myY = getY();

		World game = getWorld();
		PriorityQueue<GameObject>[][] worldObs = game.getObjects();

		return worldObs[myX][myY];
	}

	public PriorityQueue<GameObject> getObjectsInfront() {
		int frontX = getX();
		int frontY = getY();
		PriorityQueue<GameObject>[][] worldObs = game.getObjects();

		// Hi, I'm Sam. I changed orientation to queued here because that
		// represents the players actual direction. It may be that orientation
		// was added as a double up, because MovingCharacter also has an
		// orientation field. I'd like to suggest queued is renamed to direction,
		// because it represents what it is more accurately :)

		if (ori == Orientation.NORTH && frontY > 0) {
			return worldObs[frontX][frontY - 1];
		} else if (ori == Orientation.EAST && frontX < game.width() - 1) {
			return worldObs[frontX + 1][frontY];
		} else if (ori == Orientation.SOUTH
				&& frontY < game.height() - 1) {
			return worldObs[frontX][frontY + 1];
		} else if (ori == Orientation.WEST && frontX > 0) {
			return worldObs[frontX - 1][frontY];
		}
		// if we are facing the edge of the world return an empty queue of
		// objects
		return new PriorityQueue<GameObject>();
	}

	@Override
	public int compareTo(GameObject o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean pickUp(Item item) {
		return inventory.add(item);
	}

	// TODO add drop item

	/**
	 * This players inventory items
	 *
	 * @return An Arraylist containing this players inventory
	 */
	public List<Item> getInventory() {
		return inventory;
	}

	public Weapon getEquipped() {
		return equipped;
	}

	public void move(Orientation north, Orientation cameraDirection) {
		Orientation ori = Orientation.getCharacterOrientation(north, cameraDirection);
		switch (ori) {
		case NORTH:
			this.moveNorth();
			break;
		case EAST:
			this.moveEast();
			break;
		case SOUTH:
			this.moveSouth();
			break;
		case WEST:
			this.moveWest();
			break;
		}
	}

	public Orientation getCurrentOrientation() {
		return orientation;
	}

	public void rotatePerspective(Direction value) {
		switch (value) {

		case CLOCKWISE:
			updateCurrentOrientationClockwise();
			return;
		case ANTICLOCKWISE:
			updateCurrentOrientationAntiClockwise();
			return;
		default:
			throw new IllegalArgumentException(
					"Direction wasn't clockwise or anticlockwise");
		}
	}
	/**
	 * Updates the current orientation of the viewer to its clockwise
	 * counterpart.
	 */
	private void updateCurrentOrientationClockwise() {
		switch (orientation) {
		case NORTH:
			orientation = Orientation.EAST;
			return;
		case SOUTH:
			orientation = Orientation.WEST;
			return;
		case EAST:
			orientation = Orientation.SOUTH;
			return;
		case WEST:
			orientation = Orientation.NORTH;
			return;
		default:
			throw new IllegalArgumentException(
					"Current orientation is incorrect");
		}
	}

	/**
	 * Updates the current orientation of the viewer to its anticlockwise
	 * counterpart.
	 */
	private void updateCurrentOrientationAntiClockwise() {
		switch (orientation) {
		case NORTH:
			orientation = Orientation.WEST;
			return;
		case SOUTH:
			orientation = Orientation.EAST;
			return;
		case EAST:
			orientation = Orientation.NORTH;
			return;
		case WEST:
			orientation = Orientation.SOUTH;
			return;
		default:
			throw new IllegalArgumentException(
					"Current orientation is incorrect");
		}
	}

}