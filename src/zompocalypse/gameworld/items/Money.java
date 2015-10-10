package zompocalypse.gameworld.items;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
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

		if(type.equals("silver")) {
			this.amount = (amount) * 10;
		} else if(type.equals("gold")) {
			this.amount = (amount) * 100;
		}
	}

	public int getAmount() {
		return amount;
	}

	public void add(int amount) {
		this.amount += amount;
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
		List<Item> inventory = player.getInventory();
		if(!inventory.contains(this)) {
			for(Item i : inventory) {
				if(i instanceof Money) {
					Money money = (Money) i;
					money.add(amount);
				}
			}
		}
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
		int amount = this.amount;

		if(type.equals("silver")) {
			amount = amount / 10;
		} else if(type.equals("gold")) {
			amount = amount / 100;
		}

		return "A stack of " + amount + " " + type + " coins.";
	}

}
