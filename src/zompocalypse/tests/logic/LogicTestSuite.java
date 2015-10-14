package zompocalypse.tests.logic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	WorldTests.class,
	PlayerTests.class,
	ZombieTests.class,
	ContainerTests.class,
	DoorTests.class,
	OrientationTests.class,
	WeaponTests.class,
	MoneyTests.class,
	KeyTests.class,
	TorchTests.class,
	WallTests.class,
	FloorTests.class
})
public class LogicTestSuite {
}
