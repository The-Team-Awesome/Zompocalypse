package userInterface.renderWindow;

import gameWorld.*;
import gameWorld.world.*;
import gameWorld.items.*;
import gameWorld.characters.*;

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
	private int WIDTH;
	private int HEIGHT;

	private World game;
	private int id;

	private static final int TILE_WIDTH = 64;
	//private static final int FLOOR_TILE_HEIGHT = 42;  //NOT 32? Height changes for each tile
	private static final int FLOOR_TILE_HEIGHT = 42;  //NOT 32? Height changes for each tile

	private boolean testing = true;

	//The panel to be rendered on

	/** Constructor. Takes the height and width of the canvas into account.
	 *
	 * @param wd Width of window
	 * @param ht Height of window
	 */
	public RenderPanel(int id, World game){
		this.game = game;

		WIDTH = this.getWidth();
		HEIGHT = this.getHeight();


	}

	/**
	 * 	Use the player id and the game objects to get the clipping
	 *  from the player.
	 *
	 *  The clipping is a smaller version of the complete game board that
	 *  will be displayed on the screen.
	 */
	public Tile[][] clip(){

		Actor c = game.getCharacterByID(id);

		//use this to find neighbouring tiles
		int actorX = c.getX();
		int actorY = c.getY();

		//find the position to render the character at
		int renderActorX = WIDTH / 2;
		int renderActorY = HEIGHT / 2;

		int xTilesPerPanel = WIDTH / TILE_WIDTH;  //800 / 64/ Truncates
		int yTilesPerPanel = HEIGHT / FLOOR_TILE_HEIGHT; //600 / 44

		//Make a new tileset with the correct numbers of tiles
		Tile[][] tiles = new Tile[xTilesPerPanel][yTilesPerPanel];

		//iterate through the game world.
		int topLeftX = actorX - xTilesPerPanel;
		int topLeftY = actorY - yTilesPerPanel;

		return tiles;

	}

	/**
	 * Draws the background first, then draws the tiles and players.
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		int wd = 0, ht = 0;

		gameWorld.world.Tile[][] tiles;
		gameWorld.GameObject[][] objects;

		if(testing){
			wd = 5; ht = 5;
			 tiles = getDummyTiles(wd,ht);

		}
		else {
			// David's test code
			try {
				World world = Parser.ParseMap("TestMap2.xml");
				tiles = world.getMap();
				wd = world.width();
				ht = world.height();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				tiles = game.getMap();
			}
		}

		objects = getDummyObjects(wd, ht);
		Orientation o = Orientation.NORTH;

		//start from the top center
		int x;
		int y;

		//Draws from the top right of the board, goes across
		//http://gamedev.stackexchange.com/questions/25982/how-do-i-determine-the-draw-order-in-an-isometric-view-flash-game

		int offsetX = 300;
		int offsetY = 300;

		tiles[0][0].draw(offsetX, offsetY, g);

		for(int i = 0; i < tiles.length; ++i){
			for(int j = tiles[i].length-1; j >= 0; j--){
				if(tiles[i][j] instanceof Drawable){
					Drawable d = (Drawable) tiles[i][j];
					System.out.println("is drawable");
					x = (j * TILE_WIDTH / 2) + (i * TILE_WIDTH / 2) + offsetX;
					y = (i * FLOOR_TILE_HEIGHT / 2) - (j * FLOOR_TILE_HEIGHT / 2) + offsetY;

					System.out.println(String.format("At i:%d j:%d, x: %d, y: %d", i,j,x,y));
					d.draw(x,y,g);

					Drawable dd = (Drawable) objects[i][j];
					if(dd != null){
						System.out.println("draw wall");
						dd.draw(x, y, g);
					}

				}
			}
		}
	}

	private GameObject[][] getDummyObjects(int wd, int ht) {
		gameWorld.GameObject[][] objects = new GameObject[wd][ht];
		//Create a wall
		Wall w = new Wall(new String[] {
				"wall_grey_3_t_n.png",
				"wall_grey_3_t_s.png",
				"wall_grey_3_t_e.png",
				"wall_grey_3_t_w.png"
		});

		//put the wall at the items position
		objects[2][2] = w;

		return objects;
	}

	/**
	 * Dummy world for testing
	 * @return
	 */
	private gameWorld.world.Tile[][] getDummyTiles(int wd, int ht) {
		gameWorld.world.Tile[][] tiles = new Tile[wd][ht];

		String [] filenames = new String[] {
				"ground_grey_1.png"
		};
		System.out.println("making the floor");

		//Do all the floor tiles first
		for(int i = 0; i < wd; ++i){
			for(int j = 0; j < ht; ++j){
				tiles[i][j] = new Floor(i,j,filenames);
			}
		}
		return tiles;
	}
}
