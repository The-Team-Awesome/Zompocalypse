package userInterface.renderWindow;

import java.util.Comparator;

/**
 * Contains objects in the game.
 * Required to render - the texture (image)
 * and the xyz of the object.
 *
 * @author Pauline Kelly
 *
 */
public class GameObject {

	private int [] coordinates = new int [3];  //xyz in that order

	/*The height and width of the objects are represented as a percentage.
	 * When the character moves away from the object, it recalculates the
	 * positioning of the object, as well as recalculating the height and
	 * width.
	 */

	private int currentHeight = 100;
	private int currentWidth = 100;

	public GameObject(int x, int y, int z){
		this.coordinates[0] = x;
		this.coordinates[1] = y;
		this.coordinates[2] = z;
	}

	private int getX(){
		return coordinates[0];
	}

	private int getY(){
		return coordinates[1];
	}

	private int getZ(){
		return coordinates[2];
	}

	private void setX(int x){
		this.coordinates[0] = x;
	}

	private void setY(int y){
		this.coordinates[1] = y;
	}

	private void setZ(int z){
		this.coordinates[2] = z;
	}

	private static class GameObjectComparator implements Comparator<GameObject>{
		public int compare(GameObject o1, GameObject o2) {
            return (o1.getZ() - o2.getZ());
        }
	}
}
