package zompocalypse.tests.logic;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import zompocalypse.gameworld.Orientation;

public class OrientationTests {

	@Test public void orientationValidNextTest1() {
		Orientation ori = Orientation.EAST;
		Orientation oriNext = Orientation.getNext(ori);

		assertTrue(oriNext == Orientation.SOUTH);
	}

	@Test public void orientationValidNextTest2() {
		Orientation ori = Orientation.SOUTH;
		Orientation oriNext = Orientation.getNext(ori);

		assertTrue(oriNext == Orientation.WEST);
	}

	@Test public void orientationValidPrevTest1() {
		Orientation ori = Orientation.EAST;
		Orientation oriPrev = Orientation.getPrev(ori);

		assertTrue(oriPrev == Orientation.NORTH);
	}

	@Test public void orientationValidPrevTest2() {
		Orientation ori = Orientation.NORTH;
		Orientation oriPrev = Orientation.getPrev(ori);

		assertTrue(oriPrev == Orientation.WEST);
	}

}
