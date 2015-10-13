package zompocalypse.gameworld.characters;

import java.util.Map;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.world.World;

public class HomerStrategy implements Strategy {
	private final static int WAIT_TIME = 10;
	private final int SPEED = 10;
	private static int moveTimeCounter = WAIT_TIME;
	private Player target;

	public static final int POINTS = 30;
	
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

	@Override
	public void tick(World game, StrategyZombie zombie) {
		if(moveTimeCounter > 0){
			moveTimeCounter -= speed();
			return;
		}
		updateTarget(game, zombie);
		
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
		moveTimeCounter = WAIT_TIME;
	}
	
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
	
	/*
	@Override
	public void draw(Graphics g, StrategyZombie ghost) {
		// TODO Auto-generated method stub

	}
	*/
	/*

	@Override
	public void tick(World game, StrategyZombie zombie) {
		// check whether we are at an intersection.
		if (zombie.direction() == MovingCharacter.DOWN
				|| zombie.direction() == MovingCharacter.UP) {
			// ok, moving in up/down direction
			if (!game.canMoveLeft(zombie) && !game.canMoveRight(zombie)) {
				return; // no horizontal movement possible
			}
		} else if (zombie.direction() == MovingCharacter.RIGHT
				|| zombie.direction() == MovingCharacter.LEFT) {
			// ok, moving in left/right direction
			if (!game.canMoveUp(zombie) && !game.canMoveDown(zombie)) {
				return; // no horizontal movement possible
			}
		}

		// yes, we're at an intersection. Now, flip a coin to see if we're
		// really homing or going to move randomly. This is kinda important, as
		// otherwise having multiple homing ghosts just means they all act in
		// exactly the same manner.
		if(random.nextInt(10) > 7) {
			zombie.setQueued(random.nextInt(4)+1); // don't stop
			return;
		}

		double targetDistance = 10000;
		int targetDeltaX=-1;
		int targetDeltaY=-1;

		// home in on target
		synchronized(game) {
			for(Character c : game.characters()) {
				if(c instanceof Pacman && !((Pacman)c).isDead()) {
					// potential target
					int deltaX = Math.abs(c.realX() - ghost.realX());
					int deltaY = Math.abs(c.realY() - ghost.realY());
					double distance = Math.sqrt((deltaX*deltaX) + (deltaY*deltaY));
					if(distance < targetDistance) {
						targetDeltaX = c.realX() - ghost.realX();
						targetDeltaY = c.realY() - ghost.realY();
						targetDistance = distance;
					}
				}
			}
		}
		
		if(targetDeltaX != -1) {
			int deltaX = Math.abs(targetDeltaX);
			int deltaY = Math.abs(targetDeltaY);
			if(deltaX < deltaY) {
				// prefer to move north-south
				if(targetDeltaY < 0) {
					tryMoveUp(targetDeltaX < 0, game, zombie);
				} else {
					tryMoveDown(targetDeltaX < 0, game, zombie);
				}
			} else {
				// prefer to move east-west
				if(targetDeltaX < 0) {
					tryMoveLeft(targetDeltaY < 0, game, zombie);
				} else {
					tryMoveRight(targetDeltaY < 0, game, zombie);
				}
			}
		}
	}
	*/
	/*
	private void tryMoveUp(boolean preferLeft, World game, StrategyZombie zombie) {
		if(game.canMoveUp(zombie)) {
			zombie.moveUp();
		} else if(preferLeft && game.canMoveLeft(zombie)) {
			zombie.moveLeft();
		} else if(!preferLeft && game.canMoveRight(zombie)) {
			zombie.moveRight();
		} else if(game.canMoveRight(zombie)) {
			zombie.moveRight();
		} else if(game.canMoveLeft(zombie)) {
			zombie.moveLeft();
		} else {
			zombie.moveDown(); // last resort
		}
	}

	private void tryMoveDown(boolean preferLeft, World game, StrategyZombie zombie) {
		if(game.canMoveDown(zombie)) {
			zombie.moveDown();
		} else if(preferLeft && game.canMoveLeft(zombie)) {
			zombie.moveLeft();
		} else if(!preferLeft && game.canMoveRight(zombie)) {
			zombie.moveRight();
		} else if(preferLeft && game.canMoveRight(zombie)) {
			zombie.moveRight();
		} else if(!preferLeft && game.canMoveLeft(zombie)) {
			zombie.moveLeft();
		} else {
			zombie.moveUp(); // last resort
		}
	}

	private void tryMoveLeft(boolean preferUp, World game, StrategyZombie zombie) {
		if(game.canMoveLeft(zombie)) {
			zombie.moveLeft();
		} else if(preferUp && game.canMoveUp(zombie)) {
			zombie.moveUp();
		} else if(!preferUp && game.canMoveDown(zombie)) {
			zombie.moveDown();
		} else if(game.canMoveUp(zombie)) {
			zombie.moveUp();
		} else if(game.canMoveDown(zombie)) {
			zombie.moveDown();
		} else {
			zombie.moveRight(); // last resort
		}
	}

	private void tryMoveRight(boolean preferUp, World game, StrategyZombie zombie) {
		if(game.canMoveRight(zombie)) {
			zombie.moveRight();
		} else if(preferUp && game.canMoveUp(zombie)) {
			zombie.moveUp();
		} else if(!preferUp && game.canMoveDown(zombie)) {
			zombie.moveDown();
		} else if(game.canMoveUp(zombie)) {
			zombie.moveUp();
		} else if(game.canMoveDown(zombie)) {
			zombie.moveDown();
		} else {
			zombie.moveLeft(); // last resort
		}
	}
	*/
}
