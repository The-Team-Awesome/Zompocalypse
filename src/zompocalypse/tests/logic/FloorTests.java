package zompocalypse.tests.logic;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.world.Floor;

public class FloorTests {

	String[] floorFile = {"ground_grey_road_end_1_n.png", "ground_grey_road_end_1_e.png",
			"ground_grey_road_end_1_s.png", "ground_grey_road_end_1_w.png"};
	Floor floor;

	@Test public void floorRotateTest() {
		floor.rotate();

		assertTrue(floor.getFileName().equals("ground_grey_road_end_1_e.png"));
	}

	@Test public void floorXTest() {
		assertTrue(floor.getX() == 0);
	}

	@Test public void floorYTest() {
		assertTrue(floor.getY() == 0);
	}

	@Before
	public void init() {
		floor = new Floor(0, 0, floorFile);
	}

}
