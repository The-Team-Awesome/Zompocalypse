package zompocalypse.gameworld.items;

import java.awt.Graphics;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zompocalypse.gameworld.Drawable;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.characters.Player;

/**
 * A container is a type of item which can hold other items, including other containers.
 * It represents the composite class from the composite pattern.
 *
 * @author Kieran Mckay, 300276166
 */
public class Container implements GameObject, Drawable{
	private int size;
	private boolean movable;
	private String filename;
	private List<Item> heldItems;

	/**
	 * Constructor for container with no items in it.
	 * Requires a size for the amount of items it can hold.
	 * Requires a boolean to determine if the container is movable or not.
	 */
	public Container(int size, boolean movable, String filename){
		this.size = size;
		this.movable = movable;
		this.filename = filename;
		this.heldItems = new ArrayList<Item>();
	}

	/**
	 * Constructor for container which starts with a list of items.
	 * Requires a size for the amount of items it can hold.
	 * NOTE: if size is less than the amount of items passed in then: this.size == items.size()
	 * Requires a boolean to determine if the container is movable or not.
	 */
	public Container(int size, boolean movable, List<Item> items){
		this.size = size;
		this.movable = movable;
		this.heldItems = items;

		if (this.size < heldItems.size()){
			this.size = heldItems.size();
		}
	}

	public void use(Player player){
		open(player);
	}

	public boolean movable() {
		return movable;
	}

	private void open(Player player){
		//TODO open and display contents to user probably through a new window
	}

	/**
	 * Whether or not there is any more room available in this container.
	 *
	 * @return true if this container is full, false if there is more room
	 */
	public boolean isFull(){
		return size == heldItems.size();
	}

	/**
	 * Returns a boolean determining whether or not an item exists at a given index in this container.
	 *
	 * @param index - position in the containiner to find item
	 * @return True if item exists at index, False if no item at index
	 */
	public boolean hasItemAtIndex(int index){
		if (index < heldItems.size()){
			return true;
		}
		return false;
	}

	/**
	 * Returns the item at a given index in this container.
	 * The item is removed from the container.
	 * @param index - the position at which to retrieve an item from.
	 * @return the item at the given index of this container.
	 */
	public Item getItemAtIndex(int index){
		if (index >= heldItems.size()){
			throw new InvalidParameterException("Trying to retrieve Item from Container that does not exist.");
		}

		Item item = heldItems.remove(index);

		return item;
	}

	/**
	 * Used to check if this container has an item.
	 * If it contains the item, it will return the index of the first occurrence of the item
	 * If it does not contain it, it will return -1.
	 *
	 * @param item - an item to see if this container is holding
	 * @return index of the first occurrence of item if it is contained, or -1 if it is not contained
	 */
	public int hasItem(Item item){
		for (int i = 0; i < heldItems.size(); i++){
			Item current = heldItems.get(i);
			if (current.equals(item)){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Attempt to add an item to this container.
	 *
	 * @param item - the item to add to the container
	 * @return true if add successful, false if unsuccessful
	 */
	public boolean add(Item item){
		if(isFull()){
			return false;
		}
		heldItems.add(item);
		return true;
	}

	/**
	 * Get the maximum capacity of this container
	 * @return integer - how many items this container can store
	 */
	public int getSize(){
		return size;
	}

	@Override
	public String getFileName() {
		return filename;
	}

	public String getCSVCode(Map<String, String> textTileMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(int x, int y, Graphics g) {
		// TODO Auto-generated method stub

	}

	public int getUniqueID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
