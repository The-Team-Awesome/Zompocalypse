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

	protected Orientation orientation;
	protected boolean moving;
	protected Orientation queued; 	// queued direction change

	public MovingCharacter(int xCoord, int yCoord, Orientation direction) {
		super(xCoord,yCoord);
		this.orientation = direction;
		this.moving = false;
	}

	/**
	 * Determine the direction in which this character is moving.
	 */
	public Orientation direction() {
		return orientation;
	}

	public void moveNorth() {
		queued = Orientation.NORTH;
		moving = true;
	}

	public void moveEast() {
		queued = Orientation.EAST;
		moving = true;
	}

	public void moveSouth() {
		queued = Orientation.SOUTH;
		moving = true;
	}

	public void moveWest() {
		queued = Orientation.WEST;
		moving = true;
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
		int newX, newY;
		int width = game.width();
		int height = game.height();

		if(queued == Orientation.NORTH) {
			newX = xCoord;
			newY = yCoord -1;
			orientation = queued;
		} else if(queued == Orientation.SOUTH) {
			newX = xCoord;
			newY = yCoord +1;
			orientation = queued;
		} else if(queued == Orientation.EAST) {
			newX = xCoord +1;
			newY = yCoord;
			orientation = queued;
		} else if(queued == Orientation.WEST) {
			newX = xCoord -1;
			newY = yCoord;
			orientation = queued;
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
