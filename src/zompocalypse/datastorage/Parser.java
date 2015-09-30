package zompocalypse.datastorage;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import zompocalypse.gameworld.*;
import zompocalypse.gameworld.items.*;
import zompocalypse.gameworld.world.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.StreamResult;

import java.awt.Point;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;

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
		GameObject[][] objects = new GameObject[1][1];
		Set<Point> zombieSpawnPoints = new HashSet<Point>();
		Set<Point> playerSpawnPoints = new HashSet<Point>();
		int x = 0, y = 0;
		Map<String, String> textTileMap = new HashMap<String, String>();

		// Uses tile_type text to create a map of all the shortcuts to full
		// words used to condense down full image file names into much shorter
		// strings
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

		// Load file and parse each part of XML into cell of Map
		File mapCSV = Loader.LoadFile(Loader.mapDir + File.separatorChar
				+ mapFile);
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(mapCSV);

			// remember, cool kids don't take drugs

			Element nodeMap = (Element) doc.getElementsByTagName("map").item(0);

			String[] split = nodeMap.getAttribute("dimensions").split(",");
			x = Integer.parseInt(split[0]);
			y = Integer.parseInt(split[1]);
			map = new Tile[y][x];
			objects = new GameObject[y][x];

			NodeList rows = nodeMap.getElementsByTagName("row");

			for (int i = 0; i < rows.getLength(); i++) {
				Element cellMap = (Element) rows.item(i);
				NodeList cells = cellMap.getElementsByTagName("cell");
				for (int j = 0; j < cells.getLength(); j++) {
					Element cell = (Element) cells.item(j);
					parseTile(map, textTileMap, cell.getAttribute("img"), i, j);
					if (cell.hasAttribute("wall")) {
						parseWall(objects, textTileMap,
								cell.getAttribute("wall"),
								cell.getAttribute("offset"), i, j);

					}
					if (cell.hasAttribute("zombieSpawnPoint")) {
						zombieSpawnPoints.add(new Point(i,j));
					}
					if (cell.hasAttribute("playerSpawnPoint")) {
						playerSpawnPoints.add(new Point(i,j));
					}
				}
			}
		} catch (FileNotFoundException | ParserConfigurationException
				| SAXException e) {
			e.printStackTrace();
		} finally {
			mapReader.close();
		}

		return new World(x, y, map, objects, zombieSpawnPoints, playerSpawnPoints);
	}

	/**
	 * Parses a Wall from a String and places it on 2D array of GameObjects at
	 * (i,j) with the offset it is to be drawn at
	 */
	private static void parseWall(GameObject[][] objects,
			Map<String, String> textTileMap, String string, String offset, int i,
			int j) {
		String[] wall = expandCode(textTileMap, string);
		objects[i][j] = new Wall(wall, Integer.parseInt(offset));
	}

	/**
	 * Parses a tile from a String and places it on 2D array of Tiles at (i,j)
	 * coordinate.
	 */
	private static void parseTile(Tile[][] map,
			Map<String, String> textTileMap, String string, int i, int j) {

		String[] tile = expandCode(textTileMap, string);
		map[i][j] = new Floor(i, j, tile);
	}

	private static String[] expandCode(Map<String, String> textTileMap,
			String string) {
		String[] tile = null;
		String orientation;
		String[] object = string.split("-");
		String floor = "";
		for (int k = 0; k < object.length - 1; k++) {

			// for each part of string, expands it using Tile map.
			floor = floor + textTileMap.get(object[k]);
			if (k < object.length - 2)
				floor = floor + "_";
			else {

				// Creates array of different directions Tile can be facing
				// based on original direction
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
		return tile;
	}

	/**
	 * Creates an Item from a String and returns it.
	 *
	 * @param str
	 * @return
	 */
	private static Item parseItem(String str) {
		if (str.equalsIgnoreCase("ky")) {
			return new Key("gold_key.png", 0);
		} else
			return null;
	}

	/**
	 * Converts World Map to XML and returns it as a String to be printed or
	 * saved
	 *
	 * @param world
	 * @return map in XML
	 * @throws IOException
	 */
	private static String getXMLMap(World world) throws IOException {

		Tile[][] map = world.getMap();
		GameObject[][] objects = world.getObjects();
		int x = world.width();
		int y = world.height();
		Set<Point> zombieSpawnPoints = world.getZombieSpawnPoints();
		Set<Point> playerSpawnPoints = world.getPlayerSpawnPoints();

		// This will take the File for converting map elements to two-digit code
		// and create a Map that will be used to convert Object image names into
		// code for saving
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
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			mapReader.close();
		}

		// Create XML Doc of World
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element xmlWorld = doc.createElement("world");
			doc.appendChild(xmlWorld);

			// Create a Map with the (x,y) 'dimensions' of the Map.
			Element xmlMap = doc.createElement("map");
			xmlMap.setAttribute("dimensions",
					world.width() + "," + world.height());
			xmlWorld.appendChild(xmlMap);

			// Read through the Map and create a Row for every row in the map,
			// and a
			// Cell for every cell in each row.
			for (int i = 0; i < y; i++) {
				Element xmlRow = doc.createElement("row");
				for (int j = 0; j < x; j++) {
					Element xmlCell = doc.createElement("cell");
					xmlCell.setAttribute("img",
							getCode(map[i][j].getFileName(), textTileMap));
					xmlRow.appendChild(xmlCell);
					if (objects[i][j] != null) {
						if (objects[i][j] instanceof zompocalypse.gameworld.world.Wall) {
							Wall wall = (Wall) objects[i][j];
							xmlCell.setAttribute("wall", getCode(wall.getFileName(), textTileMap));
							xmlCell.setAttribute("offset", String.valueOf(wall.getOffset()));
						}
					}
					if (zombieSpawnPoints.contains(new Point(i,j))) {
						xmlCell.setAttribute("zombieSpawnPoint", "");
					}
					if (playerSpawnPoints.contains(new Point(i,j))) {
						xmlCell.setAttribute("playerSpawnPoint", "");
					}
				}
				xmlMap.appendChild(xmlRow);
			}

			// Convert Doc to Source
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			// Convert Source to String, then return
			StreamResult result = new StreamResult(new StringWriter());
			transformer.transform(source, result);
			return result.getWriter().toString();
		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// If we got here something went wrong!
		return "Error: Unable to save World";
	}

	private static String getCode(String string, Map<String, String> textTileMap) {
		String result = "";
		String[] tileCode = string.substring(0, string.length() - 4).split("_");
		for (int x = 0; x < tileCode.length; x++) {
			result = result + textTileMap.get(tileCode[x]);
			if (x < tileCode.length - 1)
				result = result + "-";
		}
		return result;
	}

	/**
	 * Prints this map to the console as it would be represented in XML
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
		System.out.println(getXMLMap(world));
	}

	/**
	 * Will convert World's Map to XML and offer a pop-up dialog box to save it
	 *
	 * @throws IOException
	 */
	public static void SaveMap(World world) throws IOException {
		String saveResult = getXMLMap(world);

		// Open dialog box and save XML file
		JFileChooser c = new JFileChooser();
		File f = new File("map.xml");
		c.setSelectedFile(f);
		// TODO maybe should pass showSaveDialog a different parameter than
		// itself? IDK does it make a difference? Should it be main window or
		// something?

		int fc = c.showSaveDialog(c);
		if (fc == JFileChooser.APPROVE_OPTION) {
			BufferedWriter out = new BufferedWriter(new FileWriter(c
					.getSelectedFile().getAbsolutePath()));
			out.write(saveResult);
			out.close();
		}

	}

}
