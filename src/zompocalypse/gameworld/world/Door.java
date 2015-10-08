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
 * A door in the game world. Doors can be locked or unlocked, if they are
 * locked, they require a key to open. A door must be open for a character to
 * walk through it.
 *
 * Implements item, and represents a leaf in the composite pattern.
 *
 * @author Kieran Mckay, 300276166
 */
public class Door implements Item, Lockable {

	private int x;
	private int y;

	protected ImageIcon[] imagesOpen;
	protected ImageIcon[] imagesClosed;
	protected ImageIcon currentImage;
	private String[] fileNamesOpen;
	private String[] fileNamesClosed;
	protected transient ImageUtils imu = ImageUtils.getImageUtilsObject();

	private boolean open;
	private boolean locked;
	private boolean occupiable;
	private int uid;
	protected String imageName;
	protected int offset;

	public Door(int x, int y, String[] fileNames, int offset, boolean locked,
			int uid) {
		imu = ImageUtils.getImageUtilsObject();
		imagesClosed = imu.setupImages(fileNames);

		String[] tempFiles = new String[2];
		tempFiles[0] = fileNames[0].substring(0, fileNames[0].length() - 13)
				+ "open"
				+ fileNames[0].substring(fileNames[0].length() - 7,
						fileNames[0].length());
		tempFiles[1] = fileNames[1].substring(0, fileNames[1].length() - 13)
				+ "open"
				+ fileNames[1].substring(fileNames[1].length() - 7,
						fileNames[1].length());

		imu = ImageUtils.getImageUtilsObject();
		imagesOpen = imu.setupImages(tempFiles);

		this.x = x;
		this.y = y;
		this.fileNamesClosed = fileNames;
		this.fileNamesOpen = tempFiles;
		this.imageName = fileNamesClosed[0];
		this.open = false;
		this.offset = offset;
		this.occupiable = false;
		this.locked = locked;
		this.uid = uid;

	}

	public void use(Player player) {
		if (locked && !open && player != null) {
			return;
		}

		open = !open;
		occupiable = !occupiable;
		imageName = fileNamesClosed[0];
	}

	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		// System.out.println("drew current image" + currentImage.toString());
		ImageUtils imu = ImageUtils.getImageUtilsObject();
		if (open)
			currentImage = imu.getCurrentImageForOrientation(worldOrientation,
					imagesOpen);
		else
			currentImage = imu.getCurrentImageForOrientation(worldOrientation,
					imagesClosed);
		g.drawImage(currentImage.getImage(), x, y - offset, null);
	}

	public boolean isLocked() {
		return locked;
	}

	public boolean unlock(boolean hasKey) {
		if (hasKey) {
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
		// TODO
		return occupiable;
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

	public String examine() {
		if (locked) {
			return "A locked door. Maybe a key could open it";
		} else if (!open) {
			return "A closed door that can be opened";
		} else if (open) {
			return "An open door.";
		} else {
			// not currently reachable
			return "A door of some sort";
		}
	}

	public Door cloneMe(int uid) {
		return new Door(x, y, fileNamesClosed, offset, locked, uid);
	}

	public int getOffset() {
		return offset;
	}

	public boolean isOpen() {
		return open;
	}

	public void rotate() {

		String[] rotateOpen = new String[fileNamesOpen.length];
		String[] rotateClosed = new String[fileNamesClosed.length];
		for (int x = 0; x < rotateOpen.length - 1; x++) {
			rotateOpen[x] = fileNamesOpen[x + 1];
			rotateClosed[x] = fileNamesClosed[x + 1];
		}
		rotateOpen[rotateOpen.length - 1] = fileNamesOpen[0];
		rotateClosed[rotateClosed.length - 1] = fileNamesClosed[0];

		ImageUtils imu = ImageUtils.getImageUtilsObject();

		this.fileNamesOpen = rotateOpen;
		this.fileNamesClosed = rotateClosed;
		this.imagesOpen = imu.setupImages(rotateOpen);
		this.imagesClosed = imu.setupImages(rotateClosed);
		if (open) {
			this.currentImage = imagesOpen[0];
			this.imageName = rotateClosed[0];
		} else {
			this.currentImage = imagesClosed[0];
			this.imageName = rotateClosed[0];
		}
	}
}
