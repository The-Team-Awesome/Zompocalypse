package gameWorld;

import java.awt.Graphics;

public class Door implements Item, Tile, Drawable{

	private int x;
	private int y;

	private String fileName;

	private boolean open;
	private boolean locked;

	public Door(int x, int y, String fileName, boolean locked) {
		this.x = x;
		this.y = y;
		this.fileName = fileName;
		this.open = false;
		this.locked = locked;
	}

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
	public boolean canMove() {
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
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}
}
