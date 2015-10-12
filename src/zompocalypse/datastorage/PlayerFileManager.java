package zompocalypse.datastorage;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

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

import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.items.Container;
import zompocalypse.gameworld.items.Door;
import zompocalypse.gameworld.items.Item;
import zompocalypse.gameworld.items.Key;
import zompocalypse.gameworld.items.Money;
import zompocalypse.gameworld.items.Torch;
import zompocalypse.gameworld.items.Weapon;
import zompocalypse.gameworld.world.Wall;

public class PlayerFileManager {

	public static void savePlayer(Player player) throws IOException {
		String saveResult = getXMLPlayer(player);

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

	private static String getXMLPlayer(Player player) {
		// Create XML Doc of World
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element xmlPlayer = doc.createElement("player");
			doc.appendChild(xmlPlayer);

			//Get all the Player attributes
			Weapon weapon = player.getEquipped();
			String[] fileName = player.getFileName().split("_");
			String name = fileName[1];
			int health = player.getHealth();
			List<Item> inventory = player.getInventory();
			int speed = player.getSpeed();
			int strength = player.getStrength();
			int score = player.score();

			xmlPlayer.setAttribute("name", name);
			xmlPlayer.setAttribute("health", String.valueOf(health));
			xmlPlayer.setAttribute("speed", String.valueOf(speed));
			xmlPlayer.setAttribute("strength", String.valueOf(strength));
			xmlPlayer.setAttribute("score", String.valueOf(score));



			// Create a Map with the (x,y) 'dimensions' of the Map.
//			Element xmlMap = doc.createElement("map");
//			xmlMap.setAttribute("dimensions",
//					world.width() + "," + world.height());
//			xmlPlayer.appendChild(xmlMap);

//			// Read through the Map and create a Row for every row in the map,
//			// and a
//			// Cell for every cell in each row.
//			for (int col = 0; col < y; col++) {
//				Element xmlRow = doc.createElement("row");
//				for (int row = 0; row < x; row++) {
//					Element xmlCell = doc.createElement("cell");
//					xmlCell.setAttribute("img",
//							getCode(map[row][col].getFileName(), textTileMap));
//					xmlRow.appendChild(xmlCell);
//					if (objects[row][col] != null) {
//						if (objects[row][col].peek() instanceof Wall) {
//							Wall wall = (Wall) objects[row][col].peek();
//							xmlCell.setAttribute("wall",
//									getCode(wall.getFileName(), textTileMap));
//							xmlCell.setAttribute("offset",
//									String.valueOf(wall.getOffset()));
//						} else if (objects[row][col].peek() instanceof Door) {
//							Door door = (Door) objects[row][col].peek();
//							xmlCell.setAttribute("door",
//									getCode(door.getFileName(), textTileMap));
//							xmlCell.setAttribute("offset",
//									String.valueOf(door.getOffset()));
//							xmlCell.setAttribute("open",
//									String.valueOf(door.isOpen()));
//							xmlCell.setAttribute("locked",
//									String.valueOf(door.isLocked()));
//						} else if (objects[row][col].peek() instanceof Key) {
//							xmlCell.appendChild(writeKey(doc,
//									(Key) objects[row][col].peek(), textTileMap));
//						} else if (objects[row][col].peek() instanceof Money) {
//							xmlCell.appendChild(writeMoney(doc,
//									(Money) objects[row][col].peek(),
//									textTileMap));
//						} else if (objects[row][col].peek() instanceof Torch) {
//							xmlCell.appendChild(writeTorch(doc,
//									(Torch) objects[row][col].peek(),
//									textTileMap));
//						} else if (objects[row][col].peek() instanceof Weapon) {
//							xmlCell.appendChild(writeWeapon(doc,
//									(Weapon) objects[row][col].peek(),
//									textTileMap));
//						} else if (objects[row][col].peek() instanceof Container) {
//							xmlCell.appendChild(writeContainer(doc,
//									(Container) objects[row][col].peek(),
//									textTileMap));
//						}
//					}
//					if (zombieSpawnPoints.contains(new Point(row, col))) {
//						xmlCell.setAttribute("zombieSpawnPoint", "");
//					}
//					if (playerSpawnPoints.contains(new Point(row, col))) {
//						xmlCell.setAttribute("playerSpawnPoint", "");
//					}
//				}
//				xmlMap.appendChild(xmlRow);
//			}

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
}
