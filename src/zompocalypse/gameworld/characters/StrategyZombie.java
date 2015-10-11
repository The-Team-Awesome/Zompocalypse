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

	private final int OFFSETY = -20;

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

	@Override
	public void draw(int realx, int realy, Graphics g,
			Orientation worldOrientation) {
		super.draw(realx, realy, OFFSETY, g, worldOrientation);
	}

	@Override
	public int compareTo(GameObject o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean occupiable() {
		// consider changing to false if we want zombies to be able to block
		return true;
	}
}
