package gameWorld;

import java.io.IOException;
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

	/**
	 * This represents the entire world as 2D array of Tiles. Tiles can either
	 * be standard floor Tiles, wall Tiles which block Players and door Tiles
	 * which can be moved through.
	 */
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

	// ***********************************************
	// Networking Methods
	// ******************
	// These methods are all used to convert World
	// data into a smaller format for sending
	// between the Client and Server.
	// ***********************************************

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

	/**
	 * This method populates the Tile map of the World from the given byte array.
	 *
	 * @param bytes
	 * @throws IOException
	 */
	public synchronized void fromByteArray(byte[] bytes) throws IOException {

	}

	/**
	 * This method converts the Tile map of this World into a byte array
	 * and returns it.
	 *
	 * @return
	 * @throws IOException
	 */
	public synchronized byte[] toByteArray() throws IOException {

		return null;
	}

	public Tile[][] getMap() {
		return map;
	}


}
