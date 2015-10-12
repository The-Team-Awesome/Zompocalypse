package zompocalypse.gameworld.items;

import java.awt.Graphics;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.ImageIcon;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.world.World;

public class Torch implements Item {

	private static final long serialVersionUID = 1L;
	private ImageIcon currentImage;
	private String filename;
	private int uid;

	public Torch(String filename, int uid) {
		this.filename = filename;
		currentImage = Loader.LoadSpriteIcon(filename);
		this.uid = uid;
	}

	@Override
	public String getFileName() {
		return filename;
	}

	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		g.drawImage(currentImage.getImage(), x+10, y, null);

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
			inventory.add(this);
			World world = player.getWorld();
			PriorityQueue<GameObject>[][] objects = world.getObjects();

			// TODO: This would be much nicer if objects could be retrieved from a map of ids to GameObjects
			for(int x = 0; x < objects.length; x++) {
				for(int y = 0; y < objects[0].length; y++) {
					for(GameObject object : objects[x][y]) {
						if(object.equals(this)) {
							objects[x][y].remove(object);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean occupiable(){
		return true;
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
		return "A torch. You could use this to light the way!";
	}

}
