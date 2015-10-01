package zompocalypse.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import zompocalypse.tests.logic.LogicTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	LogicTestSuite.class
})
public class FullTestSuite {

}
