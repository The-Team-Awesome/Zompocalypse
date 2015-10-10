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
import zompocalypse.gameworld.items.Money;
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

	private static final long serialVersionUID = 1L;

	private List<Item> inventory;

	private final int PLAYER_HEALTH = 100;
	private final int PLAYER_SPEED = 5;
	private final int PLAYER_STRENGTH = 20;

	private int score;

	private final int OFFSETY = -20;

	// This is the currently equipped Item
	private Weapon equipped;

	/**
	 * @param uid
	 * @param score
	 * @param playerName
	 * @param game
	 * @param xCoord
	 * @param yCoord
	 * @param direction
	 * @param filenames
	 */
	public Player(int uid, int score, String playerName, World game, int xCoord, int yCoord, Orientation direction,
			String[] filenames) {
		super(uid, game, xCoord, yCoord, direction, filenames);

		this.score = score;

		setHealth(PLAYER_HEALTH);
		setSpeed(PLAYER_SPEED);
		setStrength(PLAYER_STRENGTH);

		this.inventory = new ArrayList<Item>();

		// TODO: This is just temporary, adding objects to the Players
		// inventory so something is visible when viewing their backpack
		inventory.add(new Key("gold_key_inv.png", 1));
		inventory.add(new Key("gold_key_inv.png", 2));
		inventory.add(new Torch("torch.png", 3));
		inventory.add(new Money("coins_gold.png", 4, 10));
		equipped = new Weapon("sword_1.png", "A curved blade. Vicious!", 5, 5);
	}

	/**
	 * Get this players score.
	 */
	public int score() {
		return score;
	}

	/**
	 * Get this players strength  //TODO
	 */
	@Override
	public int getStrength() {
		return super.getStrength() + equipped.getStrength();
	}

	/**
	 * Add to this players score.
	 */
	public void addScore(int points) {
		score += points;
	}

	/**
	 * Ticks the game over.
	 */
	@Override
	public void tick(World game) {
		if (!isDead()) {
			super.tick(game);
		}
	}

	@Override
	public String toString() {
		return "Player [uid=" + getUid() + ", orientation=" + getOrientation()
				+ ", score=" + score + ", health=" + getHealth() + ", speed="
				+ getSpeed() + ", strength=" + getStrength() + ", charactername=" + getFileName()
				+ "]";
	}

	/**
	 * Gets the priority queue
	 */
	public PriorityQueue<GameObject> getObjectsHere() {
		int myX = getX();
		int myY = getY();

		World game = getWorld();
		PriorityQueue<GameObject>[][] worldObs = game.getObjects();

		return worldObs[myX][myY];
	}

	/**
	 * Gets a priority queue for drawing the objects in the right order.
	 * @return
	 */
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

	/**
	 * Picks up the item and puts it in the inventory.
	 * @param item
	 * @return
	 */
	public boolean pickUp(Item item) {
		return inventory.add(item);
	}

	/**
	 * This players inventory items
	 *
	 * @return An Arraylist containing this players inventory
	 */
	public List<Item> getInventory() {
		return inventory;
	}

	/**
	 * Gets the equipped weapon
	 * @return
	 */
	public Weapon getEquipped() {
		return equipped;
	}

	/**
	 * Moves the camera.
	 *
	 * @param north
	 * @param cameraDirection //TODO
	 */
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

	/**
	 * Rotates the perspective of the player.
	 * @param value
	 */
	public void rotatePerspective(Direction dir) {
		switch (dir) {
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
		Orientation orientation = getOrientation();
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
		Orientation orientation = getOrientation();
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

	/**
	 * Calls super on the draw method
	 */
	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		super.draw(x, y, OFFSETY, g, worldOrientation);
	}
}