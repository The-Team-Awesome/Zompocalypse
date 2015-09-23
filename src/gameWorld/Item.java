package gameWorld;

import java.awt.Graphics;

public interface Item {

	public void use(Player player);

	public boolean canMove();

	/**
	 * Returns a CSV representation of this Item that can be saved to CSV document
	 */
	public String getCSVCode();

	public void draw(int x, int y, Graphics g);
}