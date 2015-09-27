package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tests.logic.LogicTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	LogicTestSuite.class
})
public class FullTestSuite {

}
