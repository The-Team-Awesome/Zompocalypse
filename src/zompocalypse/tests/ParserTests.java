package zompocalypse.tests;

import java.io.IOException;

import zompocalypse.datastorage.*;
import zompocalypse.gameworld.world.World;

public class ParserTests {

	public static void main(String[] args) throws IOException {
	World world = Parser.ParseMap("TestMap2.xml", false);
	Parser.PrintMap(world);
	Parser.SaveMap(world);
	}
}
