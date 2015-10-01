package zompocalypse.gameworld.characters;

import java.awt.Graphics;

import zompocalypse.gameworld.world.World;

public interface Strategy {

	public int type();

	public int speed();

	public void tick(World game, StrategyZombie ghost);

	public void draw(Graphics g, StrategyZombie ghost);

}
