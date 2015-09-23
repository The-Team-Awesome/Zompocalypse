package gameWorld;

public interface Item {

	public void use(Player player);

	public boolean canMove();

	/**
	 * Returns a CSV representation of this Item that can be saved to CSV document
	 */
	public String getCSVCode();
}