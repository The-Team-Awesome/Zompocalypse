package zompocalypse.tests.logic;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import zompocalypse.datastorage.Loader;
import zompocalypse.datastorage.Parser;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.world.World;

public class WorldTests {

	@Test
	public void worldRemoveCharacterTest() {
		World game = getGame();
		int id = game.registerPlayer("elizabeth");

		Player p = game.getPlayer(id);
		game.disconnectPlayer(id);

		assertTrue(game.getPlayer(id) == null);
	}

	private World getGame() {
		World game = Parser.ParseMap(Loader.testFile, false);
		return game;
	}

}
