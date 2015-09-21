package tests;

import gameWorld.World;

import java.io.IOException;

import dataStorage.Parser;

public class ParserTests {

	public static void main(String[] args) throws IOException {
	World world = Parser.ParseMap("src/dataStorage/maps/TestMap.csv");
	System.out.println("I call this map, 'You need the key to open the door to get the key'\n");
	Parser.PrintMap(world);
	Parser.SaveMap(world);
	}
}
