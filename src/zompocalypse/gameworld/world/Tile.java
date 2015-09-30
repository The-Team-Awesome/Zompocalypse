package zompocalypse.gameworld.world;

import java.awt.Image;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.Drawable;
import zompocalypse.gameworld.Orientation;

/**
 * Represents a Tile in the world of Zompocalypse.
 * A Tile is a single location in a 2D array of tiles representing the game world.
 *
 * @author Kieran Mckay, 300276166
 */
public abstract class Tile implements Drawable{

	protected transient Image[] images;
	protected transient Image currentImage;
	protected String imageName;

	/**
	 * Returns the X co-ordinate of this tile's position in the 2D map of the world
	 */
	public abstract int getX();
	/**
	 * Returns the Y co-ordinate of this tile's position in the 2D map of the world
	 */
	public abstract int getY();

	/**
	 * Set the occupiable status of the Tile
	 */
	public abstract void setOccupiable(boolean bool);

	/**
	 * If this tile is able to be occupied.
	 */
	public abstract boolean occupiable();

	/**
	 * Sets up the selection of images that this floor tile can be.
	 * There are 3 possible ways to draw an item:
	 *
	 * [N]
	 * [NS,EW]
	 * [N,S,E,W]
	 *
	 * @param filenames
	 */
	protected void setupImages(String[] filenames) {
		images = new Image[filenames.length];  //image is same length as array

		for(int i = 0; i < filenames.length; ++i){
			System.out.println(filenames[i]);
			images[i] = Loader.LoadSprite(filenames[i]);
		}
		currentImage = images[0];  //get the north (default orientation)
		imageName = filenames[0];
	}

	/**
	 * If the orientation has changed, or if the player has changed direction,
	 * then change the current image.
	 */
	public void setCurrentImage(Orientation orientation){
		//Changes the current image if the board is rotated
		switch(orientation){
		case NORTH:
			currentImage = images[0];  //Will always be the first image
			return;
		case SOUTH:
			currentImage =
			getSouthOrientationImage();
			return;
		case EAST:
			currentImage =
			getEastOrientationImage();
			return;
		case WEST:
			currentImage =
			getWestOrientationImage();
			return;
		}
	}

	/**
	 * Get the orientation for the image when viewed from the South.
	 * @return
	 */
	private Image getSouthOrientationImage() {
		switch(images.length){
		case 1:
			return images[0];  //same for all
		case 2:
			return images[0];
		case 4:
			return images[1];  //get the 2nd image
		default:
			throw new IllegalStateException("Shouldn't get this far - SOUTH");
		}
	}

	/**
	 * Get the orientation for the image when viewed from the East.
	 * @return
	 */
	private Image getEastOrientationImage() {
		switch(images.length){
		case 1:
			return images[0];  //same for all
		case 2:
			return images[1];
		case 4:
			return images[2];  //get the 3rd image
		default:
			throw new IllegalStateException("Shouldn't get this far - EAST");
		}
	}

	/**
	 * Get the orientation for the image when viewed from the West.
	 * @return
	 */
	private Image getWestOrientationImage() {
		switch(images.length){
		case 1:
			return images[0];  //same for all
		case 2:
			return images[1];
		case 4:
			return images[3];  //get the 4th image
		default:
			throw new IllegalStateException("Shouldn't get this far - WEST");
		}
	}
}
