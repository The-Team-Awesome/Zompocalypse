package gameWorld;

import java.awt.Graphics;

public class Wall implements Tile, Drawable{

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean occupiable() {
		return false;
	}

	@Override
	public char getCode() {
		return '1';
	}

}
