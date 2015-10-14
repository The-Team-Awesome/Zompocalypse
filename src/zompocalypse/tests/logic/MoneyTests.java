package zompocalypse.tests.logic;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.items.Money;
import zompocalypse.gameworld.items.Torch;
import zompocalypse.gameworld.world.World;

public class MoneyTests {

	private String moneyFile = "coins_gold.png";
	private String moneyDesc = "A stack of 10 gold coins.";

	@Test public void moneyUidTest() {
		Money m = new Money(moneyFile, 0, 10);

		assertTrue(m.getUniqueID() == 0);
	}

	@Test public void moneyFilenameTest() {
		Money m = new Money(moneyFile, 0, 10);

		assertTrue(m.getFileName() == moneyFile);
	}

	@Test public void moneyOccupiableTest() {
		Money m = new Money(moneyFile, 0, 10);

		assertTrue(m.occupiable() == true);
	}

	@Test public void moneyMovableTest() {
		Money m = new Money(moneyFile, 0, 10);

		assertTrue(m.movable() == true);
	}

	@Test public void moneyExamineTest() {
		Money m = new Money(moneyFile, 0, 10);

		assertTrue(m.examine().equals(moneyDesc));
	}

	@Test public void moneyAmountTest() {
		Money m = new Money(moneyFile, 0, 10);

		assertTrue(m.getAmount() == 10);
	}

	@Test public void moneyAddingTest() {
		Money m = new Money(moneyFile, 0, 10);
		m.add(5);

		assertTrue(m.getAmount() == 15);
	}

	@Test public void moneyTypeTest() {
		Money m = new Money(moneyFile, 0, 10);
		m.add(5);

		assertTrue(m.getType().equals("gold"));
	}

	@Test public void moneyUseTest() {
		World game = LogicTestUtility.getGame();
		Player p = LogicTestUtility.getPlayer(game);
		Money m = LogicTestUtility.getMoney(game);

		m.use(p);

		assertTrue(p.getInventory().contains(m));
	}

}
