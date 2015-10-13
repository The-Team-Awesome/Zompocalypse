package zompocalypse.ui.rendering;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.Orientation;

/**
 * Uses the Singleton design pattern.
 * Provides a number of image utilities to assist in the rendering of the
 * World.
 *
 * @author Pauline Kelly
 *
 */
public class ImageUtils {

	private static ImageUtils utils = new ImageUtils();
	private static int count = 0;
	private static int calledCount = 0;
	private Map<String, ImageIcon[]> stringsToImages = new HashMap<String, ImageIcon[]>();
	
	
	protected ImageUtils(){}  //Nothing else can instantiate  a new imageutils object

	/**
	 * A public method that can return an ImageUtils object to be used.
	 * This is a safe way to access the object.
	 *
	 * @return The new ImageUtils object.
	 */
	public static ImageUtils getImageUtilsObject(){
		return utils;
	}

	/**
	 * Sets up the selection of images that this floor tile can be.
	 * There are 3 possible ways to draw an item:
	 *
	 * [N]
	 * [NS,EW]
	 * [N,E,S,W]
	 *
	 * @param filenames
	 * @return An array of new ImageIcons for the Object.
	 */
	public ImageIcon[] setupImages(String[] filenames) {
		if(stringsToImages.containsKey(filenames[0])) {
			return stringsToImages.get(filenames[0]);
		}
		
		ImageIcon[] images = new ImageIcon[filenames.length];  //image is same length as array

		for(int i = 0; i < filenames.length; ++i){
			images[i] = Loader.LoadSpriteIcon(filenames[i]);
		}
		
		stringsToImages.put(filenames[0], images);
		
		return images;
	}

	/**
	 * If the orientation has changed, or if the player has changed direction,
	 * then change the current image for the tile or game object.
	 *
	 * @param orientation The current orientation of the board
	 * @param images The set of images to select from
	 * @return The imageicon for the correct orientation
	 */
	public ImageIcon getImageForOrientation(Orientation orientation, ImageIcon[] images){
		switch(orientation){
		case NORTH:
			return images[0];
		case SOUTH:
			return getSouthImage(images);
		case EAST:
			return getEastImage(images);
		case WEST:
			return getWestImage(images);
		default:
			throw new IllegalStateException("Orientation was abnormal: " + orientation);
		}
	}

	/**
	 * Get the orientation for the image when viewed from the South.
	 *
	 * @param images The set of images to select from
	 * @return The imageicon for the correct orientation
	 */
	public ImageIcon getSouthImage(ImageIcon[] images) {
		switch(images.length){
		case 1:
			return images[0];  //same for all
		case 2:
			return images[0];
		case 4:
			return images[2];  //get the 2nd image
		default:
			throw new IllegalStateException("Shouldn't get this far - SOUTH");
		}
	}

	/**
	 * Get the orientation for the image when viewed from the East.
	 * @param images The set of images to select from
	 * @return The imageicon for the correct orientation
	 */
	public ImageIcon getEastImage(ImageIcon[]images) {
		switch(images.length){
		case 1:
			return images[0];  //same for all
		case 2:
			return images[1];
		case 4:
			return images[3];  //get the 3rd image
		default:
			throw new IllegalStateException("Shouldn't get this far - EAST");
		}
	}

	/**
	 * Get the orientation for the image when viewed from the West.
	 * @param images The set of images to select from
	 * @return The imageicon for the correct orientation
	 */
	public ImageIcon getWestImage(ImageIcon[]images) {
		switch(images.length){
		case 1:
			return images[0];  //same for all
		case 2:
			return images[1];
		case 4:
			return images[1];  //get the 4th image
		default:
			throw new IllegalStateException("Shouldn't get this far - WEST");
		}
	}
}
