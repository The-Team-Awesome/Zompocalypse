package gameWorld.characters;

import gameWorld.world.World;

import java.awt.Graphics;

public interface Strategy {

	public int type();

	public int speed();

	public void tick(World game, StrategyZombie ghost);

	public void draw(Graphics g, StrategyZombie ghost);

}
