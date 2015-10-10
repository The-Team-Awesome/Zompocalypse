package zompocalypse.gameworld.world;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.ui.rendering.ImageUtils;

/**
 * Walls can have
 * @author kellypaul1
 *
 */
public class Wall implements GameObject {

	protected ImageIcon[] images;
	protected ImageIcon currentImage;
	protected transient ImageUtils imu = ImageUtils.getImageUtilsObject();

	protected String imageName;
	protected int offset;
	private String[] filenames;


	public Wall(String[] filenames, int offset){
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
		//System.out.println("drew current image" + currentImage.toString());
		ImageUtils imu = ImageUtils.getImageUtilsObject();
		currentImage = imu.getCurrentImageForOrientation(worldOrientation, images);

		// TODO: This is hacky, yuck!
		if(imageName.contains("plant")) {
			g.drawImage(currentImage.getImage(), x + offset, y - offset, null);
		} else {
			g.drawImage(currentImage.getImage(), x, y - offset, null);
		}
	}

	public int getOffset() {
		return offset;
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

	public Wall cloneMe() {
		return new Wall(filenames, offset);
	}

	@Override
	public int compareTo(GameObject o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
