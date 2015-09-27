package tests.logic;

import static org.junit.Assert.*;
import gameWorld.characters.HomerStrategy;
import gameWorld.characters.StrategyZombie;
import gameWorld.world.World;

import java.io.IOException;

import org.junit.Test;

import dataStorage.Loader;
import dataStorage.Parser;

public class ZombieTests {

	World game;
	
	public ZombieTests() {
		try {
			/** 
			 * TODO: Ideally, we should create a Test map file that can be loaded in here. That
			 * file would have an instance of each object to test within the smallest possible space.
			 */
			game = Parser.ParseMap(Loader.mapFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A zombie in the middle of the map should be able to move around and get to
	 * other nearby squares successfully.
	 */
	
	@Test public void zombieValidMove1() {
		StrategyZombie z = new StrategyZombie(3, 3, new HomerStrategy());
		z.moveUp();
		
		z.tick(game);
		
		assertTrue(z.getX() == 3);
		assertTrue(z.getY() == 2);
	}
	
	@Test public void zombieValidMove2() {
		StrategyZombie z = new StrategyZombie(5, 5, new HomerStrategy());
		// This sequence should leave the zombie at 4, 4
		z.moveUp();
		z.moveLeft();
		z.moveUp();
		z.moveDown();
		
		z.tick(game);
		
		assertTrue(z.getX() == 4);
		assertTrue(z.getY() == 4);
	}
	
	/**
	 * A zombie at the edge of the map shouldn't be able to move past the edge.
	 */
	
	@Test public void zombieInvalidMove1() {
		StrategyZombie z = new StrategyZombie(0, 0, new HomerStrategy());
		z.moveUp();
		
		z.tick(game);
		
		assertTrue(z.getX() == 0);
		assertTrue(z.getY() == 0);
	}
	
	@Test public void zombieInvalidMove2() {
		StrategyZombie z = new StrategyZombie(0, 0, new HomerStrategy());
		// This sequence of movements should still leave the Zombie at the position 0,0
		z.moveLeft();
		z.moveUp();
		z.moveDown();
		z.moveUp();
		z.moveUp();
		
		z.tick(game);
		
		assertTrue(z.getX() == 0);
		assertTrue(z.getY() == 0);
	}
	
}
