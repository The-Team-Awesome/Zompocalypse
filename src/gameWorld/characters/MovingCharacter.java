package gameWorld.characters;

import gameWorld.Orientation;
import gameWorld.world.World;


/**
 * The moving character class represents characters in the game world which
 * move. Moving characters have a direction of movement, and a speed at which
 * they are moving.
 *
 * @author Kieran Mckay, 300276166
 */
public abstract class MovingCharacter extends Actor {

	protected Orientation direction;
	protected Orientation queued; // queued direction change

	public MovingCharacter(int realX, int realY, Orientation direction) {
		super(realX,realY);
		this.direction = direction;
	}

	/**
	 * Determine the direction in which this character is moving.
	 */
	public Orientation direction() {
		return direction;
	}

	public void moveUp() {
		queued = Orientation.NORTH;
	}

	public void moveDown() {
		queued = Orientation.SOUTH;
	}

	public void moveLeft() {
		queued = Orientation.WEST;
	}

	public void moveRight() {
		queued = Orientation.EAST;
	}

	/**
	 * The tick method is provided to enable computer control characters to
	 * make decisions.
	 */
	@Override
	public void tick(World game) {

		// Attempt to update the character's position. This is done by
		// speculating at the new board position and then deciding if this
		// should be allowed or not.
		int oldX = xCoord;
		int oldY = yCoord;
		int newX, newY;
		int width = game.width();
		int height = game.height();

		if(direction == Orientation.NORTH) {
			newX = xCoord;
			newY = yCoord -1;
		} else if(direction == Orientation.SOUTH) {
			newX = xCoord;
			newY = yCoord +1;
		} else if(direction == Orientation.EAST) {
			newX = xCoord +1;
			newY = yCoord;
		} else if(direction == Orientation.WEST) {
			newX = xCoord -1;
			newY = yCoord;
		} else {
			return;
		}

		if(newX < 0 || newY < 0 || newX >= width || newY >= height) {
			return;
		}

		if(game.isWall(newX,newY)) {
			// we've bumped into a wall ... so we have to stop!!
			System.out.println("HIT A WALL");
		} else {
			// we can update our position ...
			xCoord = newX;
			yCoord = newY;
		}
	}

	/**
	 * Determine the speed at which this character moves
	 */
	abstract public int speed();
}
