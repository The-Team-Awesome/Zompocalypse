package gameWorld.characters;

import javax.management.RuntimeErrorException;

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

	protected Orientation orientation;
	//protected Orientation queued; 	// queued direction change

	public MovingCharacter(int xCoord, int yCoord, Orientation direction) {
		super(xCoord,yCoord);
		this.orientation = direction;
	}

	/**
	 * Determine the direction in which this character is moving.
	 */
	public Orientation direction() {
		return orientation;
	}

	public void moveNorth() {
		orientation = Orientation.NORTH;
	}

	public void moveEast() {
		orientation = Orientation.EAST;
	}

	public void moveSouth() {
		orientation = Orientation.SOUTH;
	}

	public void moveWest() {
		orientation = Orientation.WEST;
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

		if(orientation == Orientation.NORTH) {
			newX = xCoord;
			newY = yCoord -1;
		} else if(orientation == Orientation.SOUTH) {
			newX = xCoord;
			newY = yCoord +1;
		} else if(orientation == Orientation.EAST) {
			newX = xCoord +1;
			newY = yCoord;
		} else if(orientation == Orientation.WEST) {
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
