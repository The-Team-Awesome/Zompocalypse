package zompocalypse.gameworld.characters;

import java.awt.Graphics;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.world.World;

public class StrategyZombie extends MovingCharacter {

	private Strategy strategy;

	public StrategyZombie(World game, int realX, int realY, Strategy strategy) {
		super(game, realX, realY, Orientation.NORTH);
		this.strategy = strategy;
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
