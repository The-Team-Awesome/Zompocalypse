package zompocalypse.gameworld.world;

import java.awt.Graphics;
import java.util.Map;

import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.items.Item;

/**
 * A door in the game world.
 * Doors can be locked or unlocked, if they are locked, they require a key to open.
 * A door must be open for a character to walk through it.
 *
 * Implements item, and represents a leaf in the composite pattern.
 *
 * @author Kieran Mckay, 300276166
 */
public class Door implements Item{

	private int x;
	private int y;

	private String fileName;

	private boolean open;
	private boolean locked;
	private boolean occupiable;
	private int uid;

	public Door(int x, int y, String fileName, boolean locked, int uid) {
		this.x = x;
		this.y = y;
		this.fileName = fileName;
		this.open = false;
		this.occupiable = false;
		this.locked = locked;
		this.uid = uid;
	}

	@Override
	public void use(Player player) {
		if(locked){
			return;
		}
		open = !open;
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
		return fileName;
	}

	@Override
	public String getCSVCode(Map<String, String> textTileMap) {
		return "2";

	}

	public void setOccupiable(boolean bool) {
		occupiable = bool;
	}

	@Override
	public void draw(int x, int y, Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getUniqueID() {
		return uid;
	}
}
