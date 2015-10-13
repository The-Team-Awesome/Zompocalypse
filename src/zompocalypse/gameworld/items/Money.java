package zompocalypse.gameworld.items;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.ImageIcon;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.world.World;

public class Money implements Item {

	private static final long serialVersionUID = 1L;
	private ImageIcon currentImage;
	private String filename;
	private int uid;

	private int amount;
	private String type;

	public Money(String string, int uid, int amount) {
		this.filename = string;
		currentImage = Loader.LoadSpriteIcon(string);
		this.uid = uid;
		this.amount = amount;

		type = string.replace("coins_", "");
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

	public String getType() {
		return type;
	}

	@Override
	public String getFileName() {
		return filename;
	}

	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		g.drawImage(currentImage.getImage(), x+16, y, null);

	}

	@Override
	public int compareTo(GameObject o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean occupiable(){
		return true;
	}

	@Override
	public void use(Player player) {
		List<Item> inventory = player.getInventory();

		boolean added = false;

		if(!inventory.contains(this)) {
			for(Item i : inventory) {
				if(i instanceof Money) {
					Money money = (Money) i;
					money.add(amount);
					added = true;
				}
			}

			if(!added) {
				player.pickUp(this);
			}

			World world = player.getWorld();
			PriorityQueue<GameObject>[][] objects = world.getObjects();
			// TODO: This would be much nicer if objects could be retrieved from a map of ids to GameObjects
			for(int x = 0; x < objects.length; x++) {
				for(int y = 0; y < objects[0].length; y++) {
					for(GameObject object : objects[x][y]) {
						if(object.equals(this)) {
							world.addItemToRemove(new Point(x, y), object);
						}
					}
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
