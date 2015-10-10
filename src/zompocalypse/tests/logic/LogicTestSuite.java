package zompocalypse.tests.logic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	PlayerTests.class,
	ZombieTests.class,
//	ContainerTests.class,
	DoorTests.class
})
public class LogicTestSuite {
}
