//package zompocalypse.tests.logic;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
//import zompocalypse.gameworld.items.Container;
//import zompocalypse.gameworld.items.Key;
//
//public class ContainerTests {
//
//	private static final String key = "gold_key_inv.png";
//
//	@Test public void containerValidAddTest1() {
//		Container c = new Container(1, false, false, "chest");
//		c.add(new Key(key,0));
//
//		// At this point, the container should have been added to and be full
//		assertTrue(c.isFull());
//	}
//
//	@Test public void containerValidAddTest2() {
//		Container c = new Container(3, false, false, "chest");
//		c.add(new Key(key,0));
//
//		// At this point, the container should not be full, but should have size of 3
//		assertFalse(c.isFull());
//		assertTrue(c.getSize() == 3);
//	}
//
//	@Test public void containerValidAddTest3() {
//		Container c = new Container(3, false, false, "chest");
//		Key key2 = new Key(key,0);
//		c.add(new Key(key,0));
//		c.add(new Key(key,0));
//		c.add(key2);
//
//		// At this point, the container be full and contain the key2 at position 2
//		assertTrue(c.isFull());
//		assertTrue(c.hasItem(key2) == 2);
//	}
//
//	@Test public void containerInavlidAddTest1() {
//		Container c = new Container(0, false, false, "chest");
//
//		// The key should not be able to be added to a container with size 0
//		assertFalse(c.add(new Key(key,0)));
//	}
//
//	@Test public void containerInavlidAddTest2() {
//		Container c = new Container(1, false, false, "chest");
//		c.add(new Key(key,0));
//
//		// The key should not be able to be added to a container that is full
//		assertFalse(c.add(new Key(key,0)));
//	}
//
//}
