package zompocalypse.gameworld.characters;

import java.io.Serializable;

import zompocalypse.gameworld.world.World;

public interface Strategy extends Serializable {

	public ActorType type();

	public int speed();

	public void tick(World game, StrategyZombie Zombie);
	
	public int getPoints();
	
	public int getDamage();

	//public void draw(Graphics g, StrategyZombie zombie);

}
