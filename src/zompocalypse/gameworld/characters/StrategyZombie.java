package zompocalypse.gameworld.characters;

import java.awt.Graphics;
import java.util.PriorityQueue;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.world.World;

public class StrategyZombie extends MovingCharacter {

	private static final long serialVersionUID = 1L;
	//the base stats of a zombie
	private final int ZOMBIE_HEALTH = 100;
	private final int ZOMBIE_SPEED = 5;
	private final int ZOMBIE_STRENGTH = 20;
	private final int BASE_ATTACK = 10;

	//offset for drawing a character
	private final int OFFSETY = -20;

	//this zombies implemented strategy (strategy design pattern)
	private Strategy strategy;

	public StrategyZombie(World game, int realX, int realY, Strategy strategy, int uid, String[] filenames) {
		super(uid, game, realX, realY, Orientation.NORTH, filenames);
		this.strategy = strategy;
		this.uid = uid;

		setHealth(ZOMBIE_HEALTH);
		setSpeed(ZOMBIE_SPEED);
		setStrength(ZOMBIE_STRENGTH);
	}

	@Override
	public void tick(World game) {
		super.tick(game);
		strategy.tick(game, this);
	}

	protected void attack(){
		PriorityQueue<GameObject> targets = getObjectsInfront();
		for (GameObject o : targets){
			if (o instanceof Player){
				((Player) o).damaged(calculateDamage());
			}
		}
	}

	/**
	 * Calculates how much damage this zombie does.
	 * Based on zombie base damage + any extra damage granted from strategy.
	 *
	 * @return int - the amount of damage this zombie inflicts
	 */
	private int calculateDamage(){
		return BASE_ATTACK + strategy.getDamage();
	}

	@Override
	public void draw(int realx, int realy, Graphics g,
			Orientation worldOrientation) {
		super.draw(realx, realy, OFFSETY, g, worldOrientation);
	}

	/**
	 * The score awarded to players for killing this zombie.
	 *
	 * @return - int score for killing
	 */
	public int getPoints(){
		return strategy.getPoints();
	}

	@Override
	public int compareTo(GameObject o) {
		return 0;
	}

	@Override
	public boolean occupiable() {
		// consider changing to false if we want zombies to be able to block
		return true;
	}
}
