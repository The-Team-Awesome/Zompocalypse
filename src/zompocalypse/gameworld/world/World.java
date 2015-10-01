package zompocalypse.gameworld.world;

import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.Actor;
import zompocalypse.gameworld.characters.Player;

/**
 * The World class representing the world in which Zompocolypse takes place.
 *
 * @author Kieran Mckay, 300276166
 */
public class World implements Serializable {
	private final int width;
	private final int height;

	private static int id;

	/**
	 * The following is a map of ID's and characters in the game. This includes
	 * players, zombies and other misc things.
	 */
	private final Map<Integer,Actor> idToActor = new HashMap<Integer,Actor>();

	/**
	 * This represents the entire world as 2D array of Tiles. Tiles can either
	 * be standard floor Tiles, wall Tiles which block Players and door Tiles
	 * which can be moved through.
	 */

	private Orientation orientation;
	private Tile[][] map;
	private GameObject[][] objects;
	private Set<Point> playerSpawnPoints;
	private Set<Point> zombieSpawnPoints;

	public World(int width, int height, Tile[][] map, GameObject[][] objects, Set<Point> zombieSpawnPoints, Set<Point> playerSpawnPoints) {
		this.width = width;
		this.height = height;
		this.map = map;
		this.objects = objects;
		this.orientation = Orientation.NORTH;
		this.zombieSpawnPoints = zombieSpawnPoints;
		this.playerSpawnPoints = playerSpawnPoints;
	}

	/**
	 * The clock tick is essentially a clock trigger, which allows the world to
	 * update the current state. The frequency with which this is called
	 * determines the rate at which the game state is updated.
	 *
	 * @return
	 */
	public synchronized void clockTick() {
		for (Actor actor : idToActor.values()){
			actor.tick(this);
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
		GameObject obj = objects[x][y];
		if (obj != null && obj instanceof Wall){
			return true;
		}

		return false;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	private void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public Set<Point> getPlayerSpawnPoints() {
		return playerSpawnPoints;
	}

	public Set<Point> getZombieSpawnPoints() {
		return zombieSpawnPoints;
	}

	/**
	 * Gets the character based on the id - if it doesn't exist then
	 * something has gone wrong
	 * @param id The ID of the character
	 * @return The character itself
	 */
	public Actor getCharacterByID(int id) {
		if(idToActor.containsKey(id)){
			return idToActor.get(id);
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

	public Tile[][] getMap() {
		return map;
	}

	public synchronized int registerPlayer() {
		// A new player has been added! Create them and put them in the
		// map of actors here.
		Player player = new Player(1, 1, Orientation.SOUTH, ++id, 0, "Bibbly Bob", "file");
		idToActor.put(id, player);
		objects[1][1] = player;
		return id;
	}

	/**
	 * This method takes an x and y co-ordinate for a click and does shit with it.
	 *
	 * @param id
	 * @param x
	 * @param y
	 */
	public synchronized boolean processMouseClick(int id, int x, int y) {
		System.out.println(id + ", " + x + ":" + y);
		return true;
	}

	/**
	 *
	 * @param id
	 * @param key
	 */
	public synchronized boolean processCommand(int id, String key) {
		System.out.println(id + ", " + key);
		Player player = (Player) idToActor.get(id);

		switch (key) {
			case "North":
				player.moveNorth();
				return true;
			case "South":
				player.moveSouth();
				return true;
			case "East":
				player.moveEast();
				return true;
			case "West":
				player.moveWest();
				return true;
			case "ItemOne":
				return true;
			case "ItemTwo":
				return true;
			case "ItemThree":
				return true;
			case "Use":
				return true;
			case "RotateClockwise":
				return true;
			case "RotateAnticlockwise":
				return true;
			default:
				break;
		}
		return false;
	}

	// ***********************************************
	// End of Networking Methods
	// ***********************************************

	@Override
	public String toString() {
		return "World [width=" + width + ", height=" + height + ", charToID="
				+ idToActor + ", orientation=" + orientation + ", map="
				+ Arrays.toString(map) + ", objects="
				+ Arrays.toString(objects) + "]";
	}

	public GameObject[][] getObjects() {
		return objects;
	}
}
