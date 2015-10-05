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

	private World game;
	private final int id;

	private static final int TILE_WIDTH = 64;
	private static final int FLOOR_TILE_HEIGHT = 42;

	private Orientation currentOrientation = Orientation.NORTH;

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

	}

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

		//how many tiles across from the player should be displayed
		int drawDistance = 10;
		boolean editMode = game.getEditMode();
		boolean showWalls = game.getShowWalls();

		Floor[][] tiles;
		PriorityQueue<GameObject>[][] objects;

		int actorX = 0, actorY = 0;


		tiles = game.getMap();
		objects = game.getObjects();
		int wd = tiles.length;
		int ht = tiles[0].length;

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
		int[] playerCoords = convertFromGameToScreen(actorX,actorY);  //players coords

		//convert these to the players coordinates
		int offsetX = -playerCoords[0] + getWidth()/2;
		int offsetY = -playerCoords[1] + getHeight()/2;


		//should be calculating the draw distance instead of using a constant

		//can't draw a square that is a negative number, or bigger than the window
		int minI = Math.max(0,actorX-drawDistance);
		int minJ = Math.max(0,actorY-drawDistance);
		int maxI = Math.min(wd, actorX+drawDistance);
		int maxJ = Math.min(ht, actorY+drawDistance);

		int x;
		int y;
		/// trial

		Floor[][] tempFloor = getDrawAreaFloor(minI,maxI,minJ,maxJ,currentOrientation,tiles);
		PriorityQueue<GameObject>[][] tempObjects = getDrawAreaObjects(minI,maxI,minJ,maxJ,currentOrientation,objects);

		//		//Initialise draw values  --WORKS

		//to draw everything its the height and width of the screen
		for (int i = 0; i < tempFloor.length; ++i) {
			for (int j = 0; j < tempFloor[0].length; j++) {
				//System.out.print("(" + i + "," + j + ")");
				if (tiles[i][j] instanceof Drawable) {
					Drawable d = tempFloor[i][j];

					int[] coords = convertFromGameToScreen(i,j);
					x = coords[0] + offsetX;
					y = coords[1] + offsetY;

					d.draw(x, y, g, currentOrientation);

					if (showWalls) {
						for (Drawable dd : tempObjects[i][j]) {
							if (dd != null) {
								dd.draw(x, y, g, currentOrientation);
							}
						}
					}
				}
			}
			// System.out.println();
		}

		editOptions(offsetX, offsetY, editMode, g);


		/// end trial


		//		//Initialise draw values  --WORKS

		//
		//		//to draw everything its the height and width of the screen
		//		for (int i = minI; i < maxI; ++i) {
		//			for (int j = minJ; j < maxJ; j++) {
		//				//System.out.print("(" + i + "," + j + ")");
		//				if (tiles[i][j] instanceof Drawable) {
		//					Drawable d = tiles[i][j];
		//
		//					int[] coords = convertFromGameToScreen(i,j);
		//					x = coords[0] + offsetX;
		//					y = coords[1] + offsetY;
		//
		//					d.draw(x, y, g);
		//
		//					if (showWalls) {
		//						for (Drawable dd : objects[i][j]) {
		//							if (dd != null) {
		//								dd.draw(x, y, g);
		//							}
		//						}
		//					}
		//				}
		//			}
		//			//System.out.println();
		//		}
		//		editOptions(offsetX, offsetY, editMode, g);
	}

	private PriorityQueue[][] getDrawAreaObjects(int minI, int maxI, int minJ,
			int maxJ, Orientation currentOrientation,
			PriorityQueue<GameObject>[][] objects) {

		PriorityQueue<GameObject>[][] temp = objects;

		switch(currentOrientation){
		case NORTH:
			break;//do nothing
		case EAST:
			temp = rotateObject90(minI, maxI, minJ,maxJ, objects);
			break;
		case SOUTH:
			temp = rotateObject90(minI, maxI, minJ,maxJ, objects);
			temp = rotateObject90(minI, maxI, minJ,maxJ, temp);
			break;
		case WEST:
			temp = rotateObject90(minI, maxI, minJ,maxJ, objects);
			temp = rotateObject90(minI, maxI, minJ,maxJ, temp);
			temp = rotateObject90(minI, maxI, minJ,maxJ, temp);
			break;
		}

		return temp;
	}

	private PriorityQueue[][] rotateObject90(int minI, int maxI, int minJ, int maxJ,
			PriorityQueue<GameObject>[][] objects) {
		PriorityQueue<GameObject>[][] temp = new PriorityQueue[maxI-minI+1][maxJ-minJ+1];
		for (int i = minI; i <= maxI; ++i) {
			for (int j = minJ; j <= maxJ; j++) {
				temp[i-minI][j-minJ] = objects[maxJ-j][i];
			}
		}
		return temp;
	}

	private Floor[][] getDrawAreaFloor(int minI, int maxI, int minJ, int maxJ,
			Orientation currentOrientation, Floor[][]tiles) {
		Floor[][] temp = tiles;

		switch(currentOrientation){
		case NORTH:
			break;//do nothing
		case EAST:
			temp = rotateFloor90(minI, maxI, minJ,maxJ, tiles);
			break;
		case SOUTH:
			temp = rotateFloor90(minI, maxI, minJ,maxJ, tiles);
			temp = rotateFloor90(minI, maxI, minJ,maxJ, temp);
			break;
		case WEST:
			temp = rotateFloor90(minI, maxI, minJ,maxJ, tiles);
			temp = rotateFloor90(minI, maxI, minJ,maxJ, temp);
			temp = rotateFloor90(minI, maxI, minJ,maxJ, temp);
			break;
		}
		return temp;
	}

	private Floor[][] rotateFloor90(int minI, int maxI, int minJ, int maxJ,
			Floor[][] tiles) {
		Floor [][] temp = new Floor[maxI-minI+1][maxJ-minJ+1];
		for (int i = minI; i <= maxI; ++i) {
			for (int j = minJ; j <= maxJ; j++) {
				temp[i-minI][j-minJ] = tiles[maxJ-j][i];
			}
		}

		return temp;
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

		int y = (j * FLOOR_TILE_HEIGHT / 2)
				+ (i * FLOOR_TILE_HEIGHT / 2);

		return new int[] {x,y};
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
