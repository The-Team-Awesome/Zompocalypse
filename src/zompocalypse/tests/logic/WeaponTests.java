package zompocalypse.tests.logic;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.items.Torch;
import zompocalypse.gameworld.items.Weapon;
import zompocalypse.gameworld.world.World;

public class WeaponTests {

	private String weaponFile = "sword_12.png";

	@Test public void weaponUidTest() {
		Weapon w = new Weapon(weaponFile, "A sharp sword", 0, 5);

		assertTrue(w.getUniqueID() == 0);
	}

	@Test public void weaponStrengthTest() {
		Weapon w = new Weapon(weaponFile, "A sharp sword", 0, 5);

		assertTrue(w.getStrength() == 5);
	}

	@Test public void weaponFilenameTest() {
		Weapon w = new Weapon(weaponFile, "A sharp sword", 0, 5);

		assertTrue(w.getFileName() == weaponFile);
	}

	@Test public void weaponOccupiableTest() {
		Weapon w = new Weapon(weaponFile, "A sharp sword", 0, 5);

		assertTrue(w.occupiable() == true);
	}

	@Test public void weaponMovableTest() {
		Weapon w = new Weapon(weaponFile, "A sharp sword", 0, 5);

		assertTrue(w.movable() == true);
	}

	@Test public void weaponExamineTest() {
		String desc = "A razor sharp sword";
		Weapon w = new Weapon(weaponFile, desc, 0, 5);

		assertTrue(w.examine().equals(desc));
	}

	@Test public void weaponUseTest() {
		World game = LogicTestUtility.getGame();
		Player p = LogicTestUtility.getPlayer(game);
		Weapon w = LogicTestUtility.getWeapon(game);

		w.use(p);

		assertTrue(p.getInventory().contains(w));
	}

}
