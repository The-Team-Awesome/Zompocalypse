package zompocalypse.ui.rendering;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.Direction;
import zompocalypse.gameworld.Drawable;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.MovingCharacter;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.world.Floor;
import zompocalypse.gameworld.world.World;

/**
 * Provides a 3D view of the world, with locations that are presented from a
 * side on perspective.
 *
 * @author Pauline Kelly
 *
 */
public class RenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int TILE_WIDTH = 64;
	private static final int FLOOR_TILE_HEIGHT = 42;

	private static int maxMovesReset; // player will move this many tiles from
										// centre before centre is reset
	private static int drawDistance; // the number of tiles that appear outwards
										// from the player
	private int centerX = 0;
	private int centerY = 0;
	private int focusX;
	private int focusY; // The coordinates of the player
	private Orientation ori; // the current orientation of the view

	private final int id; // The Player's ID
	private World game;
	private Floor blankTile; // If the tile is off the grid, then a blank tile
								// is drawn. Could be invisible if expanded to
								// include background.

	private ImageIcon blood;

	/**
	 * Constructor. Takes the height and width of the canvas into account.
	 *
	 * @param wd
	 *            Width of window
	 * @param ht
	 *            Height of window
	 */
	public RenderPanel(int id, World game) {
		this.game = game;
		this.ori = game.getOrientation();
		this.id = id;

		centerX = game.getPlayer(id).getX();
		centerY = game.getPlayer(id).getY();
		maxMovesReset = 4;
		drawDistance = 7;

		String[] blank = { "blank_tile.png" };
		blankTile = new Floor(0, 0, blank);

		this.blood = Loader.LoadSpriteIcon("bloodsplatter.png");

	}

	/**
	 * Updates the world with its new state. //TODO: is this necessary
	 *
	 * @param game
	 */
	public void updateGame(World game) {
		this.game = game;
	}

	/**
	 * Rotates the rendering in the given direction. Updates the current
	 * orientation in the rendering panel
	 *
	 * @param dir
	 *            The direction of the rotation to be updated
	 */
	public void rotate(Direction dir) {
		switch (dir) {

		case CLOCKWISE:
			updateViewClockwise();
			return;
		case ANTICLOCKWISE:
			updateViewAntiClockwise();
			return;
		default:
			throw new IllegalArgumentException(
					"Direction wasn't clockwise or anticlockwise");
		}
	}

	/**
	 * Updates the current orientation of the viewer to its clockwise
	 * counterpart.
	 */
	private void updateViewClockwise() {
		ori = Orientation.getNext(ori);
	}

	/**
	 * Updates the current orientation of the viewer to its anticlockwise
	 * counterpart.
	 */
	private void updateViewAntiClockwise() {
		ori = Orientation.getPrev(ori);
	}

	/**
	 * Draws the background first, then draws the tiles and players. Knowledge
	 * taken from
	 * http://gamedev.stackexchange.com/questions/25982/how-do-i-determine
	 * -the-draw-order-in-an-isometric-view-flash-game
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		boolean editMode = game.getEditMode(); // Whether you are creating the
												// world or not
		boolean showWalls = game.getShowWalls(); // whether the walls are
													// showing(for placing items
													// in edit mode)

		Floor[][] tiles;
		PriorityBlockingQueue<GameObject>[][] objects; // 2D array of
														// PriorityBlockingQueues
														// for arrangement of
														// items for displaying

		tiles = game.getMap();
		objects = game.getObjects();

		setFocus(editMode);

		// convert these to the players coordinates
		int offsetX = getWidth() / 2;
		int offsetY = getHeight() / 2 - drawDistance * FLOOR_TILE_HEIGHT;

		Floor[][] tempFloor = tiles;
		PriorityBlockingQueue<GameObject>[][] tempObjects = objects;

		int doubleDrawDist = drawDistance * 2;
		tempFloor = clipFloor(tiles, drawDistance, focusX, focusY,
				doubleDrawDist);
		tempObjects = clipObjects(objects, drawDistance, focusX, focusY,
				doubleDrawDist);

		tempObjects = rotateObjects(tempObjects);
		tempFloor = rotateFloor(tempFloor);
		paintGameScreen(g, showWalls, offsetX, offsetY, tempFloor, tempObjects);

		editOptions(offsetX, offsetY + drawDistance * FLOOR_TILE_HEIGHT,
				editMode, g);
	}

	/**
	 * Iterates through the Floor tiles and GameObjects, and draws them.
	 *
	 * @param g
	 *            The Graphics object to draw things with
	 * @param showWalls
	 *            Whether the walls are showing for editing purposes
	 * @param offsetX
	 *            The X offset of the screen values
	 * @param offsetY
	 *            The Y offset of the screen values
	 * @param tempFloor
	 *            The Floor in the game
	 * @param tempObjects
	 *            The Objects in the game
	 */
	private void paintGameScreen(Graphics g, boolean showWalls, int offsetX,
			int offsetY, Floor[][] tempFloor,
			PriorityBlockingQueue<GameObject>[][] tempObjects) {
		int x;
		int y;
		for (int i = 0; i < tempFloor.length; ++i) {
			for (int j = 0; j < tempFloor[0].length; j++) {

				int[] coords = convertFromGameToScreen(i, j);
				x = coords[0] + offsetX;
				y = coords[1] + offsetY;

				drawFloor(tempFloor, i, j, x, y, g);
				drawObjects(g, showWalls, tempObjects, x, y, i, j);
			}
		}
	}

	/**
	 * Draws the objects in the game.
	 *
	 * @param g
	 * @param showWalls
	 * @param tempObjects
	 * @param x
	 * @param y
	 * @param i
	 * @param j
	 */
	private void drawObjects(Graphics g, boolean showWalls,
			PriorityBlockingQueue<GameObject>[][] tempObjects, int x, int y,
			int i, int j) {
		if (!showWalls) {
			return;
		}

		for (Drawable dd : tempObjects[i][j]) {
			if (dd == null) {
				continue;
			}
			//do not draw dead characters
			if (dd instanceof Player){				
				if (((Player) dd).isDead()){
					continue;
				}
			}
			dd.draw(x, y, g, ori);

			if (!(dd instanceof MovingCharacter)) { // If they are not a moving
													// character, nothing else
													// to check for
				continue;
			} else {
				MovingCharacter ch = (MovingCharacter) dd; // Otherwise, check
															// to see if they
															// were damaged and
															// draw it
				if (ch.tookDamage()) {
					drawDamage(x, y, g);
					ch.resetDamage();
				}
			}
		}
	}

	/**
	 *
	 * @param x
	 * @param y
	 * @param g
	 */
	private void drawDamage(int x, int y, Graphics g) {
		g.drawImage(blood.getImage(), x, y + 10, null);
	}

	/**
	 * Draws the flooring
	 *
	 * @param tempFloor
	 * @param i
	 * @param j
	 * @param x
	 * @param y
	 * @param g
	 */
	private void drawFloor(Floor[][] tempFloor, int i, int j, int x, int y,
			Graphics g) {
		if (tempFloor[i][j] instanceof Drawable) {
			Drawable d = tempFloor[i][j];
			d.draw(x, y, g, ori);

		}
	}

	/**
	 * Rotates the floor according to the current orientation
	 *
	 * @param tempFloor
	 * @return The rotated 2D array of Floor tiles
	 */
	private Floor[][] rotateFloor(Floor[][] tempFloor) {
		switch (ori) {
		case NORTH:
			break;
		case EAST:
			tempFloor = rotateFloor90(tempFloor);
			break;
		case SOUTH:
			tempFloor = rotateFloor90(tempFloor);
			tempFloor = rotateFloor90(tempFloor);
			break;
		case WEST:
			tempFloor = rotateFloor90(tempFloor);
			tempFloor = rotateFloor90(tempFloor);
			tempFloor = rotateFloor90(tempFloor);
			break;
		}
		return tempFloor;
	}

	/**
	 * Rotates the floor according to the current orientation
	 *
	 * @param tempObjects
	 * @return The rotated 2D array of the Priority Queues with Gameobjects in
	 *         them
	 */
	private PriorityBlockingQueue<GameObject>[][] rotateObjects(
			PriorityBlockingQueue<GameObject>[][] tempObjects) {
		switch (ori) {
		case NORTH:
			break;
		case EAST:
			tempObjects = rotateObjects90(tempObjects);
			break;
		case SOUTH:
			tempObjects = rotateObjects90(tempObjects);
			tempObjects = rotateObjects90(tempObjects);
			break;
		case WEST:
			tempObjects = rotateObjects90(tempObjects);
			tempObjects = rotateObjects90(tempObjects);
			tempObjects = rotateObjects90(tempObjects);
			break;
		}
		return tempObjects;
	}

	/**
	 * Centers the map if the player/editor has moved too far away from the
	 * screen centre.
	 *
	 * @param editMode
	 *            Whether it is in edit mode or not
	 */
	private void setFocus(boolean editMode) {
		// get the board coordinates of the player
		if (!editMode) {
			Player c = (Player) game.getCharacterByID(id);
			centerX = c.getX();
			centerY = c.getY();
			if (c.hasTorch()) {
				maxMovesReset = 5;
				drawDistance = 12;
			} else {
				maxMovesReset = 3;
				drawDistance = 6;
			}
			if (centerX - focusX > maxMovesReset
					|| focusX - centerX > maxMovesReset
					|| centerY - focusY > maxMovesReset
					|| focusY - centerY > maxMovesReset) {
				focusX = centerX;
				focusY = centerY;
			}
		} else {
			Point p = game.getEditor();
			focusX = p.x;
			focusY = p.y;
		}
	}

	/**
	 * Rotates the given 2D array by 90 degrees
	 *
	 * @param tempObjects
	 * @return The objects in their new locations
	 */
	private PriorityBlockingQueue<GameObject>[][] rotateObjects90(
			PriorityBlockingQueue<GameObject>[][] tempObjects) {
		int size = tempObjects.length;
		PriorityBlockingQueue<GameObject>[][] temp = new PriorityBlockingQueue[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				temp[x][y] = tempObjects[size - y - 1][x];
			}
		}
		return temp;
	}

	/**
	 * Rotates the given 2D array by 90 degrees
	 *
	 * @param tempFloor
	 * @return The objects in their new locations
	 */
	private Floor[][] rotateFloor90(Floor[][] tempFloor) {
		int size = tempFloor.length;
		Floor[][] temp = new Floor[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				temp[x][y] = tempFloor[size - y - 1][x];
			}
		}
		return temp;
	}

	/**
	 * Returns the selection of GameObjects around the centre of the focus at
	 * the size of the draw distance
	 *
	 * @param objects
	 *            The 2D objects array that requires clipping
	 * @param drawDistance
	 *            The number of tiles from the player that will be displayed
	 * @param focusX
	 *            The X position of the editor or player
	 * @param focusY
	 *            The Y position of the editor or player
	 * @return
	 */
	private PriorityBlockingQueue<GameObject>[][] clipObjects(
			PriorityBlockingQueue<GameObject>[][] objects, int drawDistance,
			int focusX, int focusY, int doubleDrawDist) {
		PriorityBlockingQueue<GameObject>[][] temp = new PriorityBlockingQueue[doubleDrawDist + 1][doubleDrawDist + 1];

		for (int i = 0; i <= doubleDrawDist; i++) {
			for (int j = 0; j <= doubleDrawDist; j++) {
				if (i + focusX - drawDistance < 0
						|| j + focusY - drawDistance < 0
						|| i + focusX - objects.length - drawDistance >= 0
						|| j + focusY - objects[0].length - drawDistance >= 0)
					temp[i][j] = new PriorityBlockingQueue<GameObject>();
				else
					temp[i][j] = objects[i + focusX - drawDistance][j + focusY
							- drawDistance];
			}
		}
		return temp;
	}

	/**
	 * Returns the selection of Floor tiles around the centre of the focus at
	 * the size of the draw distance
	 *
	 * @param objects
	 *            The 2D objects array that requires clipping
	 * @param drawDistance
	 *            The number of tiles from the player that will be displayed
	 * @param focusX
	 *            The X position of the editor or player
	 * @param focusY
	 *            The Y position of the editor or player
	 * @return
	 */
	private Floor[][] clipFloor(Floor[][] tiles, int drawDistance, int focusX,
			int focusY, int doubleDrawDist) {

		Floor[][] temp = new Floor[doubleDrawDist + 1][doubleDrawDist + 1];
		for (int i = 0; i <= doubleDrawDist; i++) {
			for (int j = 0; j <= doubleDrawDist; j++) {
				if (i + focusX - drawDistance < 0
						|| j + focusY - drawDistance < 0
						|| i + focusX - tiles.length - drawDistance >= 0
						|| j + focusY - tiles[0].length - drawDistance >= 0)
					temp[i][j] = blankTile;
				else
					temp[i][j] = tiles[i + focusX - drawDistance][j + focusY
							- drawDistance];
			}
		}
		return temp;
	}

	/**
	 * Options for using the editor
	 *
	 * @param offsetX
	 * @param offsetY
	 * @param editMode
	 * @param g
	 */
	private void editOptions(int offsetX, int offsetY, boolean editMode,
			Graphics g) {
		if (editMode) {
			Set<Point> playerSpawnPoints = game.getPlayerSpawnPoints();
			Set<Point> zombieSpawnPoints = game.getZombieSpawnPoints();
			Point editor = game.getEditor();
			g.setColor(Color.GREEN);
			for (Point p : playerSpawnPoints) {
				int[] q = convertFromGameToScreen(p.x - editor.x, p.y
						- editor.y);
				g.drawOval(q[0] + offsetX + 30, q[1] + offsetY + 15, 5, 5);
			}
			g.setColor(Color.RED);
			for (Point p : zombieSpawnPoints) {
				int[] q = convertFromGameToScreen(p.x - editor.x, p.y
						- editor.y);
				g.drawOval(q[0] + offsetX + 29, q[1] + offsetY + 14, 7, 7);
			}
			g.setColor(Color.BLUE);
			g.drawOval(428, 313, 9, 9);
		}
	}

	/**
	 * Returns the screen coordinates, translated using an isometric formula
	 * from the game coordinates.
	 *
	 * @param i
	 *            Gamess x value
	 * @param j
	 *            Games y value
	 * @return The calculated X/Y values
	 */
	private int[] convertFromGameToScreen(int i, int j) {
		int x = (i * TILE_WIDTH / 2) - (j * TILE_WIDTH / 2);
		int y = (j * FLOOR_TILE_HEIGHT / 2) + (i * FLOOR_TILE_HEIGHT / 2);

		return new int[] { x, y };
	}

	/**
	 * Get the current orientation for this view - critical for rotating the
	 * board.
	 *
	 * @return The current orientation
	 */
	public Orientation getCurrentOrientation() {
		return ori;
	}
}
