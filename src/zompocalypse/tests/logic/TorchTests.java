package zompocalypse.tests.logic;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.items.Torch;
import zompocalypse.gameworld.items.Weapon;
import zompocalypse.gameworld.world.World;

public class TorchTests {

	private String torchFile = "torch.png";
	private String torchDesc = "A torch. You could use this to light the way!";

	@Test public void torchUidTest() {
		Torch t = new Torch(torchFile, 0);

		assertTrue(t.getUniqueID() == 0);
	}

	@Test public void torchFilenameTest() {
		Torch t = new Torch(torchFile, 0);

		assertTrue(t.getFileName() == torchFile);
	}

	@Test public void torchOccupiableTest() {
		Torch t = new Torch(torchFile, 0);

		assertTrue(t.occupiable() == true);
	}

	@Test public void torchMovableTest() {
		Torch t = new Torch(torchFile, 0);

		assertTrue(t.movable() == true);
	}

	@Test public void torchExamineTest() {
		Torch t = new Torch(torchFile, 0);

		assertTrue(t.examine().equals(torchDesc));
	}

	@Test public void torchUseTest() {
		World game = LogicTestUtility.getGame();
		Player p = LogicTestUtility.getPlayer(game);
		Torch t = LogicTestUtility.getTorch(game);

		t.use(p);

		assertTrue(p.getInventory().contains(t));
	}

	@Test public void torchLitTest() {
		World game = LogicTestUtility.getGame();
		Player p = LogicTestUtility.getPlayer(game);
		Torch t = LogicTestUtility.getTorch(game);

		t.use(p);

		assertTrue(p.hasTorch());
	}

}
