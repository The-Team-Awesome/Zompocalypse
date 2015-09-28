package userInterface.renderWindow;

import gameWorld.Orientation;

import java.awt.Image;

import dataStorage.Loader;

/**
 * Uses the Singleton design pattern.
 *
 * @author Pauline
 *
 */
public class ImageUtils {

	private static ImageUtils utils = new ImageUtils();

	protected ImageUtils(){}  //Nothing else can instantiate  a new imageutils object

	public static ImageUtils getImageUtilsObject(){
		return utils;
	}

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
	public Image[] setupImages(String[] filenames) {
		Image[] images = new Image[filenames.length];  //image is same length as array

		for(int i = 0; i < filenames.length; ++i){
			//System.out.println(filenames[i]);
			images[i] = Loader.LoadSprite(filenames[i]);
		}
		return images;
	}

	/**
	 * If the orientation has changed, or if the player has changed direction,
	 * then change the current image for the tile or game object.
	 */
	public Image getCurrentImageForOrientation(Orientation orientation, Image[] images){
		switch(orientation){
		case NORTH:
			return images[0];
		case SOUTH:
			return getSouthOrientationImage(images);
		case EAST:
			return getEastOrientationImage(images);
		case WEST:
			return getWestOrientationImage(images);
		default:
			throw new IllegalStateException("Orientation was abnormal: " + orientation);
		}
	}

	/**
	 * Get the orientation for the image when viewed from the South.
	 * @return
	 */
	public Image getSouthOrientationImage(Image[] images) {
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
	public Image getEastOrientationImage(Image[]images) {
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
	 * @return Image as viewed from the west
	 */
	public Image getWestOrientationImage(Image[]images) {
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
