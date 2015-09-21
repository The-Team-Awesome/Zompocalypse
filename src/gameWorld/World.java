package gameWorld;

import java.util.ArrayList;

//Equivilant of Board in djp's pacman
public class World {
	private final int width;
	private final int height;

	/**
	 * The following is a list of the characters in the game. This includes
	 * players, zombies and other misc things.
	 */
	private final ArrayList<Character> characters = new ArrayList<Character>();

	public World(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * The clock tick is essentially a clock trigger, which allows the world to
	 * update the current state. The frequency with which this is called
	 * determines the rate at which the game state is updated.
	 *
	 * @return
	 */
	public synchronized void clockTick() {

		for (int i = 0; i < characters.size(); i++){
			Character c = characters.get(i);
			c.tick(this);
		}
	}

	/**
	 * Get the board width.
	 * @return
	 */
	public int width() {
		return width;
	}

	/**
	 * Get the board height.
	 * @return
	 */
	public int height() {
		return height;
	}

	public boolean isWall(int x, int y) {
		return false;
	}


}
