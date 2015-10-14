package zompocalypse.gameworld.world;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.ui.rendering.ImageUtils;

/**
 * Wall represents a wall in the World. A wall cannot be occupied.
 *
 * @author Pauline Kelly
 *
 */
public class Wall implements GameObject {

	private static final long serialVersionUID = 1L;
	protected ImageIcon[] images;
	protected ImageIcon currentImage;
	protected transient ImageUtils imu = ImageUtils.getImageUtilsObject();

	protected String imageName;
	protected int offset;
	private String[] filenames;

	public Wall(String[] filenames, int offset) {
		imu = ImageUtils.getImageUtilsObject();

		images = imu.setupImages(filenames);
		this.currentImage = images[0];
		this.imageName = filenames[0];
		this.offset = offset;
		this.filenames = filenames;
	}

	@Override
	public String getFileName() {
		return imageName;
	}

	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		ImageUtils imu = ImageUtils.getImageUtilsObject();
		currentImage = imu.getImageForOrientation(worldOrientation, images);

		if (imageName.contains("plant")) {
			g.drawImage(currentImage.getImage(), x + offset, y - offset, null);
		} else {
			g.drawImage(currentImage.getImage(), x, y - offset, null);
		}
	}

	/**
	 * Returns the offset for the wall.
	 *
	 * @return
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Rotates the wall to a different angle.
	 */
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

	/**
	 * Returns a wall with the same asset and offset from this.
	 *
	 * @return Wall - a wall that's similar to this. *
	 */
	public Wall cloneMe() {
		return new Wall(filenames, offset);
	}

	@Override
	public int compareTo(GameObject o) {
		return 0;
	}

	/**
	 * Returns if the wall is occupiable.
	 *
	 * @return false - a door can never be occupied.
	 */
	@Override
	public boolean occupiable() {
		return false;
	}

	/**
	 * Return the file names of the images for the walls.
	 *
	 * @return a String array with the file names of the images available for
	 *         walls.
	 */
	public String[] getFilenames() {
		return filenames;
	}

}
