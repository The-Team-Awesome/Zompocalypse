package gameWorld.characters;

import gameWorld.Orientation;
import gameWorld.world.World;

import java.awt.Graphics;

public class StrategyZombie extends MovingCharacter {

	private Strategy strategy;

	public StrategyZombie(int realX, int realY, Strategy strategy) {
		super(realX, realY, Orientation.NORTH);
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
		this.orientation = queued;
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(int x, int y, Graphics g) {
		// TODO Auto-generated method stub

	}
}
