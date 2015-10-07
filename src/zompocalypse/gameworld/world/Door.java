package zompocalypse.gameworld.world;

import java.awt.Graphics;
import java.util.Map;

import javax.swing.ImageIcon;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Lockable;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.items.Item;
import zompocalypse.ui.rendering.ImageUtils;

/**
 * A door in the game world.
 * Doors can be locked or unlocked, if they are locked, they require a key to open.
 * A door must be open for a character to walk through it.
 *
 * Implements item, and represents a leaf in the composite pattern.
 *
 * @author Kieran Mckay, 300276166
 */
public class Door implements Item, Lockable {

	private int x;
	private int y;

	protected ImageIcon[] images;
	protected ImageIcon currentImage;
	private String[] fileNames;
	protected transient ImageUtils imu = ImageUtils.getImageUtilsObject();

	private boolean open;
	private boolean locked;
	private boolean occupiable;
	private int uid;
	protected String imageName;
	protected int offset;

	public Door(int x, int y, String[] fileNames, int offset, boolean locked, int uid) {
		imu = ImageUtils.getImageUtilsObject();
		images = imu.setupImages(fileNames);

		this.x = x;
		this.y = y;
		this.fileNames = fileNames;
		this.imageName = fileNames[0];
		this.open = false;
		this.offset = offset;
		this.occupiable = false;
		this.locked = locked;
		this.uid = uid;
	}

	public void use(Player player) {
		if(locked || open){
			return;
		}
		open = true;
	}

	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		//System.out.println("drew current image" + currentImage.toString());
		ImageUtils imu = ImageUtils.getImageUtilsObject();
		currentImage = imu.getCurrentImageForOrientation(worldOrientation, images);
		g.drawImage(currentImage.getImage(), x, y - offset, null);
	}

	public boolean isLocked() {
		return locked;
	}

	public boolean unlock(boolean hasKey){
		if (hasKey){
			locked = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean movable() {
		return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean occupiable() {
		//TODO
		return open;
	}

	@Override
	public String getFileName() {
		return imageName;
	}

	@Override
	public String getCSVCode(Map<String, String> textTileMap) {
		return "2";

	}

	public void setOccupiable(boolean bool) {
		occupiable = bool;
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
		if (locked){
			return "A locked door. Maybe a key could open it";
		} else if (!open){
			return "A closed door that can be opened";
		} else if (open){
			return "An open door.";
		} else {
			//not currently reachable
			return "A door of some sort";
		}
	}

	public Door cloneMe(int uid) {
		return new Door(x, y, fileNames, offset, locked, uid);
	}

	public int getOffset() {
		return offset;
	}

	public void rotate() {

		String[] rotate = new String[fileNames.length];
		for (int x = 0; x < rotate.length - 1; x ++) {
			rotate[x] = fileNames[x + 1];
		}
		rotate[rotate.length - 1] = fileNames[0];

		ImageUtils imu = ImageUtils.getImageUtilsObject();

		this.fileNames = rotate;
		this.images = imu.setupImages(rotate);
		this.currentImage = images[0];
		this.imageName = rotate[0];
	}
}
