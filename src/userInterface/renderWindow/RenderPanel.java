package userInterface.renderWindow;

import gameWorld.Drawable;
import gameWorld.World;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

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



	//For rendering objects within the game
	private List<GameObject> objects = new ArrayList<>();

	//For scaling, knowing how big the window is
	private int CANVAS_WIDTH;
	private int CANVAS_HEIGHT;

	private BufferedImage background;

	private String filePath = "img/";

	private World game;
	private int id;

	private static final int TILE_WIDTH = 64;
	private static final int TILE_HEIGHT = 32;
	//The panel to be rendered on

	/** Constructor. Takes the height and width of the canvas into account.
	 *
	 * @param wd Width of window
	 * @param ht Height of window
	 */

	public RenderPanel(int id, World game, BufferedImage background){
		this.game = game;

		CANVAS_WIDTH = this.getWidth();
		CANVAS_HEIGHT = this.getHeight();

		try {
			background = ImageIO.read(new File(filePath + "black-background.png"));
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't find the background pic");
		}
		render();
	}

	/**
	 * The user has moved to a new position - now need to
	 * check what to render.
	 *
	 * 3 situations:
	 * 	a) 	They have moved forward a step in the same direction
	 *  b) 	They have moved backwards a step in the same direction
	 *  c) 	They have rotated left or right - check which
	 *  	then render the correct location.
	 *
	 *  Change the shift depending on what is chosen.
	 */
	public void update(){

	}

	/**
	 * Draws the background first, then draws the tiles and players.
	 */
	public void paintComponent(Graphics g){
		System.out.println("painting");
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);

		gameWorld.Tile[][] tiles = game.getMap();

		Orientation o = Orientation.NORTH;

		//start from the top center
		int x = CANVAS_WIDTH / 2;
		int y = 0;

		//Draws from the top right of the board, goes across
		//http://gamedev.stackexchange.com/questions/25982/how-do-i-determine-the-draw-order-in-an-isometric-view-flash-game
		for(int i = 0; i < tiles.length; ++i){
			for(int j = tiles[i].length; j >= 0; j--){
				if(tiles[i][j] instanceof Drawable){
					Drawable d = (Drawable) tiles[i][j];
					//Image tileImage = tiles[i][j].getImage();

					x = (j * TILE_WIDTH/2) + (i * TILE_WIDTH/2);
					y = (i * TILE_HEIGHT/2) - (j * ( TILE_HEIGHT/2));
					d.draw(g);
				}
			}
		}

		//		switch(currentOrientation){
		//		case NORTH:
		//			drawNorth(g);
		//			break;
		//		case SOUTH:
		//			drawSouth(g);
		//			break;
		//		case EAST:
		//			drawEast(g);
		//			break;
		//		case WEST:
		//			drawWest(g);
		//			break;
		//		default:
		//			throw new IllegalStateException("Orientation shouldn't get to this.");
		//		}

	}

	/**
	 * Draws the board as seen from the west.
	 *
	 * Isometric formula: 	x' = x - z
	 * 						y' = y + ((x + z)/2)
	 *
	 *x: x - y,
        y: (x / 2) + (y / 2) - z
		http://stackoverflow.com/questions/892811/drawing-isometric-game-worlds
        http://gamedev.stackexchange.com/questions/8151/how-should-i-sort-images-in-an-isometric-game-so-that-they-appear-in-the-correct
	 *
	 * http://flarerpg.org/tutorials/isometric_intro/
	 * @param g
	 */
	private void drawWest(Graphics g) {
		double x;
		double y;

//		for (int i = 0; i < tiles.length; i++) {
//			for (int j = 0; j < tiles[0].length; j++) {
//				//Initially the current location
//				double xOffset = playerLocation.getX();
//				double yOffset = playerLocation.getY();
//
//				x =  i + 5.5 - xOffset * 0.5 * WIDTH / 10;  //more complicated
//				y =  i + 5.5 - yOffset * 0.5 * WIDTH / 10;
//
//				Tile t = tiles[i][j];
//
//				//the tiles also draws the object on it
//				if(t != null){
//					g.drawImage(t.draw(), (int) x, (int) y, WIDTH, HEIGHT, null);  //draw method also handles drawing items and players
//
//					//					for(Image img: t.getObjects()){
//					//						g.drawImage(img.draw(), (int) x, (int) y, WIDTH, HEIGHT, null);  //draw method also handles drawing items and players
//					//					}
//				}
//				//otherwise skip it?
//			}
//		}

	}

	private void drawEast(Graphics g) {
		// TODO Auto-generated method stub

	}

	private void drawSouth(Graphics g) {
		// TODO Auto-generated method stub

	}

	private void drawNorth(Graphics g) {
		// TODO Auto-generated method stub

	}

	/**
	 * The Rendering method that does all the work.
	 * Draws the objects with the lowest Z first.
	 *
	 * @return
	 */
	private BufferedImage render(){
		return null;
	}

	/**
	 * Load in the data for the new Location.
	 * Should probably use this method for each location.
	 *
	 * @param file
	 */
	private void onLoad(File file){

		Tile [][] tiles = new Tile[6][6];

		try {
			Scanner sc = new Scanner(file);

			while(sc.hasNextLine()){  //get the next item in the folder
				String line = sc.nextLine();
				Scanner scanLine = new Scanner(line);

				while(scanLine.hasNext()){
					String character = scanLine.next();
					System.out.print(character);
					Tile tile = null;

					switch(character){
					case "0":
						tile = new WallTile();
						break;
					case "1":
						tile = new GroundTile();
						break;
					default:
						throw new IllegalStateException("Illegal character parsed.");
					}


				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
