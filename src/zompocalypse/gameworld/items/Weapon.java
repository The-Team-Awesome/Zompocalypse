package zompocalypse.gameworld.items;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.util.PriorityQueue;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.world.World;

/** Weapons are used to damage enemies.
 *  The damage dealt to each enemy is a modifier based on the specific
 *  players strength, in addition to the type of Weapon they are holding.
 *
 * @author Sam Costigan, Kieran McKay
 *
 */
public class Weapon implements Item {

	private static final long serialVersionUID = 1L;
	private transient Image currentImage;
	private String filename;
	private int uid;

	private int strength;
	private String description;
	private final int OFFSET_X = -10;

	public Weapon(String filename, String description, int uid, int strength) {
		this.filename = filename;
		currentImage = Loader.LoadSprite(filename);
		this.description = description;
		this.uid = uid;
		this.strength = strength;
	}

	public int getStrength() {
		return strength;
	}

	public Image getImage() {
		return currentImage;
	}

	@Override
	public String getFileName() {
		return filename;
	}

	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		g.drawImage(currentImage, x+OFFSET_X, y, null);
	}

	@Override
	public int compareTo(GameObject o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean occupiable(){
		return true;
	}

	@Override
	public void use(Player player) {
		List<Item> inventory = player.getInventory();

		if(!inventory.contains(this)) {
			player.pickUp(this);
			World world = player.getWorld();
			PriorityQueue<GameObject>[][] objects = world.getObjects();

			// TODO: This would be much nicer if objects could be retrieved from a map of ids to GameObjects
			for(int x = 0; x < objects.length; x++) {
				for(int y = 0; y < objects[0].length; y++) {
					for(GameObject object : objects[x][y]) {
						if(object.equals(this)) {
							objects[x][y].remove(object);
						}
					}
				}
			}
		} else {
			if(player.getEquipped() != null) {
				player.pickUp(player.getEquipped());
			}

			player.setEquipped(this);
			inventory.remove(this);
		}
	}

	@Override
	public boolean movable() {
		return true;
	}

	@Override
	public int getUniqueID() {
		return uid;
	}

	@Override
	public String examine() {
		return description;
	}
}
