package gameWorld;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;

import userInterface.renderWindow.Orientation;
import dataStorage.Loader;

public class Floor implements Tile, Drawable{

	private int x;
	private int y;

	private Item item;
	private Wall wall;

	//


	private boolean occupiable;

	private Image currentImage;
	private String imageName;
	private Image[] images;   //in order NSEW

	public Floor(int x, int y, String[] filenames, Item myItem) {
		this.x = x;
		this.y = y;

		this.item = myItem;
		occupiable = true;

		setupImages(filenames);
	}

	/**
	 * Sets up the selection of images that this floor tile can be.
	 * There are 3 possible ways to draw an item:
	 *
	 * [N]
	 * [NS,EW]
	 * [N,S,E,W]
	 *
	 * @param filenames
	 */
	private void setupImages(String[] filenames) {
		images = new Image[filenames.length];  //image is same length as array

		for(int i = 0; i < filenames.length; ++i){
			System.out.println(filenames[i]);
			images[i] = Loader.LoadImage(filenames[i]);
		}
		currentImage = images[0];  //get the north (default orientation)
		imageName = filenames[0];
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
	private Image getSouthOrientationImage() {
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
	private Image getEastOrientationImage() {
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
	private Image getWestOrientationImage() {
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
	public String getCSVCode(Map<String, String> textTileMap) {
		String result = "0-";
		System.out.println(imageName); //TODO delete
		String[] tileCode = imageName.substring(0, imageName.length() - 4).split("_");
		for (x = 0; x < tileCode.length; x++) {
			result = result + textTileMap.get(tileCode[x]);
			if (x < tileCode.length - 1)
				result = result + "-";
		}
		if (item != null) {
			result = result + item.getCSVCode(textTileMap);
		}
		return result;
	}

	@Override
	public void setOccupiable(boolean bool) {
		occupiable = bool;
	}

	@Override
	public void draw(int x, int y, Graphics g) {
//		System.out.println("drawing floor tile");
		g.drawImage(currentImage, x, y, null);

		if(item != null){
			item.draw(x,y,g);
		}
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}
}
