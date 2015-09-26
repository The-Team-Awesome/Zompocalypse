package gameWorld;

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

	protected Image[] images;
	protected Image currentImage;
	protected String imageName;
	protected ImageUtils imu = ImageUtils.getImageUtilsObject();

	public Wall(String[] filenames){
		imu = ImageUtils.getImageUtilsObject();

		images = imu.setupImages(filenames);
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(int x, int y, Graphics g) {
		g.drawImage(currentImage, x, y, null);
	}
}
