package zompocalypse.gameworld.items;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;
import java.util.PriorityQueue;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Lockable;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.world.World;

/**
 * A key used to open a locked item such as a door or chest etc.
 * Implements item, and represents a leaf in the composite pattern.
 *
 * @author Kieran Mckay, 300276166
 */
public class Key implements Item{

	private transient Image currentImage;
	private String filename;
	private int uid;

	public Key(String filename, int uid) {
		this.filename = filename;
		currentImage = Loader.LoadSprite(filename);
		this.uid = uid;
	}

	@Override
	public void use(Player player){
		PriorityQueue<GameObject> obs = player.getObjectsHere();
		for(GameObject o : obs){
			if(o instanceof Lockable){
				boolean keyUsed = ((Lockable) o).unlock(true);
				if (keyUsed){
					player.getInventory().remove(this);
					return;
				}
			}
		}
		obs = player.getObjectsInfront();
		for(GameObject o : obs){
			if(o instanceof Lockable){
				boolean keyUsed = ((Lockable) o).unlock(true);
				if (keyUsed){
					player.getInventory().remove(this);
					return;
				}
			}
		}
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
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		g.drawImage(currentImage, x, y-18, null);

	}

	@Override
	public int getUniqueID() {
		return uid;
	}

	@Override
	public int compareTo(GameObject o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String examine(){
		return "A key. Perhaps this could unlock something....";
	}
}
