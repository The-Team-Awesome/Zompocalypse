package zompocalypse.gameworld.items;

import java.awt.Graphics;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Lockable;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.ui.rendering.ImageUtils;

/**
 * A container is a type of item which can hold other items, including other
 * containers. It represents the composite class from the composite pattern.
 *
 * @author Kieran Mckay, 300276166
 */
public class Container implements Item, Lockable {

	private static final long serialVersionUID = 1L;
	private int size;
	private boolean movable;
	private boolean locked;
	private boolean open;
	private String filename;

	private String name;
	private String description;

	protected transient ImageUtils imu = ImageUtils.getImageUtilsObject();

	protected transient ImageIcon[] imagesOpen;
	protected transient ImageIcon[] imagesClosed;
	protected transient ImageIcon currentImage;
	private String[] fileNamesOpen;
	private String[] fileNamesClosed;

	private List<Item> heldItems;
	private int uid;

	/**
	 * Requires a size for the amount of items it can hold. NOTE: if size is
	 * less than the amount of items passed in then: this.size == items.size()
	 * Requires a boolean to determine if the container is movable or not.
	 *
	 * @param fileNames
	 * @param offset
	 * @param size
	 * @param movable
	 * @param locked
	 * @param uid
	 */
	public Container(String[] fileNames, int size, String name,
			String description, boolean movable, boolean locked, boolean open,
			int uid) {
		fileNamesClosed = fileNames;
		this.filename = fileNames[0];
		imagesClosed = imu.setupImages(fileNames);

		fileNamesOpen = new String[fileNames.length];

		for (int i = 0; i < fileNames.length; i++) {
			fileNamesOpen[i] = fileNames[i].replace("closed", "open");
		}

		imagesOpen = imu.setupImages(fileNamesOpen);

		this.name = name;
		this.description = description;
		this.size = size;
		this.movable = movable;
		this.locked = locked;
		this.open = open;
		this.heldItems = new ArrayList<Item>();
		this.uid = uid;
	}

	@Override
	public void use(Player player) {

	}

	@Override
	public boolean movable() {
		return movable;
	}

	public List<Item> getHeldItems() {
		return heldItems;
	}

	@Override
	public boolean occupiable() {
		// It makes sense if you cant move an item,
		// you also can't occupy the same floor space (too big)
		return movable;
	}

	/**
	 * Whether or not there is any more room available in this container.
	 *
	 * @return true if this container is full, false if there is more room
	 */
	public boolean isFull() {
		return size == heldItems.size();
	}

	/**
	 * Returns a boolean determining whether or not an item exists at a given
	 * index in this container.
	 *
	 * @param index
	 *            - position in the containiner to find item
	 * @return True if item exists at index, False if no item at index
	 */
	public boolean hasItemAtIndex(int index) {
		if (index < heldItems.size()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the item at a given index in this container. The item is removed
	 * from the container.
	 *
	 * @param index
	 *            - the position at which to retrieve an item from.
	 * @return the item at the given index of this container.
	 */
	public Item getItemAtIndex(int index) {
		if (index >= heldItems.size()) {
			throw new InvalidParameterException(
					"Trying to retrieve Item from Container that does not exist.");
		}

		Item item = heldItems.remove(index);

		return item;
	}

	/**
	 * Used to check if this container has an item. If it contains the item, it
	 * will return the index of the first occurrence of the item If it does not
	 * contain it, it will return -1.
	 *
	 * @param item
	 *            - an item to see if this container is holding
	 * @return index of the first occurrence of item if it is contained, or -1
	 *         if it is not contained
	 */
	public int hasItem(Item item) {
		for (int i = 0; i < heldItems.size(); i++) {
			Item current = heldItems.get(i);
			if (current.equals(item)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Attempt to add an item to this container.
	 *
	 * @param item
	 *            - the item to add to the container
	 * @return true if add successful, false if unsuccessful
	 */
	public boolean add(Item item) {
		if (isFull()) {
			return false;
		}
		heldItems.add(item);
		return true;
	}

	/**
	 * Get the maximum capacity of this container
	 *
	 * @return integer - how many items this container can store
	 */
	public int getSize() {
		return size;
	}

	@Override
	public String getFileName() {
		return filename;
	}

	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		if (imu == null) {
			imu = ImageUtils.getImageUtilsObject();
		}

		imagesClosed = imu.setupImages(fileNamesClosed);
		imagesOpen = imu.setupImages(fileNamesOpen);

		if (open) {
			currentImage = imu.getImageForOrientation(worldOrientation,
					imagesOpen);
		} else {
			currentImage = imu.getImageForOrientation(worldOrientation,
					imagesClosed);
		}
		g.drawImage(currentImage.getImage(), x, y, null);
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
	public boolean isLocked() {
		return locked;
	}

	@Override
	public boolean unlock(boolean hasKey) {
		if (locked && hasKey) {
			locked = false;
			return true;
		}
		return false;
	}

	@Override
	public String examine() {
		return description;
	}

	/**
	 * Returns if the container is open.
	 * @return true if it's opened, false otherwise.
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * Returns the name of this object as a String
	 */
	public String getName() {
		return name;
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
			this.filename = rotateClosed[0];
		} else {
			this.currentImage = imagesClosed[0];
			this.filename = rotateClosed[0];
		}
	}

	/**
	 * Returns the object and information about it represented as a String
	 */
	@Override
	public String toString() {
		return "Container [size=" + size + ", movable=" + movable + ", locked="
				+ locked + ", open=" + open + ", name=" + name
				+ ", description=" + description + ", heldItems=" + heldItems
				+ "]";
	}
}
