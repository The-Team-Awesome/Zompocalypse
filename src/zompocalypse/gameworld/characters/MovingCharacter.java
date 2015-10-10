package zompocalypse.gameworld.characters;

import java.util.PriorityQueue;

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
	protected Orientation ori;
	protected boolean moving;
	protected Orientation queued; 	// queued direction change
	protected World game;

	public MovingCharacter(World game, int xCoord, int yCoord, Orientation direction) {
		super(xCoord,yCoord);
		this.game = game;
		this.ori = direction;
		this.moving = false;
		queued = Orientation.NORTH;
	}

	/**
	 * Determine the direction in which this character is moving.
	 */
	public Orientation direction() {
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
		} else if(queued == Orientation.SOUTH) {
			newX = xCoord;
			newY = yCoord +1;
			ori = queued;
		} else if(queued == Orientation.EAST) {
			newX = xCoord + 1;
			newY = yCoord;
			ori = queued;
		} else if(queued == Orientation.WEST) {
			newX = xCoord - 1;
			newY = yCoord;
			ori = queued;
		} else {
			return;
		}



		if(newX < 0 || newY < 0 || newX >= width || newY >= height) {
			return;
		}

		if(!game.isBlocked(newX,newY)) {
			// we can update our position ...
			PriorityQueue<GameObject> objects[][] = game.getObjects();

			//System.out.println(objects);

			/*for(int x = 0; x < objects.length; x++) {
				for(int y = 0; y < objects[0].length; y++) {
					System.out.print(objects[x][y] + ", ");
				}
				System.out.print("\n");
			}*/

			xCoord = newX;
			yCoord = newY;
			objects[oldX][oldY].remove(this);
			objects[newX][newY].add(this);
		}
	}

	/**
	 * Determine the speed at which this character moves
	 */
	abstract public int speed();

	public void setOrientation(Orientation orientation) {
		this.ori = orientation;
	}

}
