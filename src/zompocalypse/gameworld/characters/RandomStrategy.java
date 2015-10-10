package zompocalypse.gameworld.characters;

import java.awt.Graphics;
import java.util.Random;

import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.world.World;

public class RandomStrategy implements Strategy{
	private final static int WAIT_TIME = 10;
	private final int SPEED = 1;
	private static final Random random = new Random(System.currentTimeMillis());
	private static int moveTimeCounter = WAIT_TIME;

	@Override
	public ActorType type() {
		return ActorType.RANDOMZOMBIE;
	}

	@Override
	public int speed() {
		return SPEED;
	}

	@Override
	public void tick(World game, StrategyZombie zombie) {
		Orientation current = zombie.ori;
		int choice = random.nextInt(3);

		for(int i = 0; i < choice; i++){
			current = Orientation.getNext(current);
		}
		zombie.setQueued(current);
	}

	@Override
	public void draw(Graphics g, StrategyZombie zombie) {
		// TODO Auto-generated method stub
	}
}
