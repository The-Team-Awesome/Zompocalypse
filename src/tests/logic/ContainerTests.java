package tests.logic;

import static org.junit.Assert.*;
import gameWorld.items.Container;
import gameWorld.items.Key;

import org.junit.Test;

public class ContainerTests {
	
	
	@Test public void containerValidAddTest1() {
		Container c = new Container(1, true, "chest");
		c.add(new Key("key"));
		
		// At this point, the container should have been added to and be full
		assertTrue(c.isFull());
	}
	
	@Test public void containerValidAddTest2() {
		Container c = new Container(3, true, "chest");
		c.add(new Key("key"));
		
		// At this point, the container should not be full, but should have size of 3
		assertFalse(c.isFull());
		assertTrue(c.getSize() == 3);
	}
	
	@Test public void containerValidAddTest3() {
		Container c = new Container(3, true, "chest");
		Key key2 = new Key("key2");
		c.add(new Key("key0"));
		c.add(new Key("key1"));
		c.add(key2);
		
		// At this point, the container be full and contain the key2 at position 2
		assertTrue(c.isFull());
		assertTrue(c.hasItem(key2) == 2);
	}
	
}
