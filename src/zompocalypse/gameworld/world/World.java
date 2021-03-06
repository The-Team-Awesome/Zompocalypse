package zompocalypse.gameworld.world;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

import zompocalypse.datastorage.WorldBuilder;
import zompocalypse.gameworld.Direction;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.Actor;
import zompocalypse.gameworld.characters.HomerStrategy;
import zompocalypse.gameworld.characters.MovingCharacter;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.characters.RandomStrategy;
import zompocalypse.gameworld.characters.Strategy;
import zompocalypse.gameworld.characters.StrategyZombie;
import zompocalypse.gameworld.items.Container;
import zompocalypse.gameworld.items.Door;
import zompocalypse.gameworld.items.Item;
import zompocalypse.gameworld.items.Key;
import zompocalypse.gameworld.items.Money;
import zompocalypse.gameworld.items.Torch;
import zompocalypse.gameworld.items.Weapon;
import zompocalypse.ui.appwindow.UICommand;

/**
 * The World class representing the world in which Zompocalypse takes place.
 *
 * @author Kieran Mckay, 300276166
 */
public class World implements Serializable {
	public transient boolean godmode = false;

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;

	// increments every game tick
	public transient int tickTimer;

	private static int id;
	private Floor clipboardFloor;
	private Wall clipboardWall;

	/*
	 * The following is a map of ID's and characters in the game. This includes
	 * players, zombies and other misc things.
	 */
	private final Map<Integer, Actor> idToActor = new HashMap<Integer, Actor>();

	/*
	 * This represents the entire world as 2D array of Tiles. Tiles can either
	 * be standard floor Tiles, wall Tiles which block Players and door Tiles
	 * which can be moved through.
	 */
	private Orientation orientation;

	private String[] zombieFileNames = { "npc_zombie_n.png",
			"npc_zombie_e.png", "npc_zombie_s.png", "npc_zombie_w.png" };

	private String[] dragonFileNames = { "npc_dragon_n.png",
			"npc_dragon_e.png", "npc_dragon_s.png", "npc_dragon_w.png" };

	private Floor[][] map;
	private PriorityBlockingQueue<GameObject>[][] objects;
	private Set<Point> playerSpawnPoints;
	private Set<Point> zombieSpawnPoints;
	private boolean editMode = false;
	private boolean showWalls = true;
	private Point editor = new Point(0, 0);

	private Map<Point, GameObject> itemsToRemove = new HashMap<Point, GameObject>();

	public World(int width, int height, Floor[][] map,
			PriorityBlockingQueue<GameObject>[][] objects,
			Set<Point> zombieSpawnPoints, Set<Point> playerSpawnPoints, int id) {
		this.width = width;
		this.height = height;
		this.map = map;
		this.objects = objects;
		this.orientation = Orientation.NORTH;
		this.zombieSpawnPoints = zombieSpawnPoints;
		this.playerSpawnPoints = playerSpawnPoints;
		World.id = id;
	}

	/**
	 * The clock tick is essentially a clock trigger, which allows the world to
	 * update the current state. The frequency with which this is called
	 * determines the rate at which the game state is updated.
	 */
	public synchronized void clockTick() {
		List<MovingCharacter> dead = new ArrayList<MovingCharacter>();

		Iterator<Actor> actors = idToActor.values().iterator();
		Actor actor;
		while (actors.hasNext()) {
			actor = actors.next();
			if (!editMode) {
				if (actor instanceof MovingCharacter) {
					MovingCharacter character = (MovingCharacter) actor;
					if (character.isDead()) {
						// add to list to remove from game
						dead.add(character);

					} else {
						// non-dead moving characters tick here
						character.tick(this);

					}
				} else {
					// non moving characters tick here
					actor.tick(this);
				}
			}
		}

		// remove all dead moving characters
		for (MovingCharacter m : dead) {
			removeCharacter(m);
		}

		for (Point p : itemsToRemove.keySet()) {
			removeItem(p, itemsToRemove.get(p));
		}

		if (tickTimer % 10 == 0 && !editMode) {
			spawnZombie(new RandomStrategy());
		}
		if (tickTimer % 20 == 0 && !editMode) {
			spawnZombie(new HomerStrategy());
		}
		tickTimer++;
	}

