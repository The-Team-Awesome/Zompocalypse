package tests;

import gameWorld.World;

import java.io.IOException;

import dataStorage.Parser;

public class ParserTests {

	public static void main(String[] args) throws IOException {
	World world = Parser.ParseMap("TestMap.csv");
	Parser.PrintMap(world);
	Parser.SaveMap(world);
	}
}
