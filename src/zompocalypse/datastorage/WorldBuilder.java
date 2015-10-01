package zompocalypse.datastorage;

import zompocalypse.gameworld.world.*;

public class WorldBuilder {

	public static Tile[][] expandMap(Tile[][] map, String direction) {
		Tile[][] returnMap = new Tile[1][1];

		int x, y;
		String[] floor = {"ground_grey_1.png"};

		switch (direction) {
		case "north":
			returnMap = new Tile[map.length][map[0].length + 1];
			for (y = 0; y < map[0].length; y++) {
				for (x = 0; x < map.length; x++) {
					returnMap[x][y+1] = map[x][y];
				}
			}
			y = 0;
			for (x = 0; x < map.length; x++) {
				returnMap[x][y] = new Floor(x, y, floor);
			}
			break;
		case "south":
			returnMap = new Tile[map.length][map[0].length + 1];
			for (y = 0; y < map[0].length; y++) {
				for (x = 0; x < map.length; x++) {
					returnMap[x][y] = map[x][y];
				}
			}
			y = returnMap[0].length - 1;
			for (x = 0; x < map.length; x++) {
				returnMap[x][y] = new Floor(x, y, floor);
			}
			break;
		case "east":
			returnMap = new Tile[map.length+1][map[0].length];
			for (y = 0; y < map[0].length; y++) {
				for (x = 0; x < map.length; x++) {
					returnMap[x][y] = map[x][y];
				}
			}
			x = returnMap.length - 1;
			for (y = 0; y < map[0].length; y++) {
				returnMap[x][y] = new Floor(x, y, floor);
			}
			break;
		case "west":
			returnMap = new Tile[map.length + 1][map[0].length];
			returnMap = new Tile[map.length+1][map[0].length];
			for (y = 0; y < map[0].length; y++) {
				for (x = 0; x < map.length; x++) {
					returnMap[x + 1][y] = map[x][y];
				}
			}
			x = 0;
			for (y = 0; y < map[0].length; y++) {
				returnMap[x][y] = new Floor(x, y, floor);
			}
			break;
		}


		return returnMap;
	}

}
