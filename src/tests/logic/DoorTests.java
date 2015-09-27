package tests.logic;

import static org.junit.Assert.*;
import gameWorld.world.Door;

import org.junit.Test;

public class DoorTests {
	
	@Test public void doorValidUnlockTest1() {
		Door d = new Door(0, 0, "test", true);
		
		assertTrue(d.unlock(true));
	}
	
	@Test public void doorValidUnlockTest2() {
		Door d = new Door(0, 0, "test", false);
		
		assertTrue(d.unlock(true));
	}
	
	@Test public void doorInvalidUnlockTest1() {
		Door d = new Door(0, 0, "test", true);
		
		assertFalse(d.unlock(false));
	}
	
	@Test public void doorInvalidUnlockTest2() {
		Door d = new Door(0, 0, "test", false);
		
		assertFalse(d.unlock(false));
	}
	
}
