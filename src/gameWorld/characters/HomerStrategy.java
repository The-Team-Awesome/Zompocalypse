package gameWorld.characters;

import gameWorld.world.World;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class HomerStrategy implements Strategy {

	@Override
	public int type() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int speed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void tick(World game, StrategyZombie ghost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g, StrategyZombie ghost) {
		// TODO Auto-generated method stub
		
	}
	/*
	private static final Random random = new Random(System.currentTimeMillis());

	@Override
	public int speed() {
		return 3;
	}

	@Override
	public int type() {
		return Character.HOMERZOMBIE;
	}

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

	@Override
	public void draw(Graphics g, StrategyZombie zombie) {
		switch(zombie.direction()) {
			case MovingCharacter.RIGHT:
				g.drawImage(HGHOST_RIGHT, zombie.realX(),zombie.realY(), null, null);
				break;
			case MovingCharacter.UP:
				g.drawImage(HGHOST_UP, zombie.realX(),zombie.realY(), null, null);
				break;
			case MovingCharacter.DOWN:
				g.drawImage(HGHOST_DOWN, zombie.realX(),zombie.realY(), null, null);
				break;
			case MovingCharacter.LEFT:
				g.drawImage(HGHOST_LEFT, zombie.realX(),zombie.realY(), null, null);
				break;
		}
	}

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
