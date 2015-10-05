package zompocalypse.ui.rendering;

import java.awt.Image;

import javax.swing.ImageIcon;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.Orientation;

/**
 * Uses the Singleton design pattern.
 * Loads in an image
 *
 * @author Pauline Kelly
 *
 */
public class ImageUtils {

	//private ClockwiseMap;
	//private AnticlockWiseMap;

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
	 * [N,E,S,W]
	 *
	 * @param filenames
	 */
	public ImageIcon[] setupImages(String[] filenames) {
		ImageIcon[] images = new ImageIcon[filenames.length];  //image is same length as array

		for(int i = 0; i < filenames.length; ++i){
			//System.out.println(filenames[i]);
			images[i] = Loader.LoadSpriteIcon(filenames[i]);
		}
		return images;
	}

	/**
	 * If the orientation has changed, or if the player has changed direction,
	 * then change the current image for the tile or game object.
	 */
	public ImageIcon getCurrentImageForOrientation(Orientation orientation, ImageIcon[] images){
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
	public ImageIcon getSouthOrientationImage(ImageIcon[] images) {
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
	 * @return
	 */
	public ImageIcon getEastOrientationImage(ImageIcon[]images) {
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
	 * @return Image as viewed from the west
	 */
	public ImageIcon getWestOrientationImage(ImageIcon[]images) {
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
