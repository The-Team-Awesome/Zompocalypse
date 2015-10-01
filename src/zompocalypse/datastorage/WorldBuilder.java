package zompocalypse.datastorage;

import java.util.PriorityQueue;

import zompocalypse.gameworld.GameObject;
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

	public static PriorityQueue<GameObject>[][] expandObjects(
			PriorityQueue<GameObject>[][] objects, String direction) {
		// TODO Auto-generated method stub
		PriorityQueue<GameObject>[][] returnObjects = null;
		int x, y;

		switch(direction) {
		case "north":
			returnObjects = new PriorityQueue[objects.length][objects[0].length + 1];
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x ++) {
					returnObjects[x][y] = new PriorityQueue<GameObject>();
				}
			}
			for (y = 0; y < objects[0].length; y++) {
				for (x = 0; x < objects.length; x++) {
					returnObjects[x][y+1].addAll(objects[x][y]);
				}
			}
			break;
		case "south":
			returnObjects = new PriorityQueue[objects.length][objects[0].length + 1];
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x ++) {
					returnObjects[x][y] = new PriorityQueue<GameObject>();
				}
			}
			for (y = 0; y < objects[0].length; y++) {
				for (x = 0; x < objects.length; x++) {
					returnObjects[x][y].addAll(objects[x][y]);
				}
			}
			break;
		case "east":
			returnObjects = new PriorityQueue[objects.length + 1][objects[0].length];
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x ++) {
					returnObjects[x][y] = new PriorityQueue<GameObject>();
				}
			}
			for (y = 0; y < objects[0].length; y++) {
				for (x = 0; x < objects.length; x++) {
					returnObjects[x][y].addAll(objects[x][y]);
				}
			}
			break;
		case "west":
			returnObjects = new PriorityQueue[objects.length + 1][objects[0].length];
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x ++) {
					returnObjects[x][y] = new PriorityQueue<GameObject>();
				}
			}
			for (y = 0; y < objects[0].length; y++) {
				for (x = 0; x < objects.length; x++) {
					returnObjects[x+1][y].addAll(objects[x][y]);
				}
			}
			break;
		}

		return returnObjects;
	}

	public static Tile[][] shrinkMap(Tile[][] map, String direction) {
		Tile[][] returnMap = new Tile[1][1];

		int x, y;

		switch (direction) {
		case "north":
			returnMap = new Tile[map.length][map[0].length - 1];
			for (y = 0; y < returnMap[0].length; y++) {
				for (x = 0; x < returnMap.length; x++) {
					returnMap[x][y] = map[x][y + 1];
				}
			}
			break;
		case "south":
			returnMap = new Tile[map.length][map[0].length - 1];
			for (y = 0; y < returnMap[0].length; y++) {
				for (x = 0; x < returnMap.length; x++) {
					returnMap[x][y] = map[x][y];
				}
			}
			break;
		case "east":
			returnMap = new Tile[map.length - 1][map[0].length];
			for (y = 0; y < returnMap[0].length; y++) {
				for (x = 0; x < returnMap.length; x++) {
					returnMap[x][y] = map[x][y];
				}
			}
			break;
		case "west":
			returnMap = new Tile[map.length - 1][map[0].length];
			for (y = 0; y < returnMap[0].length; y++) {
				for (x = 0; x < returnMap.length; x++) {
					returnMap[x][y] = map[x + 1][y];
				}
			}
			break;
		}
		return returnMap;
	}

	public static PriorityQueue<GameObject>[][] shrinkObjects(
			PriorityQueue<GameObject>[][] objects, String direction) {
		// TODO Auto-generated method stub
		PriorityQueue<GameObject>[][] returnObjects = null;
		int x, y;

		switch(direction) {
		case "north":
			returnObjects = new PriorityQueue[objects.length][objects[0].length - 1];
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x ++) {
					returnObjects[x][y] = new PriorityQueue<GameObject>();
				}
			}
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x++) {
					returnObjects[x][y].addAll(objects[x][y + 1]);
				}
			}
			break;
		case "south":
			returnObjects = new PriorityQueue[objects.length][objects[0].length - 1];
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x ++) {
					returnObjects[x][y] = new PriorityQueue<GameObject>();
				}
			}
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x++) {
					returnObjects[x][y].addAll(objects[x][y]);
				}
			}
			break;
		case "east":
			returnObjects = new PriorityQueue[objects.length - 1][objects[0].length];
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x ++) {
					returnObjects[x][y] = new PriorityQueue<GameObject>();
				}
			}
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x++) {
					returnObjects[x][y].addAll(objects[x][y]);
				}
			}
			break;
		case "west":
			returnObjects = new PriorityQueue[objects.length - 1][objects[0].length];
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x ++) {
					returnObjects[x][y] = new PriorityQueue<GameObject>();
				}
			}
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x++) {
					returnObjects[x][y].addAll(objects[x + 1][y]);
				}
			}
			break;
		}

		return returnObjects;
	}

}
