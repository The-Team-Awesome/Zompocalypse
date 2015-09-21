package gameWorld;

import java.awt.Graphics;

public class Key implements Item, Drawable{
	private String filename;

	public Key(String filename) {
		this.filename = filename;
	}

	@Override
	public void use(Player player){

	}

	@Override
	public boolean canMove() {
		return true;
	}

	@Override
	public String getFileName() {
		return filename;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getCSVCode() {
		return "-K";
	}
}
