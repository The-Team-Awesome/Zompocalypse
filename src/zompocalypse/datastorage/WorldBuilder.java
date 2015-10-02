package zompocalypse.datastorage;

import java.awt.Component;
import java.util.PriorityQueue;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.world.*;

public class WorldBuilder {

	public static Floor[][] expandMap(Floor[][] map, String direction) {
		Floor[][] returnMap = new Floor[1][1];

		int x, y;
		String[] floor = { "ground_grey_1.png" };

		switch (direction) {
		case "north":
			returnMap = new Floor[map.length][map[0].length + 1];
			for (y = 0; y < map[0].length; y++) {
				for (x = 0; x < map.length; x++) {
					returnMap[x][y + 1] = map[x][y];
				}
			}
			y = 0;
			for (x = 0; x < map.length; x++) {
				returnMap[x][y] = new Floor(x, y, floor);
			}
			break;
		case "south":
			returnMap = new Floor[map.length][map[0].length + 1];
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
			returnMap = new Floor[map.length + 1][map[0].length];
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
			returnMap = new Floor[map.length + 1][map[0].length];
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

		switch (direction) {
		case "north":
			returnObjects = new PriorityQueue[objects.length][objects[0].length + 1];
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x++) {
					returnObjects[x][y] = new PriorityQueue<GameObject>();
				}
			}
			for (y = 0; y < objects[0].length; y++) {
				for (x = 0; x < objects.length; x++) {
					returnObjects[x][y + 1].addAll(objects[x][y]);
				}
			}
			break;
		case "south":
			returnObjects = new PriorityQueue[objects.length][objects[0].length + 1];
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x++) {
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
				for (x = 0; x < returnObjects.length; x++) {
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
				for (x = 0; x < returnObjects.length; x++) {
					returnObjects[x][y] = new PriorityQueue<GameObject>();
				}
			}
			for (y = 0; y < objects[0].length; y++) {
				for (x = 0; x < objects.length; x++) {
					returnObjects[x + 1][y].addAll(objects[x][y]);
				}
			}
			break;
		}

