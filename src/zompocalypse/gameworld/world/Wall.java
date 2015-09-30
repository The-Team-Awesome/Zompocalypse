package zompocalypse.gameworld.world;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import zompocalypse.gameworld.GameObject;
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


	public Wall(String[] filenames, int offset){
		imu = ImageUtils.getImageUtilsObject();

		images = imu.setupImages(filenames);
		this.currentImage = images[0];
		this.imageName = filenames[0];
		this.offset = offset;
	}

	@Override
	public String getFileName() {
		return imageName;
	}

	@Override
	public void draw(int x, int y, Graphics g) {
		//System.out.println("drew current image" + currentImage.toString());
		g.drawImage(currentImage.getImage(), x, y - offset, null);
	}

	public int getOffset() {
		return offset;
	}
}
