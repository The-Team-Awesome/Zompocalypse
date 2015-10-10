package zompocalypse.tests.logic;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import zompocalypse.datastorage.Loader;
import zompocalypse.datastorage.Parser;
import zompocalypse.gameworld.characters.HomerStrategy;
import zompocalypse.gameworld.characters.StrategyZombie;
import zompocalypse.gameworld.world.World;

public class ZombieTests {

	World game;

	private String[] filenames = { "npc_zombie_n.png",
			"npc_zombie_e.png", "npc_zombie_s.png",
			"npc_zombie_w.png" };

	public ZombieTests() {
		try {
			/**
			 * TODO: Ideally, we should create a Test map file that can be loaded in here. That
			 * file would have an instance of each object to test within the smallest possible space.
			 */
			game = Parser.ParseMap(Loader.testFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A zombie in the middle of the map should be able to move around and get to
	 * other nearby squares successfully.
	 */

	@Test public void zombieValidMove1() {
		StrategyZombie z = new StrategyZombie(game, 3, 3, new HomerStrategy(), 0, filenames);
		z.moveNorth();

		z.tick(game);

		assertTrue(z.getX() == 3);
		assertTrue(z.getY() == 2);
	}

	@Test public void zombieValidMove2() {
		StrategyZombie z = new StrategyZombie(game, 4, 4, new HomerStrategy(), 0, filenames);
		// This sequence should leave the zombie at 3, 5

		//cannot move here because wall
		z.moveNorth();
		z.tick(game);

		z.moveWest();
		z.tick(game);

		//cannot move here because wall
		z.moveNorth();
		z.tick(game);

		z.moveSouth();
		z.tick(game);

		assertTrue(z.getX() == 3);
		assertTrue(z.getY() == 5);
	}

	/**
	 * A zombie at the edge of the map shouldn't be able to move past the edge.
	 */

	@Test public void zombieInvalidMove1() {
		StrategyZombie z = new StrategyZombie(game, 0, 0, new HomerStrategy(), 0, filenames);
		z.moveNorth();

		z.tick(game);

		assertTrue(z.getX() == 0);
		assertTrue(z.getY() == 0);
	}

	@Test public void zombieInvalidMove2() {
		StrategyZombie z = new StrategyZombie(game, 0, 0, new HomerStrategy(), 0, filenames);
		// This sequence of movements should still leave the Zombie at the position 0,0
		z.moveWest();
		z.tick(game);

		z.moveNorth();
		z.tick(game);

		//TODO The following down move hits a wall in current test map
		z.moveSouth();
		z.tick(game);

		z.moveNorth();
		z.tick(game);

		z.moveNorth();
		z.tick(game);

		assertTrue(z.getX() == 0);
		assertTrue(z.getY() == 0);
	}

}
