package zompocalypse.gameworld.characters;

import java.util.Map;

import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.world.World;

/**
 * A strategy pattern implementation.
 * This strategy finds a target Player and continuously moves towards them.
 * 
 * @author Kieran Mckay, 300276166
 */
public class HomerStrategy implements Strategy {
	
	//the amount of game ticks before this pattern can act
	private final static int WAIT_TIME = 10;
	//damage this strategy adds to zombies base damage
	private final int DAMAGE = 0;
	//speed at which wait time is decremented to act
	private final int SPEED = 10;
	private static int moveTimeCounter = WAIT_TIME;
	//the target of this zombie
	private Player target;
	//the amount of points this zombie is worth
	private static final int POINTS = 30;
	
	@Override
	public ActorType type() {
		return ActorType.HOMERZOMBIE;
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
		//if we still have to wait before acting then decrement wait counter and return
		if(moveTimeCounter > 0){
			moveTimeCounter -= speed();
			return;
		}
		updateTarget(game, zombie);
		
		//move in appropriate direction
		Orientation direction = getPreferedDirection(game, zombie); 
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
		//attack a player if we can
		zombie.attack();
		
		//reset the timer before zombie can act again
		moveTimeCounter = WAIT_TIME;
	}
	
	/**
	 * Finds the location of the closest Player and targets it.
	 * 
	 * @param game - the game world acting upon
	 * @param zombie - the StrategyZombie implementing this Strategy
	 */
	private void updateTarget(World game, StrategyZombie zombie){
		
		Map<Integer, Actor> characters = game.getIdToActor();
		for(Actor a : characters.values()){
			if(a instanceof Player){
				if(target == null){
					target = (Player)a;
				}else{
					Player player = (Player) a;
					int xdifPlayer = Math.abs(zombie.getX() - player.getX());
					int ydifPlayer = Math.abs(zombie.getY() - player.getY());
					int playerDist = xdifPlayer + ydifPlayer;					

					int xdifTarget = Math.abs(zombie.getX() - target.getX());
					int ydifTarget = Math.abs(zombie.getY() - target.getY());
					int targetDist = xdifTarget + ydifTarget;
					if (playerDist < targetDist){
						target = player;
					}
				}
			}
		}
	}
	
	/**
	 * Finds the direction for this StrategyZombie to move in towards its target
	 * 
	 * @param game - the world upon which we are acting
	 * @param zombie - the StrategyZombie implementing this strategy 
	 * @return Orientation, direction in which this StrategyZombie wants to move to
	 */
	private Orientation getPreferedDirection(World game, StrategyZombie zombie){	
		
		int xdist;
		int ydist;
		
		if(target != null) {
			xdist = zombie.getX() - target.getX();
			ydist = zombie.getY() - target.getY();
		} else {
			xdist = 1;
			ydist = 1;
		}
		
		//try make some smart choices before defaulting
		if(Math.abs(xdist) > Math.abs(ydist)){
			//check x axis first
			if (xdist >= 0){
				if (!game.isBlocked(zombie.getX()-1, zombie.getY())){
					return Orientation.WEST;					
				}
			}else {
				if (!game.isBlocked(zombie.getX()+1, zombie.getY())){
					return Orientation.EAST;					
				}
			}
			if (ydist >= 0){
				if (!game.isBlocked(zombie.getX(), zombie.getY()-1)){
					return Orientation.NORTH;					
				}
			}else {
				if (!game.isBlocked(zombie.getX(), zombie.getY()+1)){
					return Orientation.SOUTH;					
				}
			}
			//dumb choice time
		}else {

			if (ydist >= 0){
				if (!game.isBlocked(zombie.getX(), zombie.getY()-1)){
					return Orientation.NORTH;					
				}
			}else {
				if (!game.isBlocked(zombie.getX(), zombie.getY()+1)){
					return Orientation.SOUTH;					
				}
			}
			if (xdist >= 0){
				if (!game.isBlocked(zombie.getX()-1, zombie.getY())){
					return Orientation.WEST;					
				}
			}else {
				if (!game.isBlocked(zombie.getX()+1, zombie.getY())){
					return Orientation.EAST;					
				}
			}
			//dumb choice time
		}

		//if we are reaching this point our two preferred directions are unavailable
		//future implementations may be smarter but for now zombie just turns around 180degrees
		return Orientation.getNext(Orientation.getNext(zombie.getOrientation()));
	}
}
