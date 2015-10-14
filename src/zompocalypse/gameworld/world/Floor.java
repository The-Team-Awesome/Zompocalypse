package zompocalypse.gameworld.world;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import zompocalypse.gameworld.Drawable;
import zompocalypse.gameworld.Orientation;
import zompocalypse.ui.rendering.ImageUtils;

/**
 * The Floor class is a tile on the board, which is responsible for drawing the
 * ground and the items that are situated on this part of the Floor.
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

	protected transient ImageIcon[] images;
	protected transient ImageIcon currentImage;
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
	public void setCurrentImage(Orientation orientation) {
		ImageUtils imu = ImageUtils.getImageUtilsObject();
		// Changes the current image if the board is rotated
		switch (orientation) {
		case NORTH:
			currentImage = images[0]; // Will always be the first image
			return;
		case SOUTH:
			currentImage = imu.getSouthImage(images);
			return;
		case EAST:
			currentImage = imu.getEastImage(images);
			return;
		case WEST:
			currentImage = imu.getWestImage(images);
			return;
		}
	}

	/**
	 * Returns the X co-ordinate of this tile's position in the 2D map of the
	 * world
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the Y co-ordinate of this tile's position in the 2D map of the
	 * world
	 */
	public int getY() {
		return y;
	}

	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		ImageUtils imu = ImageUtils.getImageUtilsObject();
		images = imu.setupImages(filenames);
		currentImage = imu.getImageForOrientation(worldOrientation, images);

		g.drawImage(currentImage.getImage(), x, y, null);
	}

	@Override
	public String getFileName() {
		return imageName;
	}

	public void rotate() {
		String[] rotate = new String[filenames.length];
		for (int x = 0; x < rotate.length - 1; x++) {
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
