package zompocalypse.tests.logic;

import java.io.IOException;

import org.junit.Test;

import zompocalypse.datastorage.Loader;
import zompocalypse.datastorage.Parser;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.world.World;

public class WorldTests {

	@Test public void worldRemoveCharacterTest() {
		World game = getGame();
		int id = game.registerPlayer("elizabeth");

		Player p = game.getPlayer(id);
		game.disconnectPlayer(id);

		assert(game.getPlayer(id) == null);
	}

	private World getGame() {
		try {
			World game = Parser.ParseMap(Loader.testFile, false);
			return game;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