		return returnObjects;
	}

	public static Floor[][] shrinkMap(Floor[][] map, String direction) {
		Floor[][] returnMap = new Floor[1][1];

		int x, y;

		switch (direction) {
		case "north":
			returnMap = new Floor[map.length][map[0].length - 1];
			for (y = 0; y < returnMap[0].length; y++) {
				for (x = 0; x < returnMap.length; x++) {
					returnMap[x][y] = map[x][y + 1];
				}
			}
			break;
		case "south":
			returnMap = new Floor[map.length][map[0].length - 1];
			for (y = 0; y < returnMap[0].length; y++) {
				for (x = 0; x < returnMap.length; x++) {
					returnMap[x][y] = map[x][y];
				}
			}
			break;
		case "east":
			returnMap = new Floor[map.length - 1][map[0].length];
			for (y = 0; y < returnMap[0].length; y++) {
				for (x = 0; x < returnMap.length; x++) {
					returnMap[x][y] = map[x][y];
				}
			}
			break;
		case "west":
			returnMap = new Floor[map.length - 1][map[0].length];
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

		switch (direction) {
		case "north":
			returnObjects = new PriorityQueue[objects.length][objects[0].length - 1];
			for (y = 0; y < returnObjects[0].length; y++) {
				for (x = 0; x < returnObjects.length; x++) {
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
				for (x = 0; x < returnObjects.length; x++) {
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
				for (x = 0; x < returnObjects.length; x++) {
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
				for (x = 0; x < returnObjects.length; x++) {
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

	public static String[] getFileName() {
		String[] result = { "null" };
		// this is ugly but would need to be stored in a file and parsed from it
		// anyway
		Object[] possibilities = { "ground_grey_1.png", "ground_grey_2.png",
				"ground_grey_dark_circle_1.png", "ground_grey_dark_dots_1.png",
				"ground_grey_green_dots_1.png", "ground_grey_greenish_1.png",
				"ground_grey_greenish_2.png", "ground_grey_mushrooms_1.png",
				"ground_grey_mushrooms_2.png", "ground_grey_mushrooms_3.png",
				"ground_grey_mushrooms_4.png", "ground_grey_mushrooms_5.png",
				"ground_grey_mushrooms_6.png", "ground_grey_mushrooms_7.png",
				"ground_grey_mushrooms_8.png", "ground_grey_patch_1.png",
				"ground_grey_pool_1.png", "ground_grey_pools_1.png",
				"ground_grey_pools_2.png", "ground_grey_red_dots_1.png",
				"ground_grey_road_corner_1_e.png",
				"ground_grey_road_end_1_e.png",
				"ground_grey_road_straight_1_ew.png",
				"ground_grey_road_t_1_e.png", "ground_grey_road_x_1.png",
				"ground_grey_stones_1.png",
				"ground_grey_tile_1_corner_1_e.png",
				"ground_grey_tile_1_one_side_1_e.png",
				"ground_grey_tile_1_two_sides_1_e.png",
				"ground_grey_tile_2_corner_e.png",
				"ground_grey_tile_2_loose_1.png",
				"ground_grey_tile_2_loose_2.png",
				"ground_grey_tile_2_one_side_e.png",
				"ground_grey_tile_2_two_sides_e.png",
				"ground_grey_trash_1.png", "ground_grey_water_corner_e.png",
				"ground_grey_water_island_1.png",
				"ground_grey_water_one_side_e.png",
				"ground_grey_water_rock_1.png",
				"ground_grey_water_two_sides_e.png", "ground_tile_1.png",
				"ground_tile_1_greenish_1.png",
				"ground_tile_1_tile_2_corner_e.png",
				"ground_tile_1_tile_2_one_side_e.png",
				"ground_tile_1_tile_2_two_sides_e.png", "ground_tile_2.png",
				"ground_tile_2_2.png", "ground_tile_2_gravel_2.png",
				"ground_tile_2_green_dots_1.png",
				"ground_tile_2_greenish_1.png", "ground_tile_2_red_dots_1.png",
				"ground_tile_2_trash_1.png" };
		// TODO this works, but I am uncomfortable with these null values!
		Component frame = null;
		Icon icon = null;
		String fileName = (String) JOptionPane.showInputDialog(frame,
				"Pliz choice a floor", "Choice a floor",
				JOptionPane.PLAIN_MESSAGE, icon, possibilities,
				"ground_grey_1.png");
		String beginning = fileName.substring(0, fileName.length() - 6);
		String end = fileName.substring(fileName.length() - 4,
				fileName.length());
		String direction = fileName.substring(fileName.length() - 6,
				fileName.length() - 4);
		System.out.println(beginning + direction + end);

		// ha ha this is so ugly but it is very late :(
		switch (direction) {
		case "_n":
			result = new String[4];
			result[0] = beginning + "_n" + end;
			result[1] = beginning + "_e" + end;
			result[2] = beginning + "_s" + end;
			result[3] = beginning + "_w" + end;
			break;
		case "_e":
			result = new String[4];
			result[0] = beginning + "_e" + end;
			result[1] = beginning + "_s" + end;
			result[2] = beginning + "_w" + end;
			result[3] = beginning + "_n" + end;
			break;
		case "_s":
			result = new String[4];
			result[0] = beginning + "_s" + end;
			result[1] = beginning + "_w" + end;
			result[2] = beginning + "_n" + end;
			result[3] = beginning + "_e" + end;
			break;
		case "_w":
			result = new String[4];
			result[0] = beginning + "_w" + end;
			result[1] = beginning + "_n" + end;
			result[2] = beginning + "_e" + end;
			result[3] = beginning + "_s" + end;
			break;
		case "ns":
			result = new String[2];
			result[0] = beginning + "ns" + end;
			result[1] = beginning + "ew" + end;
			break;
		case "ew":
			result = new String[2];
			result[0] = beginning + "ew" + end;
			result[1] = beginning + "ns" + end;
			break;
		default:
			result = new String[1];
			result[0] = fileName;
			break;
		}
		return result;
	}

}
