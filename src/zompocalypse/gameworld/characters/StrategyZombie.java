package zompocalypse.gameworld.characters;

import java.awt.Graphics;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.world.World;

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

	public StrategyZombie(World game, int realX, int realY, Strategy strategy, int uid) {
		super(game, realX, realY, Orientation.NORTH);
		this.strategy = strategy;
		this.uid = uid;

		this.health = ZOMBIE_HEALTH;
		this.speed = ZOMBIE_SPEED;
		this.strength = ZOMBIE_STRENGTH;
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
		this.direction = queued;
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(GameObject o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
