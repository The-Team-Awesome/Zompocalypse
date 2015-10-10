package zompocalypse.gameworld.world;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import zompocalypse.gameworld.Drawable;
import zompocalypse.gameworld.Orientation;
import zompocalypse.ui.rendering.ImageUtils;

/**
 * The Floor class is a tile on the board, which is responsible for drawing
 * the ground and the items that are situated on this part of the Floor.
 *
 * Drawables that the floor contains can be:
 *
 *
 * @author Kieran Mckay, 300276166 & Pauline
 *
 */
public class Floor implements Drawable {

	private static final long serialVersionUID = 1L;
	private int x;
	private int y;

	protected ImageIcon[] images;
	protected ImageIcon currentImage;
	protected String imageName;
	protected String[] filenames;

	public Floor(int x, int y, String[] filenames) {
		this.x = x;
		this.y = y;

		ImageUtils imu = ImageUtils.getImageUtilsObject();

		this.filenames = filenames;
		this.images = imu.setupImages(filenames);
		this.currentImage = images[0];
		this.imageName = filenames[0];
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
	private ImageIcon getSouthOrientationImage() {
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
	private ImageIcon getEastOrientationImage() {
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
	private ImageIcon getWestOrientationImage() {
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

	/**
	 * Returns the X co-ordinate of this tile's position in the 2D map of the world
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the Y co-ordinate of this tile's position in the 2D map of the world
	 */
	public int getY() {
		return y;
	}

	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		ImageUtils imu = ImageUtils.getImageUtilsObject();
		currentImage = imu.getCurrentImageForOrientation(worldOrientation, images);

		g.drawImage(currentImage.getImage(), x, y, null);
	}

	public String getFileName() {
		return imageName;
	}

	public void rotate() {
		String[] rotate = new String[filenames.length];
		for (int x = 0; x < rotate.length - 1; x ++) {
			rotate[x] = filenames[x + 1];
		}
		rotate[rotate.length - 1] = filenames[0];

		ImageUtils imu = ImageUtils.getImageUtilsObject();

		this.filenames = rotate;
		this.images = imu.setupImages(rotate);
		this.currentImage = images[0];
		this.imageName = rotate[0];

	}

	public Floor cloneMe() {
		return new Floor(x, y, filenames);
	}
}
