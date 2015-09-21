package gameWorld;

import java.awt.Graphics;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Container implements Item, Drawable{
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
		open();
	}

	public boolean canMove() {
		return movable;
	}

	private void open(){
		//TODO open and display contents to user
	}

	public boolean isFull(){
		return size == heldItems.size();
	}

	public boolean hasItemAtIndex(int index){
		if (index < heldItems.size()){
			return true;
		}
		return false;
	}

	public Item getItemAtIndex(int index){
		if (index >= heldItems.size()){
			throw new InvalidParameterException("Trying to retrieve Item from Container that does not exist.");
		}

		Item item = heldItems.remove(index);

		return item;
	}

	public boolean hasItem(Item item){
		if (heldItems.contains(item)){
			return true;
		}
		return false;
	}

	/**
	 * Operates like a remove really.
	 */
	public Item getItem(Item item){
		if (!heldItems.contains(item)){
			throw new InvalidParameterException("Trying to retrieve Item from Container that does not exist.");
		}

		heldItems.remove(item);
		return item;
	}

	public boolean add(Item item){
		if(isFull()){
			return false;
		}
		heldItems.add(item);
		return true;
	}

	public int getSize(){
		return size;
	}

	@Override
	public String getFileName() {
		return filename;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
	}
}
