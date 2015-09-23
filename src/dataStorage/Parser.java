package dataStorage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;

import gameWorld.Door;
import gameWorld.Floor;
import gameWorld.Item;
import gameWorld.Key;
import gameWorld.Tile;
import gameWorld.Wall;
import gameWorld.World;

/**
 * Static functions used to Parse Maps for the World from a file, and can be
 * used to save Maps to File as well.
 *
 * @author thomsedavi
 */
public class Parser {

	/**
	 * Parses a Map from a CSV file and returns a World with the new Map.
	 *
	 * @param mapFile
	 *            source of CSV document
	 * @return new World with width and height and 2D array of Tiles parsed from
	 *         mapFile
	 * @throws IOException
	 *             such sad
	 */
	public static World ParseMap(String mapFile) throws IOException {

		Tile[][] map = new Tile[1][1];
		int x = 0, y = 0;
		Map<String, String> textTileMap = new HashMap<String, String>();

		BufferedReader mapReader = null;
		File textTiles = Loader.LoadFile(Loader.mapDir + File.separatorChar
				+ "tile_types.txt");
		try {
			mapReader = new BufferedReader(new FileReader(textTiles));

			String line;
			String[] split;
			while ((line = mapReader.readLine()) != null) {
				split = line.split(",");
				textTileMap.put(split[0], split[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			mapReader.close();
		}

		File mapCSV = Loader.LoadFile(Loader.mapDir + File.separatorChar
				+ mapFile);
		try {
			mapReader = new BufferedReader(new FileReader(mapCSV));
			String line = mapReader.readLine();
			String[] split = line.split(",");
			x = Integer.parseInt(split[0]);
			y = Integer.parseInt(split[1]);
			map = new Tile[y][x];
			int i = 0;
			while ((line = mapReader.readLine()) != null) {
				split = line.split(",");
				for (int j = 0; j < x; j++) {
					parseTile(map, textTileMap, split[j], i, j);
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			mapReader.close();
		}

		return new World(x, y, map);
	}

	/**
	 * Parses a tile from a String and places it on 2D array of Tiles at (i,j)
	 * coordinate.
	 */
	private static void parseTile(Tile[][] map,
			Map<String, String> textTileMap, String string, int i, int j) {
		String[] line = string.split("_");
		String[] object = line[0].split("-");
		String[] tile = null;
		String orientation;
		switch (object[0]) {
		case "0":
			Item thing = null;
			String floor = "";
			for (int k = 1; k < object.length - 1; k++) {
				floor = floor + textTileMap.get(object[k]);
				if (k < object.length - 2)
					floor = floor + "_";
				else {
					switch (object[object.length - 1]) {
					case "n":
						orientation = "nsew";
						tile = new String[4];
						for (int x = 0; x < orientation.length(); x++) {
							tile[x] = floor + "_" + orientation.charAt(x) + ".png";
						}
						break;
					case "s":
						orientation = "snwe";
						tile = new String[4];
						for (int x = 0; x < orientation.length(); x++) {
							tile[x] = floor + "_" + orientation.charAt(x) + ".png";
						}
						break;
					case "e":
						orientation = "ewns";
						tile = new String[4];
						for (int x = 0; x < orientation.length(); x++) {
							tile[x] = floor + "_" + orientation.charAt(x) + ".png";
						}
						break;
					case "w":
						orientation = "wesn";
						tile = new String[4];
						for (int x = 0; x < orientation.length(); x++) {
							tile[x] = floor + "_" + orientation.charAt(x) + ".png";
						}
						break;
					case "ns":
						tile = new String[2];
						tile[0] = floor + "_" + "ns.png";
						tile[1] = floor + "_" + "ew.png";
						break;
					case "ew":
						tile = new String[2];
						tile[0] = floor + "_" + "ew.png";
						tile[1] = floor + "_" + "ns.png";
						break;
					default:
						floor = floor + "_" + object[object.length - 1] + ".png";
						tile = new String[] { floor };
						break;
					}
				}
			}
			map[i][j] = new Floor(i, j, tile, thing);
			break;
		case "1":
			map[i][j] = new Wall();
			break;
		case "2":
			map[i][j] = new Door(i, j, "I love Kieran", false);
			break;
		}
	}

	private static Item parseItem(String str) {
		if (str.equalsIgnoreCase("K")) {
			return new Key("Kieran is so berady");
		} else
			return null;
	}

	/**
	 * @param textTileMap
	 * @param map
	 *            2D array of tiles representing map
	 * @param x
	 *            width of board
	 * @param y
	 *            height of board
	 * @return map in CSV
	 */
	private static String getCSVMap(World world, Map<String, String> textTileMap) {

		Tile[][] map = world.getMap();
		int x = world.width();
		int y = world.height();

		String mapOutput = x + "," + y + "\n";

		for (int i = 0; i < y; i++) {
			mapOutput = mapOutput + map[i][0].getCSVCode(textTileMap);
			for (int j = 1; j < x; j++) {
				mapOutput = mapOutput + "," + map[i][j].getCSVCode(textTileMap);
			}
			mapOutput = mapOutput + "\n";
		}

		return mapOutput;
	}

	/**
	 * Gives a dialog box that can be used to save the current map in CSV
	 *
	 * @param map
	 *            series of tiles in map
	 * @param x
	 *            width of map
	 * @param y
	 *            height of map
	 * @throws IOException
	 *             packs a sad
	 */
	public static void SaveMap(World world) throws IOException {

		Map<String, String> textTileMap = new HashMap<String, String>();

		BufferedReader mapReader = null;
		File textTiles = Loader.LoadFile(Loader.mapDir + File.separatorChar
				+ "tile_types.txt");
		try {
			mapReader = new BufferedReader(new FileReader(textTiles));

			String line;
			String[] split;
			while ((line = mapReader.readLine()) != null) {
				split = line.split(",");
				textTileMap.put(split[1], split[0]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			mapReader.close();
		}

		JFileChooser c = new JFileChooser();
		// TODO maybe should pass showSaveDialog a different parameter than
		// itself? IDK does it make a difference? Should it be main window or
		// something?
		int fc = c.showSaveDialog(c);
		if (fc == JFileChooser.APPROVE_OPTION) {
			BufferedWriter out = new BufferedWriter(new FileWriter(c
					.getSelectedFile().getAbsolutePath()));
			out.write(getCSVMap(world, textTileMap));
			out.close();
		}
	}

	/**
	 * Prints this map to the console as it would be represented in CSV
	 *
	 * @throws IOException
	 */
	public static void PrintMap(World world) throws IOException {
		Map<String, String> textTileMap = new HashMap<String, String>();

		BufferedReader mapReader = null;
		File textTiles = Loader.LoadFile(Loader.mapDir + File.separatorChar
				+ "tile_types.txt");
		try {
			mapReader = new BufferedReader(new FileReader(textTiles));

			String line;
			String[] split;
			while ((line = mapReader.readLine()) != null) {
				split = line.split(",");
				textTileMap.put(split[1], split[0]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			mapReader.close();
		}

		System.out.println(getCSVMap(world, textTileMap));
	}

}
