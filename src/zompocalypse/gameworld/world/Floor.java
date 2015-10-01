package zompocalypse.gameworld.world;

import java.awt.Graphics;

import javax.swing.ImageIcon;

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
public class Floor extends Tile{

	private int x;
	private int y;

	//Floor tiles can contain
	private boolean occupiable;

	protected ImageIcon[] images;
	protected ImageIcon currentImage;
	protected String imageName;
	protected String[] filenames;

	public Floor(int x, int y, String[] filenames) {
		this.x = x;
		this.y = y;

		occupiable = true;

		ImageUtils imu = ImageUtils.getImageUtilsObject();

		this.filenames = filenames;
		this.images = imu.setupImages(filenames);
		this.currentImage = images[0];
		this.imageName = filenames[0];
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public boolean occupiable() {
		return occupiable;
	}

	@Override
	public void setOccupiable(boolean bool) {
		occupiable = bool;
	}

	@Override
	public void draw(int x, int y, Graphics g) {
		//System.out.println("drawing floor");
		g.drawImage(currentImage.getImage(), x, y, null);
	}

	@Override
	public String getFileName() {
		return imageName;
	}
}
