package zompocalypse.gameworld.characters;

import java.util.Random;

import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.world.World;

public class RandomStrategy implements Strategy{
	private final static int WAIT_TIME = 10;
	private final int SPEED = 10;
	private static final Random random = new Random(System.currentTimeMillis());
	private static int moveTimeCounter = WAIT_TIME;	

	private static final int POINTS = 10;

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
		moveTimeCounter = WAIT_TIME;
	}
	/*
	@Override
	public void draw(Graphics g, StrategyZombie zombie) {
		// TODO Auto-generated method stub
	}
	*/
}
