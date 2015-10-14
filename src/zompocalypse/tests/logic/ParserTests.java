package zompocalypse.tests.logic;

import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import org.junit.Before;
import org.junit.Test;

import zompocalypse.datastorage.Parser;
import zompocalypse.gameworld.items.*;
import zompocalypse.gameworld.world.*;

public class ParserTests {

	World game;

	@Test
	public void floorIsGreyWithARoadInTheMiddle() {
		Floor[][] floor = game.getMap();
		for (int x = 0; x < floor.length; x++) {
			assertEquals(floor[x][0].getFileName(), "ground_grey_1.png");
			assertEquals(floor[x][1].getFileName(),
					"ground_grey_road_straight_1_ns.png");
			assertEquals(floor[x][2].getFileName(), "ground_grey_1.png");
		}
	}

	@Test
	public void objectIsKey() {
		PriorityBlockingQueue[][] objects = game.getObjects();
		Object thing = objects[0][2].poll();
		assertTrue(thing instanceof Key);
	}

	@Test
	public void objectIsMoney() {
		PriorityBlockingQueue[][] objects = game.getObjects();
		Object thing = objects[1][2].poll();
		assertTrue(thing instanceof Money);
		Money money = (Money) thing;
		assertEquals(money.getAmount(), 10);
		assertEquals(money.getType(), "gold");
	}

	@Test
	public void objectIsTorch() {
		PriorityBlockingQueue[][] objects = game.getObjects();
		Object thing = objects[2][2].poll();
		assertTrue(thing instanceof Torch);
	}

	@Test
	public void objectIsWeapon() {
		PriorityBlockingQueue[][] objects = game.getObjects();
		Object thing = objects[3][2].poll();
		assertTrue(thing instanceof Weapon);
		Weapon weapon = (Weapon) thing;
		assertEquals(weapon.getStrength(), 20);
		assertEquals(weapon.examine(), "Basil");
	}

	@Test
	public void objectIsContainer() {
		PriorityBlockingQueue[][] objects = game.getObjects();
		Object thing = objects[4][2].poll();
		assertTrue(thing instanceof Container);
		Container container = (Container) thing;
		assertEquals(container.getName(), "Chesty");
		assertEquals(container.examine(), "Is a chest");
		assertEquals(container.getSize(), 1);
		List<Item> inventory = container.getHeldItems();
		assertEquals(inventory.size(), 1);
		Item item = inventory.get(0);
		assertTrue(item instanceof Money);
		Money money = (Money) item;
		assertEquals(money.getAmount(), 5);
		assertEquals(money.getType(), "silver");
	}

	@Test
	public void objectsAreWalls() {
		PriorityBlockingQueue[][] objects = game.getObjects();
		assertTrue(objects[0][0].poll() instanceof Wall);
		assertTrue(objects[1][0].poll() instanceof Wall);
		assertTrue(objects[3][0].poll() instanceof Wall);
		assertTrue(objects[4][0].poll() instanceof Wall);
	}

	@Test
	public void objectIsDoor() {
		PriorityBlockingQueue[][] objects = game.getObjects();
		assertTrue(objects[2][0].poll() instanceof Door);
	}

	@Test
	public void savesCorrectly() {
		Door door = (Door) game.getObjects()[2][0].peek();
		door.unlock(true);
		String string = Parser.getXMLMap(game);
		assertEquals(
				string,
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><world>"
						+ "<map dimensions=\"5,3\"><row>"
						+ "<cell img=\"g-gr-1\" offset=\"48\" wall=\"wl-br-1-st-ns\"/>"
						+ "<cell img=\"g-gr-1\" offset=\"48\" wall=\"wl-br-1-st-ns\"/>"
						+ "<cell door=\"wl-br-1-do-cl-ns\" img=\"g-gr-1\" locked=\"false\" offset=\"48\" open=\"false\"/>"
						+ "<cell img=\"g-gr-1\" offset=\"48\" wall=\"wl-br-1-st-ns\"/>"
						+ "<cell img=\"g-gr-1\" offset=\"48\" wall=\"wl-br-1-st-ns\"/>"
						+ "</row><row><cell img=\"g-gr-r-st-1-ns\"/>"
						+ "<cell img=\"g-gr-r-st-1-ns\"/>"
						+ "<cell img=\"g-gr-r-st-1-ns\"/>"
						+ "<cell img=\"g-gr-r-st-1-ns\"/>"
						+ "<cell img=\"g-gr-r-st-1-ns\"/>"
						+ "</row><row>"
						+ "<cell img=\"g-gr-1\"><key img=\"gl-ky\"/></cell>"
						+ "<cell img=\"g-gr-1\"><money amount=\"10\" img=\"co-gl\"/>"
						+ "</cell><cell img=\"g-gr-1\"><torch img=\"to\"/></cell>"
						+ "<cell img=\"g-gr-1\"><weapon description=\"Basil\" img=\"sw-1\" strength=\"20\"/></cell>"
						+ "<cell img=\"g-gr-1\"><container description=\"Is a chest\" img=\"ch-1-cl-ew\" locked=\"true\" movable=\"false\" name=\"Chesty\" open=\"true\" size=\"1\">"
						+ "<money amount=\"5\" img=\"co-sv\"/>"
						+ "</container></cell></row></map></world>");
	}

	@Before
	public void initialiseGame() {
		game = Parser.ParseMap("Tiny_Test_Map.xml", false);
	}
}
