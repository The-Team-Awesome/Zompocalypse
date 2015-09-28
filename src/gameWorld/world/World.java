package gameWorld.world;

import gameWorld.GameObject;
import gameWorld.Orientation;
import gameWorld.characters.Actor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The World class representing the world in which Zompocolypse takes place.
 *
 * @author Kieran Mckay, 300276166
 */
public class World {
	private final int width;
	private final int height;

	/**
	 * The following is a map of ID's and characters in the game. This includes
	 * players, zombies and other misc things.
	 */
	private final Map<Integer,Actor> charToID = new HashMap<>();

	/**
	 * This represents the entire world as 2D array of Tiles. Tiles can either
	 * be standard floor Tiles, wall Tiles which block Players and door Tiles
	 * which can be moved through.
	 */

	private Orientation orientation;
	private Tile[][] map;
	private GameObject[][] objects;

	public World(int width, int height, Tile[][] map) {
		this.width = width;
		this.height = height;
		this.map = map;
		this.orientation = Orientation.NORTH;
	}

	/**
	 * The clock tick is essentially a clock trigger, which allows the world to
	 * update the current state. The frequency with which this is called
	 * determines the rate at which the game state is updated.
	 *
	 * @return
	 */
	public synchronized void clockTick() {
		for (int i = 0; i < charToID.size(); i++){
			Actor c = charToID.get(i);
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

	public Orientation getOrientation() {
		return orientation;
	}

	private void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * Gets the character based on the id - if it doesn't exist then
	 * something has gone wrong
	 * @param id The ID of the character
	 * @return The character itself
	 */
	public Actor getCharacterByID(int id) {
		if(charToID.containsKey(id)){
			return charToID.get(id);
		}
		else {
			throw new IllegalStateException("Character with this code does not exist");
		}
	}

	// ***********************************************
	// Networking Methods
	// ******************
	// These methods are all used to convert World
	// data into a smaller format for sending
	// between the Client and Server or giving the
	// World information about Events to use.
	// ***********************************************

	/**
	 * Gets a Tile[][] which represents the area which a Character can currently
	 * see in their view.
	 *
	 * @param character - The Character object whose perspective is being requested
	 * @param size - The size of the perspective to return
	 * @return A 2D array of Tiles - edge cases are null objects
	 */
	public Tile[][] getCharacterPerspective(Actor character, int size) {
		Tile[][] perspective = new Tile[size][size];

		int offset = (size - 1) / 2;

		int charX = character.getX();
		int charY = character.getY();

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
		System.out.println(Arrays.toString(bytes));
	}

	/**
	 * This method converts the Tile map of this World into a byte array
	 * and returns it.
	 *
	 * @return
	 * @throws IOException
	 */
	public synchronized byte[] toByteArray() throws IOException {
		byte[] data = new byte[2];
		data[0] = 1;
		data[1] = 0;
		return data;
	}

	public Tile[][] getMap() {
		return map;
	}

	/**
	 * This method takes an x and y co-ordinate for a click and does shit with it.
	 *
	 * @param id
	 * @param x
	 * @param y
	 */
	public synchronized void processMouseClick(int id, int x, int y) {
		System.out.println(id + ", " + x + ":" + y);
	}

	/**
	 *
	 * @param id
	 * @param key
	 */
	public synchronized void processKeyPress(int id, String key) {
		System.out.println(id + ", " + key);
	}

	/**
	 *
	 * @param id
	 * @param command
	 */
	public synchronized void processAction(int id, String command) {
		System.out.println(id + ", " + command);
	}

	// ***********************************************
	// End of Networking Methods
	// ***********************************************
	
	@Override
	public String toString() {
		return "World [width=" + width + ", height=" + height + ", charToID="
				+ charToID + ", orientation=" + orientation + ", map="
				+ Arrays.toString(map) + ", objects="
				+ Arrays.toString(objects) + "]";
	}
}
