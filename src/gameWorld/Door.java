package gameWorld;

import java.awt.Graphics;
import java.util.Map;


public class Door extends Wall implements Item {

	private int x;
	private int y;

	private String fileName;

	private boolean open;
	private boolean locked;
	private boolean occupiable;

	public Door(int x, int y, String fileName, boolean locked) {
		this.x = x;
		this.y = y;
		this.fileName = fileName;
		this.open = false;
		this.occupiable = false;
		this.locked = locked;

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

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public boolean occupiable() {
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

	@Override
	public void setOccupiable(boolean bool) {
		occupiable = bool;
	}

	@Override
	public void draw(int x, int y, Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getUniqueID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
