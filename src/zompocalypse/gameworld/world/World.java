package zompocalypse.gameworld.world;

import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import zompocalypse.datastorage.*;
import zompocalypse.gameworld.Direction;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.Actor;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.items.Item;
import zompocalypse.ui.appwindow.UICommand;
import zompocalypse.ui.rendering.RenderPanel;

/**
 * The World class representing the world in which Zompocolypse takes place.
 *
 * @author Kieran Mckay, 300276166
 */
public class World implements Serializable {
	private int width;
	private int height;

	private static int id;
	private Floor clipboardFloor;
	private Wall clipboardWall;

	/**
	 * The following is a map of ID's and characters in the game. This includes
	 * players, zombies and other misc things.
	 */
	private final Map<Integer, Actor> idToActor = new HashMap<Integer, Actor>();

	/**
	 * This represents the entire world as 2D array of Tiles. Tiles can either
	 * be standard floor Tiles, wall Tiles which block Players and door Tiles
	 * which can be moved through.
	 */

	private Orientation orientation;
	private Floor[][] map;
	private PriorityQueue<GameObject>[][] objects;
	private Set<Point> playerSpawnPoints;
	private Set<Point> zombieSpawnPoints;
	private boolean editMode = false;
	private boolean showWalls = true;
	private Point editor = new Point(0, 0);

	public World(int width, int height, Floor[][] map,
			PriorityQueue<GameObject>[][] objects,
			Set<Point> zombieSpawnPoints, Set<Point> playerSpawnPoints, int id) {
		this.width = width;
		this.height = height;
		this.map = map;
		this.objects = objects;
		this.orientation = Orientation.NORTH;
		this.zombieSpawnPoints = zombieSpawnPoints;
		this.playerSpawnPoints = playerSpawnPoints;
		this.id = id;
	}

	/**
	 * The clock tick is essentially a clock trigger, which allows the world to
	 * update the current state. The frequency with which this is called
	 * determines the rate at which the game state is updated.
	 *
	 * @return
	 */
	public synchronized void clockTick() {
		for (Actor actor : idToActor.values()) {
			actor.tick(this);
		}
	}

	/**
	 * Get the board width.
	 *
	 * @return
	 */
	public int width() {
		return width;
	}

	/**
	 * Get the board height.
	 *
	 * @return
	 */
	public int height() {
		return height;
	}

