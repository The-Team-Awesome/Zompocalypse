package gameWorld.items;

import gameWorld.characters.Player;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;

import dataStorage.Loader;

/**
 * A key used to open a locked item such as a door or chest etc.
 * Implements item, and represents a leaf in the composite pattern.
 *
 * @author Kieran Mckay, 300276166
 */
public class Key implements Item{
	private String filename;
	private Image currentImage;
	private int uid;

	public Key(String filename, int uid) {
		this.filename = filename;
		currentImage = Loader.LoadSprite(filename);
		this.uid = uid;
	}

	@Override
	public void use(Player player){

	}

	@Override
	public boolean movable() {
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
		g.drawImage(currentImage, x, y-18, null);

	}

	@Override
	public int getUniqueID() {
		return uid;
	}
}
