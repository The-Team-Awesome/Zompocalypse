package zompocalypse.gameworld.items;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.util.Map;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.Player;

public class Weapon implements Item {

	private transient Image currentImage;
	private String filename;
	private int uid;

	private int strength;
	private String description;

	public Weapon(String filename, String description, int uid, int strength) {
		this.filename = filename;
		currentImage = Loader.LoadSprite(filename);
		this.description = description;
		this.uid = uid;
		this.strength = strength;
	}

	public int getStrength() {
		return strength;
	}

	public Image getImage() {
		return currentImage;
	}

	@Override
	public String getFileName() {
		return filename;
	}

	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		g.drawImage(currentImage, x+10, y, null);
	}

	@Override
	public int compareTo(GameObject o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void use(Player player) {
		List<GameObject> inventory = player.getInventory();
		if(!inventory.contains(this)) {
			inventory.add(this);
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
		return description;
	}

}
