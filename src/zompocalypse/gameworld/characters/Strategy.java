package zompocalypse.gameworld.characters;

import java.io.Serializable;

import zompocalypse.gameworld.world.World;
/**
 * An interface representing an adaptable Strategy adopted by a StrategyZombie.
 * Used to implement the strategy design pattern.
 * 
 * @author Kieran Mckay, 300276166
 */
public interface Strategy extends Serializable {
	
	/**
	 * The type of actor this Strategy is an instance of
	 * 
	 * @return ActorType an enum of types of Actor
	 */
	public ActorType type();

	/**
	 * How fast the StrategyZombie implementing this strategy pattern can move
	 * 
	 * @return an int representing the Speed of the StrategyZombie
	 */
	public int speed();

	public void tick(World game, StrategyZombie Zombie);
	
	/**
	 * Points awarded to players for killing the StrategyZombie using this Strategy
	 * 
	 * @return int representing the amount of points this StrategyZombie is worth
	 */
	public int getPoints();
	
	/**
	 * Damage done to players by the StrategyZombie using this Strategy
	 * 
	 * @return int representing the amount of damage this StrategyZombie can inflict
	 */
	public int getDamage();
}
