package gameWorld;

public class Floor implements Tile{

	private int x;
	private int y;
	private String filename;
	private Item myItem;

	public Floor(int x, int y, String filename, Item myItem) {
		this.x = x;
		this.y = y;
		this.filename = filename;
		this.myItem = myItem;
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
	public String getFileName() {
		return filename;
	}

	@Override
	public boolean occupiable() {
		return true;
	}

}
