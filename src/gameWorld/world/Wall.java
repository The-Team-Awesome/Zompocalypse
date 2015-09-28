package gameWorld.world;

import gameWorld.Drawable;
import gameWorld.GameObject;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;

import userInterface.renderWindow.ImageUtils;

/**
 * Walls can have
 * @author kellypaul1
 *
 */
public class Wall implements GameObject {

	protected transient Image[] images;
	protected transient Image currentImage;
	protected transient ImageUtils imu = ImageUtils.getImageUtilsObject();

	protected String imageName;


	public Wall(String[] filenames){
		imu = ImageUtils.getImageUtilsObject();

		images = imu.setupImages(filenames);
		this.currentImage = images[0];
		this.imageName = filenames[0];
	}

	@Override
	public String getFileName() {
		return imageName;
	}

	@Override
	public void draw(int x, int y, Graphics g) {
		System.out.println("drew current image" + currentImage.toString());
		g.drawImage(currentImage, x, y, null);
	}
}
