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
 *
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

	// For scaling, knowing how big the window is
	private int WIDTH;
	private int HEIGHT;

	private World game;
	private int id;

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

		WIDTH = this.getWidth();
		HEIGHT = this.getHeight();
	}

	/**
	 * Use the player id and the game objects to get the clipping from the
	 * player.
	 *
	 * The clipping is a smaller version of the complete game board that will be
	 * displayed on the screen.
	 */
	public Floor[][] clip() {

		Actor c = game.getCharacterByID(id);

		// use this to find neighboring tiles
		int actorX = c.getX();
		int actorY = c.getY();

		// find the position to render the character at
		int renderActorX = WIDTH / 2;
		int renderActorY = HEIGHT / 2;

		int xTilesPerPanel = WIDTH / TILE_WIDTH; // 800 / 64/ Truncates
		int yTilesPerPanel = HEIGHT / FLOOR_TILE_HEIGHT; // 600 / 44

		// Should just have a defined viewport?

		// Make a new tileset with the correct numbers of tiles
		Floor[][] tiles = new Floor[xTilesPerPanel][yTilesPerPanel];

		// get top, left, right, bottom points to know how many tiles
		// to render

		// iterate through the game world.
		int topLeftX = actorX - xTilesPerPanel;
		int topLeftY = actorY - yTilesPerPanel;

		return tiles;
	}

	public void updateGame(World game) {
		this.game = game;
	}

	/**
	 * The rotation method takes in: -the current orientation of the board -the
	 * new orientation of the player
	 */
	private void rotate(Orientation oldO, int direction, Floor[][] tiles,
			GameObject[][] objects) {

	}

	/**
	 * Rotates the rendering in the given direction.
	 *
	 * @param clockwise
	 */
	public void rotate(Direction clockwise) {
		// and call the private method from here - it was taking
		// too many methods to be public directly:
		// rotate(oldO, direction, tiles, objects);
	}

	/**
	 * Draws the background first, then draws the tiles and players.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Floor[][] tiles;
		PriorityQueue<GameObject>[][] objects;

		/*
		 * // Just change testing to true to use these dummy methods
		 * if(testing){ tiles = getDummyTiles(5,5); objects = getDummyObjects(5,
		 * 5); } else {
		 */
		tiles = game.getMap();
		objects = game.getObjects();
		// S}

		int wd = tiles.length;
		int ht = tiles[0].length;

		Orientation o = Orientation.NORTH;

		// start from the top center
		int x;
		int y;

		// Draws from the top right of the board, goes across
		// http://gamedev.stackexchange.com/questions/25982/how-do-i-determine-the-draw-order-in-an-isometric-view-flash-game

		int offsetX = 200;
		int offsetY = 400;
		boolean editMode = game.getEditMode();
		boolean showWalls = game.getShowWalls();

		// tiles[0][0].draw(offsetX, offsetY, g);

		for (int i = 0; i < tiles.length; ++i) {
			for (int j = 0; j < tiles[i].length; j++) {
				if (tiles[i][j] instanceof Drawable) {
					Drawable d = tiles[i][j];
					// System.out.println("is drawable");
					y = (j * FLOOR_TILE_HEIGHT / 2)
							+ (i * FLOOR_TILE_HEIGHT / 2) + offsetX;
					x = (i * TILE_WIDTH / 2) - (j * TILE_WIDTH / 2) + offsetY;

					// System.out.println(String.format("At i:%d j:%d, x: %d, y: %d",
					// i,j,x,y));
					d.draw(x, y, g);

					if (showWalls) {
						for (Drawable dd : objects[i][j]) {
							// Drawable dd = objects[i][j];
							if (dd != null) {
								// System.out.println("draw wall");
								dd.draw(x, y, g);
							}
						}
					}
				}
			}
		}

		if (editMode) {
			Set<Point> playerSpawnPoints = game.getPlayerSpawnPoints();
			Set<Point> zombieSpawnPoints = game.getZombieSpawnPoints();
			Point editor = game.getEditor();
			g.setColor(Color.GREEN);
			for (Point p : playerSpawnPoints) {
				g.drawOval((p.x * TILE_WIDTH / 2) - (p.y * TILE_WIDTH / 2)
						+ offsetY + 30, (p.y * FLOOR_TILE_HEIGHT / 2)
						+ (p.x * FLOOR_TILE_HEIGHT / 2) + offsetX + 15, 5, 5);
			}
			g.setColor(Color.RED);
			for (Point p : zombieSpawnPoints) {
				g.drawOval((p.x * TILE_WIDTH / 2) - (p.y * TILE_WIDTH / 2)
						+ offsetY + 29, (p.y * FLOOR_TILE_HEIGHT / 2)
						+ (p.x * FLOOR_TILE_HEIGHT / 2) + offsetX + 14, 7, 7);
			}
			g.setColor(Color.BLUE);
			g.drawOval((editor.x * TILE_WIDTH / 2)
					- (editor.y * TILE_WIDTH / 2) + offsetY + 28, (editor.y
					* FLOOR_TILE_HEIGHT / 2)
					+ (editor.x * FLOOR_TILE_HEIGHT / 2) + offsetX + 13, 9, 9);
		}
	}

	/*
	 * private GameObject[][] getDummyObjects(int wd, int ht) { GameObject[][]
	 * objects = new GameObject[wd][ht]; //Create a wall Wall w = new Wall(new
	 * String[] { "wall_grey_3_t_n.png", "wall_grey_3_t_s.png",
	 * "wall_grey_3_t_e.png", "wall_grey_3_t_w.png" }, 50);
	 * 
	 * //put the wall at the items position objects[2][2] = w;
	 * 
	 * return objects; }
	 */
	/**
	 * Dummy world for testing
	 *
	 * @return
	 */
	private Floor[][] getDummyTiles(int wd, int ht) {
		Floor[][] tiles = new Floor[wd][ht];

		String[] filenames = new String[] { "ground_grey_1.png" };
		// System.out.println("making the floor");

		// Do all the floor tiles first
		for (int i = 0; i < wd; ++i) {
			for (int j = 0; j < ht; ++j) {
				tiles[i][j] = new Floor(i, j, filenames);
			}
		}
		return tiles;
	}

	public Orientation getCurrentOrientation() {
		return currentOrientation;
	}

}