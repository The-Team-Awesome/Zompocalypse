package zompocalypse.gameworld.characters;

import java.util.Random;

import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.world.World;

/**
 * A strategy pattern implementation.
 * This strategy will move randomly and attack any Players in their way.
 *
 * @author Kieran Mckay, 300276166
 */
public class RandomStrategy implements Strategy{

	private static final long serialVersionUID = 1L;
	//the amount of game ticks before this pattern can act
	private final static int WAIT_TIME = 10;
	//damage this strategy adds to zombies base damage
	private final int DAMAGE = 0;
	//speed at which wait time is decremented to act
	private final int SPEED = 10;
	private static int moveTimeCounter = WAIT_TIME;
	//the amount of points this zombie is worth
	private static final int POINTS = 10;
	//random object for determining a direction to move
	private static final Random random = new Random(System.currentTimeMillis());

	@Override
	public ActorType type() {
		return ActorType.RANDOMZOMBIE;
	}

	@Override
	public int speed() {
		return SPEED;
	}

	public int getPoints(){
		return POINTS;
	}

	public int getDamage(){
		return DAMAGE;
	}

	@Override
	public void tick(World game, StrategyZombie zombie) {
		if(moveTimeCounter > 0){
			moveTimeCounter -= speed();
			return;
		}
		Orientation direction = zombie.getOrientation();

		int choice = random.nextInt(3);
		for(int i = 0; i < choice; i++){
			direction = Orientation.getNext(direction);
		}

		switch (direction) {
		case NORTH:
			zombie.moveNorth();
			break;
		case EAST:
			zombie.moveEast();
			break;
		case SOUTH:
			zombie.moveSouth();
			break;
		case WEST:
			zombie.moveWest();
			break;
		}
		zombie.attack();
		moveTimeCounter = WAIT_TIME;
	}
}
