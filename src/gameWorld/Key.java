package gameWorld;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;

import dataStorage.Loader;

public class Key implements Item, Drawable{
	private String filename;
	private Image currentImage;

	public Key(String filename) {
		this.filename = filename;
		currentImage = Loader.LoadImage(filename);

	}

	@Override
	public void use(Player player){

	}

	@Override
	public boolean canMove() {
		return true;
	}

	@Override
	public String getFileName() {
		return filename;
	}

	@Override
	public String getCSVCode(Map<String, String> textTileMap) {
		return "_ky";
	}

	@Override
	public void draw(int x, int y, Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(currentImage, x, y-18, null);

	}

	@Override
	public int getUniqueID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
