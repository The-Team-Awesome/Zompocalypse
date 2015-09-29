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
		int homeDistance = 50;
		/*
		for(Character c : game.characters()) {
			if(c instanceof Player && !((Player)c).isDead()) {
				int deltaX = Math.abs(c.getX() - getX());
				int deltaY = Math.abs(c.getY() - getY());
				double distance = Math.sqrt((deltaX*deltaX) + (deltaY*deltaY));

				if(distance < homeDistance) {
					strategy = new RandomStrategy();
				} else {
					strategy = new HomerStrategy();
				}
			}
		}
		*/
		super.tick(game);

		strategy.tick(game, this);
	}

	public void setQueued(Orientation queued) {
		this.queued = queued;
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
