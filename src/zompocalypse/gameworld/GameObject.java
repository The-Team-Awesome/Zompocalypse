package zompocalypse.gameworld;

public interface GameObject extends Drawable, Comparable<GameObject> {
	public boolean occupiable();
}
