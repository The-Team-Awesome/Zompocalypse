package gameWorld;

import java.awt.Graphics;
import java.awt.Image;

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
	public String getCSVCode() {
		return "1";
	}

	@Override
	public void setOccupiable(boolean bool) {
	}

	@Override
	public void draw(int x, int y, Graphics g) {
		// TODO Auto-generated method stub

	}
}
