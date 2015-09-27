package tests.logic;

import static org.junit.Assert.*;

import java.io.IOException;

import gameWorld.characters.Player;
import gameWorld.items.Container;
import gameWorld.items.Key;
import gameWorld.world.Door;
import gameWorld.world.World;

import org.junit.Test;

import dataStorage.Loader;
import dataStorage.Parser;

public class PlayerTests {

	World game;
	
	public PlayerTests() {
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
	 * Tests that a Player is left with the expected number of points after having 5 points added.
	 */
	@Test public void playerValidPointsTest1() {
		Player p = generatePlayer(3, 3);
		p.addScore(5);
		
		assertTrue(p.score() == 5);
	}
	
	/**
	 * A Player starting at position 3, 3 should be able to move down to valid nearby squares.
	 */
	
	@Test public void playerValidMoveTest1() {
		// TODO: This test currently fails, which means movement is not correctly implemented!
		Player p = generatePlayer(3, 3);
		p.moveDown();
		
		p.tick(game);
		
		assertTrue(p.getX() == 3);
		assertTrue(p.getY() == 4);
	}
	
	@Test public void playerValidMoveTest2() {
		// TODO: This test currently fails, which means movement is not correctly implemented!
		Player p = generatePlayer(3, 3);
		p.moveDown();
		p.tick(game);
		
		p.moveLeft();
		
		p.tick(game);
		
		assertTrue(p.getX() == 2);
		assertTrue(p.getY() == 4);
	}
	
	/**
	 * A Player starting at the edge of the map shouldn't be able to move past the edge
	 */
	
	@Test public void playerInvalidMoveTest1() {
		Player p = generatePlayer(0, 0);
		p.moveUp();
		
		p.tick(game);
		
		assertTrue(p.getX() == 0);
		assertTrue(p.getY() == 0);
		
	}
	
	@Test public void playerInvalidMoveTest2() {
		Player p = generatePlayer(0, 5);
		p.moveLeft();
		
		p.tick(game);
		
		assertTrue(p.getY() == 5);
		assertTrue(p.getX() == 0);
	}
	
	@Test public void playerValidKeyTest1() {
		Player p = generatePlayer(0, 0);
		Key k = new Key("gold_key_inv.png");
		
		k.use(p);
		
		// TODO: At this point, it is expected that the key will be in the Players inventory.
		// Inventories aren't yet implemented, so can't be checked! But soon....
	}
	
	@Test public void playerValidDoorTest1() {
		Player p = generatePlayer(0, 0);
		Door d = new Door(0, 0, "test", false);
		
		d.use(p);
		
		assertTrue(d.occupiable());
	}
	
	@Test public void playerValidDoorTest2() {
		Player p = generatePlayer(0, 0);
		Door d = new Door(0, 0, "test", true);
		
		d.unlock(true);
		d.use(p);
		
		assertTrue(d.occupiable());
	}
	
	@Test public void playerInvalidDoorTest1() {
		Player p = generatePlayer(0, 0);
		Door d = new Door(0, 0, "test", true);
		
		d.use(p);
		
		assertFalse(d.occupiable());
	}
	
	@Test public void playerInvalidDoorTest2() {
		Player p = generatePlayer(0, 0);
		Door d = new Door(0, 0, "test", true);
		
		assertFalse(d.occupiable());
	}
	
	// TODO: Following more implementation, there will be more tests here for interaction with Items
	
	/**
	 * Utility method to return a Test Player at x and y position.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private Player generatePlayer(int x, int y) {
		Player p = new Player(x, y, 0, 1, 0, "Bibbly Bob", "file");
		
		return p;
	}
}
