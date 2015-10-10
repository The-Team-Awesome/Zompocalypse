package zompocalypse.gameworld.characters;

import java.util.PriorityQueue;

import zompocalypse.gameworld.Direction;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.world.World;

/**
 * The moving character class represents characters in the game world which
 * move. Moving characters have a direction of movement, and a speed at which
 * they are moving.
 *
 * @author Kieran Mckay, 300276166
 */
public abstract class MovingCharacter extends Actor {

	private static final long serialVersionUID = 1L;
	protected boolean moving;

	private int health;
	private int speed;   //non monving zomies have 0 speed?
	private int strength;

	protected Orientation ori;  //the orientation that the player is moving in

	public MovingCharacter(int uid, World game, int xCoord, int yCoord, Orientation direction,
			String[] filenames) {
		super(uid, game, xCoord, yCoord, direction, filenames);

		this.moving = false;
	}

	/**
	 * Check if this character is dead.
	 */
	public boolean isDead() {
		return health <= 0;
	}

	/**
	 * Get this characters remaining health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Get this characters strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * Updates the characters health after taking damage
	 * @param damage
	 */
	public void damaged(int damage) {
		setHealth(getHealth() - damage);
	}

	/**
	 * The tick method is provided to enable computer control characters to
	 * make decisions.
	 */
	@Override
	public void tick(World game) {
		if(!moving){
			return;
		}
		moving = false;

		// Attempt to update the character's position. This is done by
		// speculating at the new board position and then deciding if this
		// should be allowed or not.
		int oldX = xCoord;
		int oldY = yCoord;

		int newX;
		int newY;

		int width = game.width();
		int height = game.height();

		if(queued == Orientation.NORTH) {
			newX = xCoord;
			newY = yCoord -1;
			ori = queued;
		}
		else if(queued == Orientation.SOUTH) {
			newX = xCoord;
			newY = yCoord +1;
			ori = queued;
		}
		else if(queued == Orientation.EAST) {
			newX = xCoord + 1;
			newY = yCoord;
			ori = queued;
		}
		else if(queued == Orientation.WEST) {
			newX = xCoord - 1;
			newY = yCoord;
			ori = queued;
		}
		else {
			throw new IllegalStateException();
		}

		if(newX < 0 || newY < 0 || newX >= width || newY >= height) {
			return;
		}

		//If the game is not blocked, then the position can be updated
		if(!game.isBlocked(newX,newY)) {
			PriorityQueue<GameObject> objects[][] = game.getObjects();

			xCoord = newX;
			yCoord = newY;
			objects[oldX][oldY].remove(this);
			objects[newX][newY].add(this);
		}
	}

	/**
	 * Sets the orientation for the character to move in
	 */
	public void setOrientation(Orientation orientation) {
		this.ori = orientation;
	}

	/**
	 * Get this characters speed
	 */
	public void setSpeed(int s) {
		this.speed = s;
	}

	/**
	 * Set this characters health
	 */
	public void setHealth(int h) {
		this.health = h;
	}

	/**
	 * Set this characters strength
	 */
	public void setStrength(int st) {
		this.strength = st;
	}

	/**
	 * Get this characters speed
	 */
	public int getSpeed() {
		return this.speed;
	}

	/**
	 * Determine the direction in which this character is moving.
	 */
	public Orientation getOrientation() {
		return ori;
	}

	/**
	 * Changes the new orientation to North, updates that it must be changed.
	 */
	public void moveNorth() {
		queued = Orientation.NORTH;
		moving = true;
	}

	/**
	 * Changes the new orientation to East, updates that it must be changed.
	 */
	public void moveEast() {
		queued = Orientation.EAST;
		moving = true;
	}

	/**
	 * Changes the new orientation to South, updates that it must be changed.
	 */
	public void moveSouth() {
		queued = Orientation.SOUTH;
		moving = true;
	}

	/**
	 * Changes the new orientation to West, updates that it must be changed.
	 */
	public void moveWest() {
		queued = Orientation.WEST;
		moving = true;
	}

	/**
	 * Rotates the perspective of the player view
	 * @param value
	 */
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
	 * Updates the current orientation of the viewer to its anticlockwise
	 * counterpart.
	 */
	protected void updateCurrentOrientationAntiClockwise() {
		setOrientation(Orientation.getPrev(getOrientation()));
	}

	/**
	 * Updates the current orientation of the viewer to its clockwise
	 * counterpart.
	 */
	protected void updateCurrentOrientationClockwise() {
		setOrientation(Orientation.getNext(getOrientation()));
	}
}