	/**
	 * This puts an item on the map to remove objects from the world.
	 *
	 * @param point
	 * @param item
	 */
	public void addItemToRemove(Point point, GameObject item) {
		itemsToRemove.put(point, item);
	}

	/**
	 * Removes a MovingCharacter from the World.
	 *
	 * @param character
	 *            - MovingCharacter to be removed.
	 */
	private void removeCharacter(MovingCharacter character) {
		// handle removing zombies here
		if (character.isDead() && character instanceof StrategyZombie) {
			StrategyZombie zombie = (StrategyZombie) character;
			// remove character from map of active characters
			idToActor.remove(zombie.getUid(), zombie);
			// remove this character from the gameObjects array
			objects[zombie.getX()][zombie.getY()].remove(zombie);

			// loop though all players in game and give them score based on what
			// died
			for (Actor a : idToActor.values()) {
				if (a instanceof Player) {
					((Player) a).addScore(zombie.getPoints());
				}
			}
		}
	}

	/**
	 * Removes item from the World.
	 *
	 * @param p
	 *            - Point where the item is located.
	 * @param item
	 *            - item to be removed.
	 */
	private void removeItem(Point p, GameObject item) {
		objects[p.x][p.y].remove(item);
		itemsToRemove.remove(p);
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

	/**
	 * Checks if the position is blocked, not being available to be occupied.
	 *
	 * @param x
	 *            - position x desired.
	 * @param y
	 *            - position y desired.
	 * @return true - if it's blocked, false otherwise.
	 */
	public boolean isBlocked(int x, int y) {

		// everything off the map is treated as a wall
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return true;
		}
		PriorityBlockingQueue<GameObject> obj = objects[x][y];
		for (GameObject o : obj) {
			if (o != null)
				return !o.occupiable();
		}
		return false;
	}

