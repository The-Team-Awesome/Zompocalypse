package zompocalypse.tests.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import zompocalypse.datastorage.Parser;
import zompocalypse.gameworld.items.Container;
import zompocalypse.gameworld.items.Key;
import zompocalypse.gameworld.world.Floor;
import zompocalypse.gameworld.world.World;

public class ParserTests {

	World game;

	@Test
	public void floorIsGreyWithARoadInTheMiddle() {
		Floor[][] floor = game.getMap();
		for (int x = 0; x < floor.length; x++) {
			assertEquals(game.getMap()[x][0].getFileName(), "ground_grey_1.png");
			assertEquals(game.getMap()[x][1].getFileName(),
					"ground_grey_road_straight_1_ns.png");
			assertEquals(game.getMap()[x][2].getFileName(), "ground_grey_1.png");
		}
	}

	@Before
	public void initialiseGame() {
		game = Parser.ParseMap("Tiny_Test_Map.xml", false);
	}
}
