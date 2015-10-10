package zompocalypse.gameworld.characters;

import java.awt.Graphics;

import zompocalypse.gameworld.world.World;

public interface Strategy {

	public ActorType type();

	public int speed();

	public void tick(World game, StrategyZombie Zombie);

	public void draw(Graphics g, StrategyZombie zombie);

}
