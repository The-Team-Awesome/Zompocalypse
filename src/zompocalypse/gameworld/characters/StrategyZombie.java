package zompocalypse.gameworld.characters;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import zompocalypse.gameworld.Direction;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.world.World;
import zompocalypse.ui.rendering.ImageUtils;

public class StrategyZombie extends MovingCharacter {

	private final int ZOMBIE_HEALTH = 100;
	private final int ZOMBIE_SPEED = 5;
	private final int ZOMBIE_STRENGTH = 20;
	private final int BASE_ATTACK = 10;

	private final int uid;
	private int health;
	private int speed;
	private int strength;

	private Strategy strategy;
	private Orientation orientation = Orientation.NORTH;

	private String[] filenames = { "npc_zombie_n.png",
			"npc_zombie_e.png", "npc_zombie_s.png",
			"npc_zombie_w.png" };

	private ImageIcon[] images;
	private String imageName;
	private ImageIcon currentImage;

	public StrategyZombie(World game, int realX, int realY, Strategy strategy, int uid) {
		super(game, realX, realY, Orientation.NORTH);
		this.strategy = strategy;
		this.uid = uid;

		this.health = ZOMBIE_HEALTH;
		this.speed = ZOMBIE_SPEED;
		this.strength = ZOMBIE_STRENGTH;

		ImageUtils imu = ImageUtils.getImageUtilsObject();
		this.images = imu.setupImages(filenames);
		this.currentImage = images[0];
		this.imageName = filenames[0];
	}

	public void damaged(int damage) {
		health = health - damage;
	}

	@Override
	public int speed() {
		return strategy.speed();
	}

	@Override
	public void tick(World game) {
		super.tick(game);
		strategy.tick(game, this);
	}

	public void setQueued(Orientation queued) {
		this.ori = queued;
	}

	@Override
	public String getFileName() {
		return this.imageName;
	}

	public void draw(int realx, int realy, Graphics g,
			Orientation worldOrientation) {
		ImageUtils imu = ImageUtils.getImageUtilsObject();
		Orientation ord = Orientation.getCharacterOrientation(queued, worldOrientation);
		currentImage = imu.getCurrentImageForOrientation(ord,
				images);

		g.drawImage(currentImage.getImage(), realx, realy - 20, null);
	}

	@Override
	public int compareTo(GameObject o) {
		// TODO Auto-generated method stub
		return 0;
	}


	public Orientation getCurrentOrientation() {
		return orientation;
	}

	public void rotatePerspective(Direction value) {
		switch (value) {

		case CLOCKWISE:
			updateCurrentOrientationClockwise();
			return;
		case ANTICLOCKWISE:
			updateCurrentOrientationAntiClockwise();
			return;
		default:
			throw new IllegalArgumentException(
					"Direction wasn't clockwise or anticlockwise");
		}
	}
	/**
	 * Updates the current orientation of the viewer to its clockwise
	 * counterpart.
	 */
	private void updateCurrentOrientationClockwise() {
		switch (orientation) {
		case NORTH:
			orientation = Orientation.EAST;
			return;
		case SOUTH:
			orientation = Orientation.WEST;
			return;
		case EAST:
			orientation = Orientation.SOUTH;
			return;
		case WEST:
			orientation = Orientation.NORTH;
			return;
		default:
			throw new IllegalArgumentException(
					"Current orientation is incorrect");
		}
	}

	/**
	 * Updates the current orientation of the viewer to its anticlockwise
	 * counterpart.
	 */
	private void updateCurrentOrientationAntiClockwise() {
		switch (orientation) {
		case NORTH:
			orientation = Orientation.WEST;
			return;
		case SOUTH:
			orientation = Orientation.EAST;
			return;
		case EAST:
			orientation = Orientation.NORTH;
			return;
		case WEST:
			orientation = Orientation.SOUTH;
			return;
		default:
			throw new IllegalArgumentException(
					"Current orientation is incorrect");
		}
	}
}
