package userInterface.renderWindow;

import gameWorld.Drawable;
import gameWorld.Floor;
import gameWorld.World;

import java.awt.Graphics;
import java.io.IOException;
import javax.swing.JPanel;

import dataStorage.Parser;

/**
 * Provides a 3D view of the world, with locations
 * that are presented from a side on perspective.
 *
 *
 *
 * @author Pauline Kelly
 *
 */
public class RenderPanel extends JPanel {

	/* Application Canvas is a field - need one in
	 * the constructor for the Renderer.
	 *
	 * Need the location passed in - make a new
	 * renderer per location or use setLocation()?
	 *
	 * Need the 4 orientations - NSEW. A location
	 * that is entered from the north is shown with
	 * the view facing south.
	 *
	 * Locations in the distance are shown in the
	 * background.
	 *
	 */

	//For scaling, knowing how big the window is
	private int CANVAS_WIDTH;
	private int CANVAS_HEIGHT;

	private World game;
	private int id;

	private static final int TILE_WIDTH = 64;
	//private static final int FLOOR_TILE_HEIGHT = 42;  //NOT 32? Height changes for each tile
	private static final int FLOOR_TILE_HEIGHT = 44;  //NOT 32? Height changes for each tile

	private boolean testing = true;

	//The panel to be rendered on

	/** Constructor. Takes the height and width of the canvas into account.
	 *
	 * @param wd Width of window
	 * @param ht Height of window
	 */

	public RenderPanel(int id, World game){
		this.game = game;

		CANVAS_WIDTH = this.getWidth();
		CANVAS_HEIGHT = this.getHeight();
	}

	/**
	 * Draws the background first, then draws the tiles and players.
	 */
	@Override
	public void paintComponent(Graphics g){
		System.out.println("painting");
		super.paintComponent(g);

		gameWorld.Tile[][] tiles;

		if(testing){
			 tiles = getDummyWorld();
		}
		else {
			tiles = game.getMap();
		}

		// David's test code
		try {
			World world = Parser.ParseMap("TestMap.csv");
			tiles = world.getMap();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Orientation o = Orientation.NORTH;

		//start from the top center
		int x;
		int y;

		//Draws from the top right of the board, goes across
		//http://gamedev.stackexchange.com/questions/25982/how-do-i-determine-the-draw-order-in-an-isometric-view-flash-game

		int offsetX = 300;
		int offsetY = 300;

		for(int i = 0; i < tiles.length; ++i){
			for(int j = tiles[i].length-1; j >= 0; j--){
				if(tiles[i][j] instanceof Drawable){
					System.out.println("was drawable");

					Drawable d = (Drawable) tiles[i][j];

					x = (j * TILE_WIDTH / 2) + (i * TILE_WIDTH / 2) + offsetX;
					y = (i * FLOOR_TILE_HEIGHT / 2) - (j * FLOOR_TILE_HEIGHT / 2) + offsetY;

					System.out.println(String.format("At i:%d j:%d, x: %d, y: %d", i,j,x,y));
					d.draw(x,y,g);
				}
			}
		}
	}

	/**
	 * Dummy world for testing
	 * @return
	 */
	private gameWorld.Tile[][] getDummyWorld() {
		gameWorld.Tile[][] tiles = new gameWorld.Tile[5][5];

		String [] filenames = new String[] {
				"ground_grey_1.png"
		};
		System.out.println("making the floor");

		//Get the floor
		for(int i = 0; i < tiles.length; ++i){
			for(int j = 0; j < tiles[0].length; ++j){
				tiles[i][j] = new Floor(i,j,filenames, null);
			}
		}

		//Set the walls
		tiles[3][3].setOccupiable(true);

		return tiles;
	}
}