	public boolean isBlocked(int x, int y) {

		// everything off the map is treated as a wall
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return true;
		}
		PriorityQueue<GameObject> obj = objects[x][y];
		for (GameObject o : obj) {
			if (o != null && o instanceof Wall)
				return true;
			else if (o instanceof Wall)
				return true;
			else if (o instanceof Door)
				return !((Door)o).occupiable();

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
	 * Gets the character based on the id - if it doesn't exist then something
	 * has gone wrong
	 *
	 * @param id
	 *            The ID of the character
	 * @return The character itself
	 */
	public Actor getCharacterByID(int id) {
		if (idToActor.containsKey(id)) {
			return idToActor.get(id);
		} else {
			throw new IllegalStateException(
					"Character with this code does not exist");
		}
	}

	public Floor[][] getMap() {
		return map;
	}

	public PriorityQueue<GameObject>[][] getObjects() {
		return objects;
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
	 * @param character
	 *            - The Character object whose perspective is being requested
	 * @param size
	 *            - The size of the perspective to return
	 * @return A 2D array of Tiles - edge cases are null objects
	 */
	public Floor[][] getCharacterPerspective(Actor character, int size) {
		Floor[][] perspective = new Floor[size][size];

		int offset = (size - 1) / 2;

		int charX = character.getX();
		int charY = character.getY();

		int perspX = 0;
		for (int x = charX - offset; x <= charX + offset; x++) {
			int perspY = 0;

			for (int y = charY - offset; y <= charY + offset; y++) {
				if (x >= 0 || x < height || y >= 0 || y < height) {
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
	 * This method creates a new player on the game and returns the id value
	 * which they were registered with. It is synchronized because it can be
	 * called in a networked game by multiple Client/Server connections.
	 *
	 * @return integer ID value
	 */
	public synchronized int registerPlayer() {
		// A new player has been added
		int x, y;

		for (Point p : playerSpawnPoints) {
			x = p.x;
			y = p.y;
		}
		// A new player has been added! Create them and put them in the
		// map of actors here.

		String[] filenames = { "character_gina_empty_n.png",
				"character_gina_empty_e.png", "character_gina_empty_s.png",
				"character_gina_empty_w.png" };

		// TODO: This should really get valid information for name,
		// as well as select their x, y co-ordinates based on a valid portal
		Player player = new Player(1, 1, Orientation.NORTH, ++id, 0,
				"Bibbly Bob", filenames, this);
		idToActor.put(id, player);
		objects[player.getX()][player.getY()].add(player);
		return player.getUID();
	}

	/**
	 * This method takes the id of a player and removes them from the game.
	 *
	 * @param id
	 */
	public synchronized void disconnectPlayer(int id) {

		Player player = (Player) idToActor.get(id);

		objects[player.getX()][player.getY()].remove(player);

		idToActor.remove(id);
	}

	/**
	 * This method takes an x and y co-ordinate for a click and does shit with
	 * it.
	 *
	 * @param id
	 * @param x
	 * @param y
	 */
	public synchronized boolean processMouseClick(int id, int x, int y) {
		// System.out.println(id + ", " + x + ":" + y);
		return true;
	}

	/**
	 *
	 * @param id
	 * @param key
	 */
	public synchronized boolean processCommand(int id, String key) {
		// System.out.println(id + ", " + key);
		Player player = (Player) idToActor.get(id);

		/**
		 * TODO: From Sam. This breaks the networked element of the game. The
		 * reason it breaks is because the world and the renderer are supposed
		 * to be decoupled from each other, since the world is persistent across
		 * all clients but each client has a unique renderer and view of the
		 * world. A better solution to this problem would be to store the
		 * orientation on each unique player and get it from them. Until this is
		 * solved, my beautiful networking won't be able to work :(
		 */
		Orientation cameraDirection = player.getCurrentOrientation();

		// Remember that key is a String, so call .equals() instead of ==

		if (key.equals(UICommand.NORTH.getValue())) {
			if (editMode)
				editor.y--;
			else
				player.move(Orientation.NORTH, cameraDirection);
			return true;
		} else if (key.equals(UICommand.SOUTH.getValue())) {
			if (editMode)
				editor.y++;
			else
				player.move(Orientation.SOUTH, cameraDirection);
			return true;
		} else if (key.equals(UICommand.EAST.getValue())) {
			if (editMode)
				editor.x++;
			else
				player.move(Orientation.WEST, cameraDirection); // TODO This is
																// wrong! Should
																// be 'east' not
																// 'west'??? But
																// it works :(
			return true;
		} else if (key.equals(UICommand.WEST.getValue())) {
			if (editMode)
				editor.x--;
			else
				player.move(Orientation.EAST, cameraDirection); // TODO This is
																// also wrong!
			return true;
		} else if (key.equals(UICommand.ITEMONE.getValue())) {
			return true;
		} else if (key.equals(UICommand.ITEMTWO.getValue())) {
			return true;
		} else if (key.equals(UICommand.ITEMTHREE.getValue())) {
			return true;
		} else if (key.equals(UICommand.USE.getValue())) {
			for (GameObject o : player.getObjectsHere()) {
				if (o instanceof Item) {
					((Item) o).use(player);
					return true;
				}
			}
			for (GameObject o : player.getObjectsInfront()) {
				if (o instanceof Item) {
					((Item) o).use(player);
					return true;
				}
			}
			return false;
		} else if (key.equals(UICommand.ROTATEANTICLOCKWISE.getValue())) {
			this.rotatePlayerPerspective(id, Direction.ANTICLOCKWISE);
			return true;
		}

		else if (key.equals(UICommand.ROTATECLOCKWISE.getValue())) {
			this.rotatePlayerPerspective(id, Direction.CLOCKWISE);
			return true;
		} else {
			return false;
		}
	}

	// ***********************************************
	// End of Networking Methods
	// ***********************************************

	/*
	 * @Override public String toString() { return "World [width=" + width +
	 * ", height=" + height + ", charToID=" + idToActor + ", orientation=" +
	 * orientation + ", map=" + Arrays.toString(map) + ", objects=" +
	 * Arrays.toString(objects) + "]"; }
	 */

	// ***********************************************
	// Everything below here is used for editing mode
	// ***********************************************

	public void setEditMode() {
		editMode = true;
		for (int k : idToActor.keySet()) {
			Actor a = idToActor.get(k);
			objects[a.getX()][a.getY()].clear();
		}
		editor = new Point(0, 0);
	}

	public boolean getEditMode() {
		return editMode;
	}

	public Point getEditor() {
		return editor;
	}

	public void toggleWalls() {
		if (editMode)
			showWalls = !showWalls;
	}

	public boolean getShowWalls() {
		return showWalls;
	}

	public void expandMap(String direction) {
		if (editMode) {
			switch (direction) {
			case "north":
				editor.y++;
				for (Point p : playerSpawnPoints)
					p.y++;
				for (Point p : zombieSpawnPoints)
					p.y++;
			case "south":
				height++;
				break;
			case "west":
				editor.x++;
				for (Point p : playerSpawnPoints)
					p.x++;
				for (Point p : zombieSpawnPoints)
					p.x++;
			case "east":
				width++;
				break;
			}
			System.out.println("New width: " + width + ", new height: "
					+ height);
			map = WorldBuilder.expandMap(map, direction);
			objects = WorldBuilder.expandObjects(objects, direction);
		}
	}

	public void shrinkMap(String direction) {
		if (editMode) {
			switch (direction) {
			case "north":
				editor.y--;
				for (Point p : playerSpawnPoints)
					p.y--;
				for (Point p : zombieSpawnPoints)
					p.y--;
			case "south":
				height--;
				break;
			case "west":
				editor.x--;
				for (Point p : playerSpawnPoints)
					p.x--;
				for (Point p : zombieSpawnPoints)
					p.x--;
			case "east":
				width--;
				break;
			}
		}
		System.out.println("New width: " + width + ", new height: " + height);
		map = WorldBuilder.shrinkMap(map, direction);
		objects = WorldBuilder.shrinkObjects(objects, direction);
	}

	public void toggleZombieSpawnPoint() {
		if (zombieSpawnPoints.contains(editor))
			zombieSpawnPoints.remove(editor);
		else
			zombieSpawnPoints.add(new Point(editor.x, editor.y));
	}

	public void togglePlayerSpawnPoint() {
		if (playerSpawnPoints.contains(editor))
			playerSpawnPoints.remove(editor);
		else
			playerSpawnPoints.add(new Point(editor.x, editor.y));
	}

	public void editTile() {
		String[] tileName = WorldBuilder.getFloorFileName();
		System.out.println(tileName[0]);
		map[editor.x][editor.y] = new Floor(editor.x, editor.y, tileName);

	}

	public void editWall() {
		while (!objects[editor.x][editor.y].isEmpty())
			objects[editor.x][editor.y].poll();
		String[] wallName = WorldBuilder.getWallFileName();
		if (wallName == null)
			return;
		int offset = 55;
		if (wallName[0].contains("brown_1") || wallName[0].contains("grey_2")
				|| wallName[0].contains("grey_3"))
			offset = 48;
		if (wallName != null)
			objects[editor.x][editor.y].add(new Wall(wallName, offset));
	}

	public void editObject() {
		// TODO Auto-generated method stub

	}

	public void rotateTile() {
		map[editor.x][editor.y].rotate();
	}

	public void copyLocation() {
		clipboardFloor = map[editor.x][editor.y];
		clipboardWall = ((Wall) objects[editor.x][editor.y].peek());
	}

	public void pasteLocation() {
		map[editor.x][editor.y] = clipboardFloor.cloneMe();
		if (!objects[editor.x][editor.y].isEmpty())
			objects[editor.x][editor.y].poll();
		if (clipboardWall != null)
			objects[editor.x][editor.y].add(clipboardWall.cloneMe());
	}

	public void rotateObject() {
		GameObject x = objects[editor.x][editor.y].peek();
		if (x instanceof Wall) {
			((Wall) x).rotate();
		} else if (x instanceof Door) {
			((Door) x).rotate();
		}
	}

	public void rotatePlayerPerspective(int id, Direction value) {
		Player player = (Player) idToActor.get(id);
		player.rotatePerspective(value);
	}

	public Player getPlayer(int id) {
		return (Player) idToActor.get(id);
	}

	public void editDoor() {
		while (!objects[editor.x][editor.y].isEmpty())
			objects[editor.x][editor.y].poll();
		String[] doorName = WorldBuilder.getDoorFileName();
		if (doorName == null)
			return;
		int offset = 55;
		if (doorName[0].contains("brown_1") || doorName[0].contains("grey_2")
				|| doorName[0].contains("grey_3"))
			offset = 48;
		if (doorName != null)
			objects[editor.x][editor.y].add(new Door(editor.x, editor.y,
					doorName, offset, false, id++));
	}

	public void toggleDoor() {
		GameObject x = objects[editor.x][editor.y].peek();
		if (x instanceof Door) {
			((Door) x).use(null);
		}
	}
}
