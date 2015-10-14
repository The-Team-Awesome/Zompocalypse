package zompocalypse.tests.logic;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.items.Key;
import zompocalypse.gameworld.items.Torch;
import zompocalypse.gameworld.world.World;

public class KeyTests {

	private String keyFile = "gold_key.png";
	private String keyDesc = "A key. Perhaps this could unlock something....";

	@Test public void keyUidTest() {
		Key k = new Key(keyFile, 0);

		assertTrue(k.getUniqueID() == 0);
	}

	@Test public void keyFilenameTest() {
		Key k = new Key(keyFile, 0);

		assertTrue(k.getFileName() == keyFile);
	}

	@Test public void keyOccupiableTest() {
		Key k = new Key(keyFile, 0);

		assertTrue(k.occupiable() == true);
	}

	@Test public void keyMovableTest() {
		Key k = new Key(keyFile, 0);

		assertTrue(k.movable() == true);
	}

	@Test public void keyExamineTest() {
		Key k = new Key(keyFile, 0);

		assertTrue(k.examine().equals(keyDesc));
	}

	@Test public void keyUseTest() {
		World game = LogicTestUtility.getGame();
		Player p = LogicTestUtility.getPlayer(game);
		Key k = LogicTestUtility.getKey(game);

		k.use(p);

		assertTrue(p.getInventory().contains(k));
	}

}
