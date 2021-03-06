package zompocalypse.gameworld.characters;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.world.World;
import zompocalypse.ui.rendering.ImageUtils;

/**
 * An Actor is a record of information about a particular character in the
 * game. There are essentially two kinds of characters: player controlled and
 * computer controlled.
 *
 * @author Kieran Mckay, 300276166
 */
public abstract class Actor implements GameObject {

	private static final long serialVersionUID = 1L;

	protected int xCoord; 	// x-position
	protected int yCoord; 	// y-position
	protected int uid;		//unique ID for actor

	protected String filename;	//the filename for this actor
	protected World game;		//a reference to the world

	private String[] filenames;
	private transient ImageIcon[] images;
	private transient ImageIcon currentImage;
	protected Orientation orientation;

	/**
	 * Constructor taking an X and Y co-ordinate for the current character
	 */
	public Actor(int uid, World game, int xCoord, int yCoord,
			Orientation direction, String[] filenames) {

		this.game = game;

		this.orientation = direction;
		this.filenames = filenames;

		ImageUtils imu = ImageUtils.getImageUtilsObject();
		this.images = imu.setupImages(filenames);
		this.currentImage = images[0];
		this.filename = filenames[0]; // pass in the name of the character

		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.uid = uid; // everyone should have a userid so that they can be
						// drawn
	}

	/**
	 * The current X coordinate for this character
	 */
	public String[] getFilenames() {
		return filenames;
	}

	/**
	 * The current X coordinate for this character
	 */
	public int getX() {
		return xCoord;
	}

	/**
	 * set X coord of this character
	 * @param x
	 */
	public void setX(int x) {
		this.xCoord = x;
	}

	/**
	 * set Y coord of this character
	 * @param y
	 */
	public void setY(int y) {
		this.yCoord = y;
	}

	/**
	 * Get unique id
	 * @return
	 */
	public int getUid() {
		return this.uid;
	}

	/**
	 * Get character's name
	 * @return
	 */
	@Override
	public String getFileName() {
		return this.filename;
	}

	/**
	 * Gets the current image
	 * @return
	 */
	protected ImageIcon getCurrentImage() {
		return currentImage;
	}

	/**
	 * The current y coordinate for this character
	 */
	public int getY() {
		return yCoord;
	}

	/**
	 * Returns the world
	 * @return
	 */
	public void setWorld(World game) {
		this.game = game;
	}

	/**
	 * Returns the world
	 * @return
	 */
	public World getWorld() {
		return game;
	}

	/**
	 * Sets up the drawing for the character, to be overridden using the specific offset
	 */
	public void draw(int realx, int realy, int offsetY, Graphics g, Orientation worldOrientation) {
		ImageUtils imu = ImageUtils.getImageUtilsObject();
		Orientation ord = Orientation.getCharacterOrientation(orientation,
				worldOrientation);
		images = imu.setupImages(filenames);
		currentImage = imu.getImageForOrientation(ord, images);
		g.drawImage(getCurrentImage().getImage(), realx, realy + offsetY, null);
	}

	@Override
	public int compareTo(GameObject o) {
		//always wants to be highest priority
		return 0;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * Sets the orientation for the character
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * Get the direction for the character
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * The following method is provided to allow characters to take actions on
	 * every clock tick; for example, ghosts may choose new directions to move
	 * in.
	 */
	public abstract void tick(World game);
}
