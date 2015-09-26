package tests;

import gameWorld.world.World;

import java.io.IOException;

import dataStorage.*;

public class ParserTests {

	public static void main(String[] args) throws IOException {
	World world = Parser.ParseMap("TestMap2.xml");
	Parser.PrintMap(world);
	Parser.SaveMap(world);
	}
}
