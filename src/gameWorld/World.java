package gameWorld;

import java.util.ArrayList;
import java.util.List;

//Equivilant of Board in djp's pacman
public class World {
	private final int width;
	private final int height;

	/**
	 * The following is a list of the characters in the game. This includes
	 * players, zombies and other misc things.
	 */
	private final ArrayList<Character> characters = new ArrayList<Character>();

	private Tile[][] map;

	public World(int width, int height, Tile[][] map) {
		this.width = width;
		this.height = height;
		this.map = map;
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


	/**
	 * Gets a Tile[][] which represents the area which a Character can currently
	 * see in their view.
	 *
	 * @param character - The Character object whose perspective is being requested
	 * @param size - The size of the perspective to return
	 * @return A 2D array of Tiles - edge cases are null objects
	 */
	public Tile[][] getCharacterPerspective(Character character, int size) {
		Tile[][] perspective = new Tile[size][size];

		int offset = (size - 1) / 2;

		int charX = character.realX;
		int charY = character.realY;

		int perspX = 0;
		for(int x = charX - offset; x <= charX + offset; x++) {
			int perspY = 0;

			for(int y = charY - offset; y <= charY + offset; y++) {
				if(x >=0 || x < height || y >= 0 || y < height) {
					perspective[perspX][perspY] = map[x][y];
				} else {
					// Careful!
					perspective[perspX][perspY] = null;
				}
				perspY++;
			}

			perspX++;
		}

		return perspective;
	}


}
