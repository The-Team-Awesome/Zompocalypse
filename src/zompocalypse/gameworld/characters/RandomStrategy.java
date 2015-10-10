package zompocalypse.gameworld.characters;

import java.awt.Graphics;
import java.util.Random;

import zompocalypse.gameworld.world.World;

public class RandomStrategy implements Strategy{
	private static final Random random = new Random(System.currentTimeMillis());

	@Override
	public ActorType type() {
		return ActorType.RANDOMZOMBIE;
	}

	@Override
	public int speed() {
		return 1;
	}

	@Override
	public void tick(World game, StrategyZombie zombie) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g, StrategyZombie zombie) {
		// TODO Auto-generated method stub

	}

}
