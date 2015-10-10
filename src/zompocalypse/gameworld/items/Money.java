package zompocalypse.gameworld.items;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.Player;

public class Money implements Item {

	private transient Image currentImage;
	private String filename;
	private int uid;

	private int amount;
	private String type;

	public Money(String filename, int uid, int amount) {
		this.filename = filename;
		currentImage = Loader.LoadSprite(filename);
		this.uid = uid;
		this.amount = amount;

		String type = filename.replace("coins_", "");
		type = type.replace(".png", "");
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String getFileName() {
		return filename;
	}

	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		g.drawImage(currentImage, x+16, y, null);

	}

	@Override
	public int compareTo(GameObject o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void use(Player player) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean movable() {
		return true;
	}

	@Override
	public int getUniqueID() {
		return uid;
	}

	@Override
	public String examine() {

		String description = "";

		if(amount < 50) {
			description = "A small stack of " + type + " coins.";
		} else if(amount < 100) {
			description = "A stack of " + type + " coins.";
		} else {
			description = "A huge stack of " + type + " coins! You'll be rich!";
		}

		return description;
	}

}
