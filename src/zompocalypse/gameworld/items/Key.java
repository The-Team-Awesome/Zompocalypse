package zompocalypse.gameworld.items;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import javax.swing.ImageIcon;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Lockable;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.world.World;
import zompocalypse.ui.rendering.ImageUtils;

/**
 * A key used to open a locked item such as a door or chest etc. Implements
 * item, and represents a leaf in the composite pattern.
 *
 * @author Kieran Mckay, 300276166
 */
public class Key implements Item {

	private static final long serialVersionUID = 1L;
	private transient ImageIcon[] images;
	private transient ImageIcon currentImage;
	private String filename;
	private int uid;

	public Key(String filename, int uid) {
		this.filename = filename;
		ImageUtils imu = ImageUtils.getImageUtilsObject();
		String[] filenames = { filename };
		images = imu.setupImages(filenames);
		currentImage = images[0];
		this.uid = uid;
	}

	@Override
	public void use(Player player) {
		List<Item> inventory = player.getInventory();

		if (!inventory.contains(this)) {
			player.pickUp(this);
			World world = player.getWorld();
			PriorityBlockingQueue<GameObject>[][] objects = world.getObjects();

			// TODO: This would be much nicer if objects could be retrieved from
			// a map of ids to GameObjects

			for (int x = 0; x < objects.length; x++) {
				for (int y = 0; y < objects[0].length; y++) {
					for (GameObject object : objects[x][y]) {
						if (object.equals(this)) {
							world.addItemToRemove(new Point(x, y), object);
						}
					}
				}
			}
		} else {
			PriorityBlockingQueue<GameObject> obs = player.getObjectsHere();
			for (GameObject o : obs) {
				if (o instanceof Lockable) {
					boolean keyUsed = ((Lockable) o).unlock(true);
					if (keyUsed) {
						player.getInventory().remove(this);
						return;
					}
				}
			}
			obs = player.getObjectsInfront();
			for (GameObject o : obs) {
				if (o instanceof Lockable) {
					boolean keyUsed = ((Lockable) o).unlock(true);
					if (keyUsed) {
						player.getInventory().remove(this);
						return;
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
	public String getFileName() {
		return filename;
	}

	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {

		ImageUtils imu = ImageUtils.getImageUtilsObject();
		String[] filenames = { filename };
		images = imu.setupImages(filenames);
		currentImage = images[0];

		g.drawImage(currentImage.getImage(), x, y - 18, null);

	}

	@Override
	public int getUniqueID() {
		return uid;
	}

	@Override
	public int compareTo(GameObject o) {
		return 0;
	}

	@Override
	public String examine() {
		return "A key. Perhaps this could unlock something....";
	}

	@Override
	public boolean occupiable() {
		return true;
	}
}
