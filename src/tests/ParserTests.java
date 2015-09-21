package tests;

import gameWorld.Tile;

import java.io.IOException;

import dataStorage.Parser;

public class ParserTests {

	public static void main(String[] args) throws IOException {
	Tile[][] map = Parser.ParseMap("src/dataStorage/maps/TestMap.csv");
	Parser.PrintMap(map, 5, 5);
	Parser.SaveMap(map, 5, 5);
	}
}
