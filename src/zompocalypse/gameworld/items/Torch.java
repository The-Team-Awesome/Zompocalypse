package zompocalypse.gameworld.items;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.Player;

public class Torch implements Item {

	private transient Image currentImage;
	private String filename;
	private int uid;

	public Torch(String filename, int uid) {
		this.filename = filename;
		currentImage = Loader.LoadSprite(filename);
		this.uid = uid;
	}

	@Override
	public String getFileName() {
		return filename;
	}

	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		g.drawImage(currentImage, x+10, y, null);

	}

	@Override
	public int compareTo(GameObject o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void use(Player player) {
		// TODO Auto-generated method stub

	}
	
	public boolean occupiable(){
		return true;
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
		return "A torch. You could use this to light the way!";
	}

}
