package zompocalypse.datastorage;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.items.Container;
import zompocalypse.gameworld.items.Door;
import zompocalypse.gameworld.items.Item;
import zompocalypse.gameworld.items.Key;
import zompocalypse.gameworld.items.Money;
import zompocalypse.gameworld.items.Torch;
import zompocalypse.gameworld.items.Weapon;
import zompocalypse.gameworld.world.Floor;
import zompocalypse.gameworld.world.Wall;
import zompocalypse.gameworld.world.World;

/**
 * Static functions used to Parse Maps for the World from a file, and can be
 * used to save Maps to File as well.
 *
 * @author David Thomsen
 */
public class Parser {

	private static int id;

	/**
	 * Parses a Map from a XML file and returns a World with the new Map.
	 *
	 * @param mapFile
	 *            source of XML document
	 * @param fileName
	 * @return new World with width and height and 2D array of Tiles parsed from
	 *         mapFile
	 * @throws IOException
	 *             such sad
	 */
	public static World ParseMap(String mapFile, boolean absolute) {

		Floor[][] map = new Floor[1][1];
		PriorityBlockingQueue<GameObject>[][] objects = null;
		Set<Point> zombieSpawnPoints = new HashSet<Point>();
		Set<Point> playerSpawnPoints = new HashSet<Point>();
		int x = 0, y = 0;
		id = 0;
		Map<String, String> textTileMap = new HashMap<String, String>();

		// Uses tile_type text to create a map of all the shortcuts to full
		// words used to condense down full image file names into much shorter
		// strings
		BufferedReader mapReader = null;
		File textTiles = Loader.LoadFile(Loader.mapDir + Loader.separator
				+ "tile_types.txt", false);

		try {
			mapReader = new BufferedReader(new FileReader(textTiles));

			String line;
			String[] split;
			while ((line = mapReader.readLine()) != null) {
				split = line.split(",");
				textTileMap.put(split[0], split[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				mapReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Load file and parse each part of XML into cell of Map
		File mapXML = null;

		System.out.println(mapFile);

		if (absolute)
			mapXML = Loader.LoadFile(mapFile, true);
		else
			mapXML = Loader.LoadFile(
					Loader.mapDir + Loader.separator + mapFile, false);

		if (mapXML == null)
			return null;

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(mapXML);

			// remember, cool kids don't take drugs

			Element nodeMap = (Element) doc.getElementsByTagName("map").item(0);

			// Load the dimensions from XML document and create a new Map of
			// those directions

			String[] split = nodeMap.getAttribute("dimensions").split(",");
			x = Integer.parseInt(split[0]);
			y = Integer.parseInt(split[1]);
			map = new Floor[x][y];

			// create a grid of priority queues for the objects in the game

			objects = new PriorityBlockingQueue[x][y];
			for (int j = 0; j < y; j++) {
				for (int i = 0; i < x; i++) {
					objects[i][j] = new PriorityBlockingQueue<GameObject>();
				}
			}

			NodeList rows = nodeMap.getElementsByTagName("row");

			// go through each Row, and for each Row each Cell, parse the floor
			// tile and the Object in each location

			for (int row = 0; row < rows.getLength(); row++) {
				Element cellMap = (Element) rows.item(row);
				NodeList cells = cellMap.getElementsByTagName("cell");
				for (int col = 0; col < cells.getLength(); col++) {
					Element cell = (Element) cells.item(col);
					parseTile(map, textTileMap, cell.getAttribute("img"), col,
							row);
					if (cell.hasAttribute("wall")) {
						parseWall(objects, textTileMap,
								cell.getAttribute("wall"),
								cell.getAttribute("offset"), col, row);

					} else if (cell.hasAttribute("door")) {
						parseDoor(objects, textTileMap,
								cell.getAttribute("door"),
								cell.getAttribute("offset"),
								cell.getAttribute("locked"),
								cell.getAttribute("open"), col, row);
					} else if (cell.hasChildNodes()) {
						Element object = (Element) cell.getFirstChild();
						if (object.getNodeName().equals("container")) {
							objects[col][row].add(parseContainer(textTileMap,
									object));
						} else if (object.getNodeName().equals("key")) {
							objects[col][row].add(parseKey(textTileMap,
									object.getAttribute("img")));
						} else if (object.getNodeName().equals("money")) {
							objects[col][row].add(parseMoney(textTileMap,
									object.getAttribute("img"),
									object.getAttribute("amount")));
						} else if (object.getNodeName().equals("torch")) {
							objects[col][row].add(parseTorch(textTileMap,
									object.getAttribute("img")));
						} else if (object.getNodeName().equals("weapon")) {
							objects[col][row].add(parseWeapon(textTileMap,
									object.getAttribute("img"),
									object.getAttribute("strength"),
									object.getAttribute("description")));
						}
					}
					if (cell.hasAttribute("zombieSpawnPoint")) {
						zombieSpawnPoints.add(new Point(col, row));
					}
					if (cell.hasAttribute("playerSpawnPoint")) {
						playerSpawnPoints.add(new Point(col, row));
					}
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			return null;
		} finally {
			try {
				mapReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return new World(x, y, map, objects, zombieSpawnPoints,
				playerSpawnPoints, id);
	}

	/**
	 * returns a Weapon constructed from Strings read from XML document
	 *
	 * @param textTileMap
	 *            map of codes to full word
	 * @param img
	 *            condensed image file name to be expanded using the codes in
	 *            the above textTileMap
	 * @param strength
	 *            to be converted to Integer and loaded as sword strength
	 * @param description
	 *            of sword
	 * @return
	 */
	private static Weapon parseWeapon(Map<String, String> textTileMap,
			String img, String strength, String description) {
		return new Weapon(expandCode(textTileMap, img)[0], description, id++,
				Integer.parseInt(strength));
	}

	/**
	 * returns a Torch constructed from Strings read from XML document
	 *
	 * @param textTileMap
	 *            map of codes to full word
	 * @param img
	 *            condensed image file name to be expanded using the codes in
	 *            the above textTileMap
	 * @return
	 */
	private static Torch parseTorch(Map<String, String> textTileMap, String img) {
		return new Torch(expandCode(textTileMap, img)[0], id++);
	}

	/**
	 * returns a Money constructed from Strings read from XML document
	 *
	 * @param textTileMap
	 *            map of codes to full word
	 * @param img
	 *            condensed image file name to be expanded using the codes in
	 *            the above textTileMap
	 * @param amount
	 *            to be converted to Integer and loaded as money amount
	 * @return
	 */
	private static Money parseMoney(Map<String, String> textTileMap,
			String img, String amount) {
		return new Money(expandCode(textTileMap, img)[0], id++,
				Integer.parseInt(amount));
	}

	/**
	 * returns a Key constructed from Strings read from XML document
	 *
	 * @param textTileMap
	 *            map of codes to full word
	 * @param img
	 *            condensed image file name to be expanded using the codes in
	 *            the above textTileMap
	 * @return
	 */
	private static Key parseKey(Map<String, String> textTileMap, String img) {
		return new Key(expandCode(textTileMap, img)[0], id++);
	}

	/**
	 * returns a Key constructed from an Element read from XML document.
	 * Recursively fills container with Game objects
	 *
	 * @param textTileMap
	 *            map of codes to full word
	 * @param img
	 *            condensed image file name to be expanded using the codes in
	 *            the above textTileMap
	 * @return
	 */
	private static Container parseContainer(Map<String, String> textTileMap,
			Element object) {
		String[] container = expandCode(textTileMap, object.getAttribute("img"));
		Container cont = new Container(container, Integer.parseInt(object
				.getAttribute("size")), object.getAttribute("name"),
				object.getAttribute("description"), object.getAttribute(
						"movable").equals("true"), object
						.getAttribute("locked").equals("false"), object
						.getAttribute("open").equals("false"), id++);
		if (object.hasChildNodes()) {
			NodeList nList = object.getChildNodes();
			for (int x = 0; x < nList.getLength(); x++) {
				Element node = (Element) nList.item(x);
				// if (object.getNodeName().equals("container")) {
				// cont.add(parseContainer(textTileMap, object));
				if (node.getNodeName().equals("key")) {
					cont.add(parseKey(textTileMap, node.getAttribute("img")));
				} else if (node.getNodeName().equals("money")) {
					cont.add(parseMoney(textTileMap, node.getAttribute("img"),
							node.getAttribute("amount")));
				} else if (node.getNodeName().equals("torch")) {
					cont.add(parseTorch(textTileMap, node.getAttribute("img")));
				} else if (node.getNodeName().equals("weapon")) {
					cont.add(parseWeapon(textTileMap, node.getAttribute("img"),
							node.getAttribute("strength"),
							node.getAttribute("description")));
				}
			}
		}
		return cont;
	}

	/**
	 * Loads a Door into the array of Objects based on information taken from
	 * XML document
	 *
	 * @param objects
	 *            Door will be loaded into Objects at col and row coordinates
	 * @param textTileMap
	 *            map of codes to full word
	 * @param img
	 *            condensed image file name to be expanded using the codes in
	 *            the above textTileMap
	 * @param offset
	 *            for door to draw
	 * @param locked
	 * @param open
	 */
	private static void parseDoor(
			PriorityBlockingQueue<GameObject>[][] objects,
			Map<String, String> textTileMap, String string, String offset,
			String locked, String open, int col, int row) {
		String[] door = expandCode(textTileMap, string);
		boolean isLocked = true;
		if (locked.equals("false"))
			isLocked = false;
		objects[col][row].add(new Door(col, row, door,
				Integer.parseInt(offset), isLocked, id++));
		if (open.equals("true"))
			((Door) objects[col][row].peek()).use(null);
	}

	/**
	 * Parses a Wall from a String and places it on 2D array of GameObjects at
	 * (i,j) with the offset it is to be drawn at
	 */
	private static void parseWall(
			PriorityBlockingQueue<GameObject>[][] objectz,
			Map<String, String> textTileMap, String string, String offset,
			int i, int j) {
		String[] wall = expandCode(textTileMap, string);
		objectz[i][j].add(new Wall(wall, Integer.parseInt(offset)));
	}

	/**
	 * Parses a tile from a String and places it on 2D array of Tiles at (i,j)
	 * coordinate.
	 */
	private static void parseTile(Floor[][] map,
			Map<String, String> textTileMap, String string, int i, int j) {

		String[] tile = expandCode(textTileMap, string);
		map[i][j] = new Floor(i, j, tile);
	}

	/**
	 * Creates a full .png file name from a condensed file name using
	 * abbreviated code names passed into a Map to get full String words
	 */
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
					orientation = "nesw";
					tile = new String[4];
					for (int x = 0; x < orientation.length(); x++) {
						tile[x] = floor + "_" + orientation.charAt(x) + ".png";
					}
					break;
				case "s":
					orientation = "swne";
					tile = new String[4];
					for (int x = 0; x < orientation.length(); x++) {
						tile[x] = floor + "_" + orientation.charAt(x) + ".png";
					}
					break;
				case "e":
					orientation = "eswn";
					tile = new String[4];
					for (int x = 0; x < orientation.length(); x++) {
						tile[x] = floor + "_" + orientation.charAt(x) + ".png";
					}
					break;
				case "w":
					orientation = "wnes";
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
					floor = floor + "_"
							+ textTileMap.get(object[object.length - 1])
							+ ".png";
					tile = new String[] { floor };
					break;
				}
			}
		}
		if (tile == null) {
			tile = new String[1];
			tile[0] = textTileMap.get(object[0]) + ".png";
		}
		return tile;
	}

	/**
	 * Converts World Map to XML and returns it as a String to be printed or
	 * saved
	 *
	 * @param world
	 * @return map in XML
	 * @throws IOException
	 */
	public static String getXMLMap(World world) {

		Floor[][] map = world.getMap();
		PriorityBlockingQueue<GameObject>[][] objects = world.getObjects();
		int x = world.width();
		int y = world.height();
		Set<Point> zombieSpawnPoints = world.getZombieSpawnPoints();
		Set<Point> playerSpawnPoints = world.getPlayerSpawnPoints();

		// This will take the File for converting map elements to two-digit code
		// and create a Map that will be used to convert Object image names into
		// code for saving
		Map<String, String> textTileMap = new HashMap<String, String>();

		BufferedReader mapReader = null;
		File textTiles = Loader.LoadFile(Loader.mapDir + Loader.separator
				+ "tile_types.txt", false);
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
			try {
				mapReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			for (int col = 0; col < y; col++) {
				Element xmlRow = doc.createElement("row");
				for (int row = 0; row < x; row++) {
					Element xmlCell = doc.createElement("cell");
					xmlCell.setAttribute("img",
							getCode(map[row][col].getFileName(), textTileMap));
					xmlRow.appendChild(xmlCell);
					if (objects[row][col] != null) {
						if (objects[row][col].peek() instanceof Wall) {
							Wall wall = (Wall) objects[row][col].peek();
							xmlCell.setAttribute("wall",
									getCode(wall.getFileName(), textTileMap));
							xmlCell.setAttribute("offset",
									String.valueOf(wall.getOffset()));
						} else if (objects[row][col].peek() instanceof Door) {
							Door door = (Door) objects[row][col].peek();
							xmlCell.setAttribute("door",
									getCode(door.getFileName(), textTileMap));
							xmlCell.setAttribute("offset",
									String.valueOf(door.getOffset()));
							xmlCell.setAttribute("open",
									String.valueOf(door.isOpen()));
							xmlCell.setAttribute("locked",
									String.valueOf(door.isLocked()));
						} else if (objects[row][col].peek() instanceof Key) {
							xmlCell.appendChild(writeKey(doc,
									(Key) objects[row][col].peek(), textTileMap));
						} else if (objects[row][col].peek() instanceof Money) {
							xmlCell.appendChild(writeMoney(doc,
									(Money) objects[row][col].peek(),
									textTileMap));
						} else if (objects[row][col].peek() instanceof Torch) {
							xmlCell.appendChild(writeTorch(doc,
									(Torch) objects[row][col].peek(),
									textTileMap));
						} else if (objects[row][col].peek() instanceof Weapon) {
							xmlCell.appendChild(writeWeapon(doc,
									(Weapon) objects[row][col].peek(),
									textTileMap));
						} else if (objects[row][col].peek() instanceof Container) {
							xmlCell.appendChild(writeContainer(doc,
									(Container) objects[row][col].peek(),
									textTileMap));
						}
					}
					if (zombieSpawnPoints.contains(new Point(row, col))) {
						xmlCell.setAttribute("zombieSpawnPoint", "");
					}
					if (playerSpawnPoints.contains(new Point(row, col))) {
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
		return "Error: Unable to save the World";
	}

	/**
	 * Write a Container to XML, iterating through each object in the container
	 * recursively, returning a Node representing that container
	 */
	private static Node writeContainer(Document doc, Container container,
			Map<String, String> textTileMap) {
		Element xmlContainer = doc.createElement("container");
		xmlContainer.setAttribute("img",
				getCode(container.getFileName(), textTileMap));
		xmlContainer.setAttribute("size", String.valueOf(container.getSize()));
		xmlContainer.setAttribute("movable",
				String.valueOf(container.movable()));
		xmlContainer.setAttribute("locked",
				String.valueOf(container.isLocked()));
		xmlContainer.setAttribute("open", String.valueOf(container.isOpen()));
		xmlContainer.setAttribute("name", String.valueOf(container.getName()));
		xmlContainer.setAttribute("description",
				String.valueOf(container.examine()));
		List<Item> inventory = container.getHeldItems();
		for (Item i : inventory) {
			if (i instanceof Container) {
				xmlContainer.appendChild(writeContainer(doc, (Container) i,
						textTileMap));
			} else if (i instanceof Key) {
				xmlContainer.appendChild(writeKey(doc, (Key) i, textTileMap));
			} else if (i instanceof Torch) {
				xmlContainer
						.appendChild(writeTorch(doc, (Torch) i, textTileMap));
			} else if (i instanceof Weapon) {
				xmlContainer.appendChild(writeWeapon(doc, (Weapon) i,
						textTileMap));
			} else if (i instanceof Money) {
				xmlContainer
						.appendChild(writeMoney(doc, (Money) i, textTileMap));
			}
		}
		return xmlContainer;
	}

	/**
	 * Returns a XML node representing a Weapon
	 */
	private static Node writeWeapon(Document doc, Weapon i,
			Map<String, String> textTileMap) {
		Element xmlWeapon = doc.createElement("weapon");
		xmlWeapon.setAttribute("img", getCode(i.getFileName(), textTileMap));
		xmlWeapon.setAttribute("strength", String.valueOf(i.getStrength()));
		xmlWeapon.setAttribute("description", i.examine());
		return xmlWeapon;
	}

	/**
	 * Returns a XML node representing a Torch
	 */
	private static Node writeTorch(Document doc, Torch i,
			Map<String, String> textTileMap) {
		Element xmlTorch = doc.createElement("torch");
		xmlTorch.setAttribute("img", getCode(i.getFileName(), textTileMap));
		return xmlTorch;
	}

	/**
	 * Returns a XML node representing a Money
	 */
	private static Node writeMoney(Document doc, Money i,
			Map<String, String> textTileMap) {
		Element xmlMoney = doc.createElement("money");
		xmlMoney.setAttribute("img", getCode(i.getFileName(), textTileMap));
		xmlMoney.setAttribute("amount", String.valueOf(i.getAmount()));
		return xmlMoney;
	}

	/**
	 * Returns a XML node representing a Key
	 */
	private static Node writeKey(Document doc, Key i,
			Map<String, String> textTileMap) {
		Element xmlKey = doc.createElement("key");
		xmlKey.setAttribute("img", getCode(i.getFileName(), textTileMap));
		return xmlKey;
	}

	/**
	 * Returns a String representing a condensed filename to save space when
	 * saving the game
	 */
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
		File textTiles = Loader.LoadFile(Loader.mapDir + Loader.separator
				+ "tile_types.txt", false);
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
