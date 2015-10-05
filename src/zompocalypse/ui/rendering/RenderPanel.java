package zompocalypse.ui.rendering;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Set;

import javax.swing.JPanel;

import zompocalypse.gameworld.*;
import zompocalypse.gameworld.characters.*;
import zompocalypse.gameworld.world.*;

/**
 * Provides a 3D view of the world, with locations that are presented from a
 * side on perspective.
 *
 * @author Pauline Kelly
 *
 */
public class RenderPanel extends JPanel {

	/*
	 * Application Canvas is a field - need one in the constructor for the
	 * Renderer.
	 *
	 * Need the location passed in - make a new renderer per location or use
	 * setLocation()?
	 *
	 * Need the 4 orientations - NSEW. A location that is entered from the north
	 * is shown with the view facing south.
	 *
	 * Locations in the distance are shown in the background.
	 */

	private static final long serialVersionUID = 1L;
	// For scaling, knowing how big the window is
	// private final int WIDTH;
	// private final int HEIGHT;

	private World game;
	private final int id;

	private static final int TILE_WIDTH = 64;
	// private static final int FLOOR_TILE_HEIGHT = 42; //NOT 32? Height changes
	// for each tile
	private static final int FLOOR_TILE_HEIGHT = 42; // NOT 32? Height changes
														// for each tile

	private boolean testing = false;

	private Orientation currentOrientation = Orientation.NORTH;

	// Remember the tiles and objects for next time you render - in case the
	// orientation hasn't changed.
	// Will need to update each tme.
	private Floor[][] tiles;
	private GameObject[][] objects;

	// The panel to be rendered on

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
		this.id = id;
		// WIDTH = this.getWidth();
		// HEIGHT = this.getHeight();
	}

	/**
	 * Use the player id and the game objects to get the clipping from the
	 * player.
	 *
	 * The clipping is a smaller version of the complete game board that will be
	 * displayed on the screen.
	 */
	// public Floor[][] clip() {
	//
	//
	//
	// // find the position to render the character at
	// int renderActorX = WIDTH / 2;
	// int renderActorY = HEIGHT / 2;
	//
	// int xTilesPerPanel = WIDTH / TILE_WIDTH; // 800 / 64/ Truncates
	// int yTilesPerPanel = HEIGHT / FLOOR_TILE_HEIGHT; // 600 / 44
	//
	// // Should just have a defined viewport?
	//
	// // Make a new tileset with the correct numbers of tiles
	// Floor[][] tiles = new Floor[xTilesPerPanel][yTilesPerPanel];
	//
	// // get top, left, right, bottom points to know how many tiles
	// // to render
	//
	// // iterate through the game world.
	// int topLeftX = actorX - xTilesPerPanel;
	// int topLeftY = actorY - yTilesPerPanel;
	//
	// return tiles;
	// }

	public void updateGame(World game) {
		this.game = game;
	}

	/**
	 * Rotates the rendering in the given direction. Updates the current
	 * orientation in the rendering panel
	 *
	 * @param clockwise
	 */
	public void rotate(Direction dir) {
		switch (dir) {
		case CLOCKWISE:
			updateCurrentOrientationClockwise();
			return;
		case ANTICLOCKWISE:
			updateCurrentOrientationAntiClockwise();
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
	private void updateCurrentOrientationClockwise() {
		switch (currentOrientation) {
		case NORTH:
			currentOrientation = Orientation.EAST;
			return;
		case SOUTH:
			currentOrientation = Orientation.WEST;
			return;
		case EAST:
			currentOrientation = Orientation.SOUTH;
			return;
		case WEST:
			currentOrientation = Orientation.NORTH;
			return;
		default:
			throw new IllegalArgumentException(
					"Current orientation is incorrect");
		}
	}

	/**
	 * Updates the current orientation of the viewer to its anticlockwise
	 * counterpart.
	 */
	private void updateCurrentOrientationAntiClockwise() {
		switch (currentOrientation) {
		case NORTH:
			currentOrientation = Orientation.WEST;
			return;
		case SOUTH:
			currentOrientation = Orientation.EAST;
			return;
		case EAST:
			currentOrientation = Orientation.NORTH;
			return;
		case WEST:
			currentOrientation = Orientation.SOUTH;
			return;
		default:
			throw new IllegalArgumentException(
					"Current orientation is incorrect");
		}
	}

	/**
	 * Draws the background first, then draws the tiles and players.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Floor[][] tiles;
		PriorityQueue<GameObject>[][] objects;
		int actorX = 0, actorY = 0;

		tiles = game.getMap();
		objects = game.getObjects();
		int wd = tiles.length;
		int ht = tiles[0].length;

		// determine if the game is in edit mode
		boolean editMode = game.getEditMode();
		boolean showWalls = game.getShowWalls();

		// get the board coordinates of the player
		if (!editMode) {
			Actor c = game.getCharacterByID(id);
			actorX = c.getX();
			actorY = c.getY();
		} else {
			Point p = game.getEditor();
			actorX = p.x;
			actorY = p.y;
		}

		// http://gamedev.stackexchange.com/questions/25982/how-do-i-determine-the-draw-order-in-an-isometric-view-flash-game
		int[] playerCoords = convertFromGameToScreen(actorX, actorY); // players
																		// coords

		// convert these to the players coordinates
		int offsetX = -playerCoords[0] + getWidth() / 2;
		int offsetY = -playerCoords[1] + getHeight() / 2;

		// how many tiles across from the player should be displayed
		int drawDistance = 10;

		// should be calculating the draw distance instead of using a constant

		// can't draw a square that is a negative number, or bigger than the
		// window
		int minI = Math.max(0, actorX - drawDistance);
		int minJ = Math.max(0, actorY - drawDistance);
		int maxI = Math.min(wd, actorX + drawDistance);
		int maxJ = Math.min(ht, actorY + drawDistance);

		// Initialise draw values
		int x;
		int y;

		// to draw everything its the height and width of the screen
		for (int i = minI; i < maxI; ++i) {
			for (int j = minJ; j < maxJ; j++) {
				// System.out.print("(" + i + "," + j + ")");
				if (tiles[i][j] instanceof Drawable) {
					Drawable d = tiles[i][j];

					int[] coords = convertFromGameToScreen(i, j);
					x = coords[0] + offsetX;
					y = coords[1] + offsetY;

					d.draw(x, y, g);

					if (showWalls) {
						for (Drawable dd : objects[i][j]) {
							if (dd != null) {
								dd.draw(x, y, g);
							}
						}
					}
				}
			}
			// System.out.println();
		}
		editOptions(offsetX, offsetY, editMode, g);
	}

	/**
	 * Davids options for using the editor
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
				int[] q = convertFromGameToScreen(p.x, p.y);
				g.drawOval(q[0] + offsetX + 30, q[1] + offsetY + 15, 5, 5);
			}
			g.setColor(Color.RED);
			for (Point p : zombieSpawnPoints) {
				int[] q = convertFromGameToScreen(p.x, p.y);
				g.drawOval(q[0] + offsetX + 29, q[1] + offsetY + 14, 7, 7);
			}
			g.setColor(Color.BLUE);
			g.drawOval(428, 313, 9, 9);
//			g.drawOval((editor.x * TILE_WIDTH / 2)
//					- (editor.y * TILE_WIDTH / 2) + offsetY + 28, (editor.y
//					* FLOOR_TILE_HEIGHT / 2)
//					+ (editor.x * FLOOR_TILE_HEIGHT / 2) + offsetX + 13, 9, 9);
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
	 * @return
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
		return currentOrientation;
	}

}