	public Orientation getOrientation() {
		return orientation;
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

	public PriorityBlockingQueue<GameObject>[][] getObjects() {
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
	public synchronized int registerPlayer(String fileName) {
		// A new player has been added
		int x = width / 2, y = height / 2;

		for (Point p : playerSpawnPoints) {
			x = p.x;
			y = p.y;
		}
		// A new player has been added! Create them and put them in the
		// map of actors here.

		String[] filenames = { "character_" + fileName + "_empty_n.png",
				"character_" + fileName + "_empty_e.png",
				"character_" + fileName + "_empty_s.png",
				"character_" + fileName + "_empty_w.png" };

		// TODO: This should really get valid information for name,
		// as well as select their x, y co-ordinates based on a valid portal
		Player player = new Player(x, y, Orientation.NORTH, ++id, 0, fileName,
				filenames, this);
		idToActor.put(id, player);
		objects[player.getX()][player.getY()].add(player);

		return player.getUid();
	}

	/**
	 * This method creates a loaded player on the game and returns the id value
	 * which they were registered with. It is synchronized because it can be
	 * called in a networked game by multiple Client/Server connections.
	 *
	 * @return integer ID value
	 */
	public int registerLoadedPlayer(Player player) {
		// A new player has been added
		int x = width / 2, y = height / 2;

		for (Point p : playerSpawnPoints) {
			x = p.x;
			y = p.y;
		}

		player.setUID(++id);
		player.setWorld(this);
		player.setX(x);
		player.setY(y);

		idToActor.put(id, player);
		objects[player.getX()][player.getY()].add(player);

		return player.getUid();
	}

	/**
	 * Spawns a type of zombie according to the strategy given.
	 *
	 * @param strat
	 *            Strategy that was included
	 */
	public void spawnZombie(Strategy strat) {
		int x = 1;
		int y = 1;

		Random rand = new Random(System.currentTimeMillis());
		int choice = rand.nextInt(4);

		switch (choice) {
		case 0:
			x = 1;
			y = 1;
			break;
		case 1:
			x = width - 2;
			y = 1;
			break;
		case 2:
			x = width - 2;
			y = height - 2;
			break;
		case 3:
			x = 1;
			y = height - 2;
			break;
		}
		for (Point p : zombieSpawnPoints) {
			x = p.x;
			y = p.y;
		}

		StrategyZombie zombie = new StrategyZombie(this, x, y, strat, ++id,
				zombieFileNames);

		if (strat instanceof HomerStrategy) {
			zombie = new StrategyZombie(this, x, y, strat, ++id,
					dragonFileNames);
		}

		idToActor.put(id, zombie);
		objects[zombie.getX()][zombie.getY()].add(zombie);
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
	 * Processes the commands sent from the user input.
	 *
	 * @param id
	 *            - id of the current player.
	 * @param key
	 *            - command sent from the user.
	 */
	public synchronized void processCommand(int id, String key) {
		Player player = (Player) idToActor.get(id);

		if (player == null)
			return;

		if (key.equals(UICommand.NORTH.getValue())) {
			if (editMode) {
				editor.y--;
			} else {
				player.move(Orientation.NORTH, orientation);
			}
		} else if (key.equals(UICommand.SOUTH.getValue())) {
			if (editMode) {
				editor.y++;
			} else {
				player.move(Orientation.SOUTH, orientation);
			}
		} else if (key.equals(UICommand.EAST.getValue())) {
			if (editMode) {
				editor.x++;
			} else {
				player.move(Orientation.WEST, orientation);
			}
		} else if (key.equals(UICommand.WEST.getValue())) {
			if (editMode) {
				editor.x--;
			} else {
				player.move(Orientation.EAST, orientation);
			}
		} else if (key.equals(UICommand.USE.getValue())) {
			// The player uses something. This will be whatever
			// is in front of the player, so its uses are unlimited!
			player.use();
		} else if (key.contains(UICommand.USEITEM.getValue())) {
			useItem(player, key);
		} else if (key.contains(UICommand.TAKEITEM.getValue())) {
			takeItem(player, key);
		} else if (key.equals(UICommand.BACKPACKUSE.getValue())) {
			player.useQueued();
		} else if (key.equals(UICommand.BACKPACKDROP.getValue())) {
			player.dropQueued();
		} else if (key.equals(UICommand.CONTAINER.getValue())) {
			player.takeQueued();
		} else if (key.equals(UICommand.ROTATEANTICLOCKWISE.getValue())) {
			orientation = Orientation.getPrev(orientation);
		} else if (key.equals(UICommand.ROTATECLOCKWISE.getValue())) {
			orientation = Orientation.getNext(orientation);
		}
	}

	/**
	 * Performs an user using an item.
	 *
	 * @param player
	 *            - player using the item.
	 * @param key
	 *            - command from user.
	 */
	private void useItem(Player player, String key) {
		String trimmed = key.replace(UICommand.USEITEM.getValue(), "");
		int itemId = Integer.parseInt(trimmed);
		List<Item> inventory = player.getInventory();
		Item using = null;

		for (Item i : inventory) {
			if (i.getUniqueID() == itemId) {
				using = i;
			}
		}
		player.queueItem(using);
	}

	/**
	 * Performs player taking an item.
	 *
	 * @param player
	 *            - player taking an item.
	 * @param key
	 *            - command sent from the user.
	 */
	private void takeItem(Player player, String key) {
		String trimmed = key.replace(UICommand.TAKEITEM.getValue(), "");
		int itemId = Integer.parseInt(trimmed);

		PriorityBlockingQueue<GameObject> objects = player.getObjectsInfront();
		for (GameObject object : objects) {
			if (object instanceof Item) {
				if (object instanceof Container) {
					Container container = (Container) object;
					List<Item> items = container.getHeldItems();
					Iterator<Item> iterator = items.iterator();
					Item next;

					while (iterator.hasNext()) {
						next = iterator.next();
						if (next.getUniqueID() == itemId) {
							player.queueTake(next, container);
							return;
						}
					}
				}
			}
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

	/**
	 * Enables god mode!
	 */
	public void toggleGodmode() {
		godmode = !godmode;
	}

	public void setEditMode() {
		editMode = true;
		editor = new Point(0, 0);
		Queue<GameObject> thingStack = new ArrayDeque<GameObject>();
		for (int i = 0; i < objects.length; i++) {
			for (int j = 0; j < objects[0].length; j++) {
				while (!objects[i][j].isEmpty())
					thingStack.add(objects[i][j].poll());
				while (!thingStack.isEmpty()) {
					GameObject thing = thingStack.poll();
					if (!(thing instanceof Actor))
						objects[i][j].add(thing);
				}
			}
		}
	}

	public boolean getEditMode() {
		return editMode;
	}

	public Point getEditor() {
		return editor;
	}

	/**
	 * Changes the state of the walls in edit world mode.
	 */
	public void toggleWalls() {
		if (editMode)
			showWalls = !showWalls;
	}

	public boolean getShowWalls() {
		return showWalls;
	}

	/**
	 * Expands the map in edit world mode.
	 *
	 * @param direction
	 *            - direction to be expanded.
	 */
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

	/**
	 * Reduces the world map in edit mode.
	 *
	 * @param direction
	 */
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

	/**
	 * Toggles zombie spawn point into world edit mode.
	 */
	public void toggleZombieSpawnPoint() {
		if (zombieSpawnPoints.contains(editor))
			zombieSpawnPoints.remove(editor);
		else
			zombieSpawnPoints.add(new Point(editor.x, editor.y));
	}

	/**
	 * Toggles player spawn point into world edit mode.
	 */
	public void togglePlayerSpawnPoint() {
		if (playerSpawnPoints.contains(editor))
			playerSpawnPoints.remove(editor);
		else
			playerSpawnPoints.add(new Point(editor.x, editor.y));
	}

	/**
	 * Edits a tile into world edit mode.
	 */
	public void editTile() {
		String[] tileName = WorldBuilder.getFloorFileName();
		System.out.println(tileName[0]);
		map[editor.x][editor.y] = new Floor(editor.x, editor.y, tileName);

	}

	/**
	 * Edits a wall into world edit mode.
	 */
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

	/**
	 * This method adds objects into the game at the given position. It gives
	 * the user a selector for a range of different objects which they can add
	 * into the game.
	 */
	public void editObject() {
		while (!objects[editor.x][editor.y].isEmpty())
			objects[editor.x][editor.y].poll();

		String[] objectName = WorldBuilder.getObjectFileName();
		if (objectName == null)
			return;

		int offset = 0;
		if (objectName[0].contains("chest")) {

			String name = WorldBuilder.getString("Pliz give a name");
			String description = WorldBuilder
					.getString("Pliz do a description");
			int size = WorldBuilder.getInteger("Pliz put a size number");

			Container c = new Container(objectName, size, name, description,
					false, false, false, id++);

			int i = 0;
			while (i < size) {
				Item item = editItem(true);
				if (item != null) {
					System.out.println(item);
					c.add(item);
				}
				i++;
			}

			objects[editor.x][editor.y].add(c);
		} else if (objectName[0].contains("ground_grey")) {
			// This is a decorative object, but functions just the same as a
			// wall
			offset = 48;
			objects[editor.x][editor.y].add(new Wall(objectName, offset));
		} else if (objectName[0].contains("plant")) {
			// Plants function basically the same as a Wall, they just look
			// slightly different
			offset = 12;
			objects[editor.x][editor.y].add(new Wall(objectName, offset));
		} else if (objectName[0].contains("sword")) {
			// This is a Sword! They need a specific description and strength
			// rating,
			// to give the game different kinds of swords with different
			// qualities.
			String description = WorldBuilder
					.getString("Pliz do a description");
			int strength = WorldBuilder
					.getInteger("Pliz gimme a strength number");
			objects[editor.x][editor.y].add(new Weapon(objectName[0],
					description, id++, strength));
		} else if (objectName[0].contains("torch")) {
			// This is a torch, pretty torch gives the player light :)
			objects[editor.x][editor.y].add(new Torch(objectName[0], id++));
		} else if (objectName[0].contains("key")) {
			// This is a key! It is used to unlock doors.
			objects[editor.x][editor.y].add(new Key(objectName[0], id++));
		} else if (objectName[0].contains("coins")) {
			int amount = WorldBuilder.getInteger("Pliz gimme a amount number");
			objects[editor.x][editor.y].add(new Money(objectName[0], id++,
					amount));
		}

	}

	/**
	 * Edits the item, if using the Editor.
	 *
	 * @param inside
	 *            Whether it is inside that particular tile
	 * @return
	 */
	public Item editItem(boolean inside) {
		while (!objects[editor.x][editor.y].isEmpty())
			objects[editor.x][editor.y].poll();

		String itemName = WorldBuilder.getItemFileName();
		if (itemName == null)
			return null;

		if (itemName.contains("sword")) {
			// This is a Sword! They need a specific description and strength
			// rating,
			// to give the game different kinds of swords with different
			// qualities.
			String description = WorldBuilder
					.getString("Provide a description:");
			int strength = WorldBuilder.getInteger("Strength number:");

			Weapon w = new Weapon(itemName, description, id++, strength);

			if (inside) {
				return w;
			} else {
				objects[editor.x][editor.y].add(w);
			}
		} else if (itemName.contains("torch")) {
			// This is a torch, pretty torch gives the player light :)

			Torch t = new Torch(itemName, id++);

			if (inside) {
				return t;
			} else {
				objects[editor.x][editor.y].add(t);
			}

		} else if (itemName.contains("key")) {
			// This is a key! It is used to unlock doors.

			Key k = new Key(itemName, id++);

			if (inside) {
				return k;
			} else {
				objects[editor.x][editor.y].add(k);
			}
		} else if (itemName.contains("coins")) {
			int amount = WorldBuilder.getInteger("Amount number:");

			Money m = new Money(itemName, id++, amount);

			if (inside) {
				return m;
			} else {
				objects[editor.x][editor.y].add(m);
			}
		}

		return null;
	}

	/**
	 * Rotates the tile around.
	 */
	public void rotateTile() {
		map[editor.x][editor.y].rotate();
	}

	/**
	 * Copies the location of the selected item.
	 */
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

	/**
	 * Rotates an object into world edit mode.
	 */
	public void rotateObject() {
		GameObject x = objects[editor.x][editor.y].peek();
		if (x instanceof Wall) {
			((Wall) x).rotate();
		} else if (x instanceof Door) {
			((Door) x).rotate();
		} else if (x instanceof Container) {
			((Container) x).rotate();
		}
	}

	/**
	 * Rotates player perspective into world edit mode.
	 *
	 * @param id
	 * @param value
	 */
	public void rotatePlayerPerspective(int id, Direction value) {
		Player player = (Player) idToActor.get(id);
		player.rotatePerspective(value);
	}

	public Player getPlayer(int id) {
		return (Player) idToActor.get(id);
	}

	/**
	 * Returns the map to getting the actor IDs/ Actors.
	 *
	 * @return
	 */
	public Map<Integer, Actor> getIdToActor() {
		return idToActor;
	}

	/**
	 * Edits a door into world edit mode.
	 */
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

	/**
	 * Provides the open/close functionality.
	 */
	public void toggleDoor() {
		GameObject x = objects[editor.x][editor.y].peek();
		if (x instanceof Door) {
			((Door) x).use(null);
		}
	}

	public int getUID() {
		return ++id;
	}
}
