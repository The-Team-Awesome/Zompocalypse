package zompocalypse.tests.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.items.Door;
import zompocalypse.gameworld.world.Wall;
import zompocalypse.gameworld.world.World;

public class WallTests {

	String[] test = {"wall_grey_1_door_closed_ew.png", "wall_grey_1_door_closed_ns.png"};
	Wall wall = new Wall(test, 2);

	@Test public void isOccupableTrue() {
		assertFalse(wall.occupiable());
	}

	@Test public void compareToValid() {
		Wall wallTwo = wall.cloneMe();
		assertTrue((wallTwo.getFileName().equals(wall.getFileName()) &&
				(wallTwo.getOffset() == wall.getOffset())));
	}

	@Test public void rotateOnceValid() {
		Wall wallTwo = wall.cloneMe();

		wall.rotate();

		assertTrue(wallTwo.getFilenames() != wall.getFilenames());
	}

	@Test public void rotate360DegreesValid() {
		Wall wallTwo = wall.cloneMe();

		wall.rotate();
		wall.rotate();
		wall.rotate();
		wall.rotate();

		assertTrue(wallTwo.getFilenames()[0].equals(wall.getFilenames()[0]));
	}

}
