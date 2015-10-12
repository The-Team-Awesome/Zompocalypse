package zompocalypse.datastorage;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.items.Container;
import zompocalypse.gameworld.items.Door;
import zompocalypse.gameworld.items.Item;
import zompocalypse.gameworld.items.Key;
import zompocalypse.gameworld.items.Money;
import zompocalypse.gameworld.items.Torch;
import zompocalypse.gameworld.items.Weapon;
import zompocalypse.gameworld.world.Wall;
import zompocalypse.gameworld.world.World;

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

			// Get all the Player attributes
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

			if (weapon != null) {
				xmlPlayer.appendChild(writeItem(weapon, doc));
			}

			Element xmlInventory = doc.createElement("inventory");
			xmlPlayer.appendChild(xmlInventory);

			for (Item i : inventory) {
				xmlInventory.appendChild(writeItem(i, doc));
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

	private static Node writeItem(Item i, Document doc) {
		if (i instanceof Container) {
			Container container = (Container) i;
			Element xmlContainer = doc.createElement("container");
			xmlContainer.setAttribute("filename", container.getFileName());
			xmlContainer.setAttribute("size",
					String.valueOf(container.getSize()));
			xmlContainer.setAttribute("movable",
					String.valueOf(container.movable()));
			xmlContainer.setAttribute("locked",
					String.valueOf(container.isLocked()));
			xmlContainer.setAttribute("open",
					String.valueOf(container.isOpen()));
			xmlContainer.setAttribute("name",
					String.valueOf(container.getName()));
			xmlContainer.setAttribute("description",
					String.valueOf(container.examine()));
			List<Item> inventory = container.getHeldItems();
			for (Item j : inventory) {
				xmlContainer.appendChild(writeItem(j, doc));
			}
			return xmlContainer;
		} else if (i instanceof Key) {
			Key key = (Key) i;
			Element xmlMoney = doc.createElement("key");
			xmlMoney.setAttribute("filename", key.getFileName());
			return xmlMoney;
		} else if (i instanceof Money) {
			Money money = (Money) i;
			Element xmlMoney = doc.createElement("money");
			xmlMoney.setAttribute("filename", money.getFileName());
			xmlMoney.setAttribute("amount", String.valueOf(money.getAmount()));
			return xmlMoney;
		} else if (i instanceof Torch) {
			Torch torch = (Torch) i;
			Element xmlTorch = doc.createElement("torch");
			xmlTorch.setAttribute("filename", torch.getFileName());
			return xmlTorch;
		} else if (i instanceof Weapon) {
			Weapon weapon = (Weapon) i;
			Element xmlWeapon = doc.createElement("weapon");
			xmlWeapon.setAttribute("strength",
					String.valueOf(weapon.getStrength()));
			xmlWeapon.setAttribute("filename", weapon.getFileName());
			xmlWeapon.setAttribute("description", weapon.examine());
			return xmlWeapon;
		}
		throw new Error(
				"Should not ever never get to this point unless someone did a bad");
	}

	public static Player loadPlayer(File playerFile, World game) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(playerFile);

			// remember, cool kids don't take drugs

			Element playerXML = (Element) doc.getElementsByTagName("player")
					.item(0);

			int health = Integer.parseInt(playerXML.getAttribute("health"));
			String name = playerXML.getAttribute("name");
			int score = Integer.parseInt(playerXML.getAttribute("score"));
			int speed = Integer.parseInt(playerXML.getAttribute("speed"));
			int strength = Integer.parseInt(playerXML.getAttribute("strength"));

			String[] filenames = { "character_" + name + "_empty_n.png",
					"character_" + name + "_empty_e.png",
					"character_" + name + "_empty_s.png",
					"character_" + name + "_empty_w.png" };

			Weapon weapon = null;
			List<Item> inventory = new ArrayList<Item>();

			if (playerXML.hasChildNodes()) {
				NodeList nList = playerXML.getChildNodes();
				for (int x = 0; x < nList.getLength(); x++) {
					Element node = (Element) nList.item(x);
					// if (object.getNodeName().equals("container")) {
					// cont.add(parseContainer(textTileMap, object));
					if (node.getNodeName().equals("weapon")) {
						String fileName = node.getAttribute("filename");
						String description = node.getAttribute("description");
						int damage = Integer.parseInt(node.getAttribute("strength"));
						weapon = new Weapon(fileName, description, game.getUID(), damage);
					}

				}
			}

			Player player = new Player(0, 0, Orientation.NORTH, 0, score,
					"Bibbly Bob", filenames, null);
			player.setStrength(strength);
			player.setHealth(health);
			player.setSpeed(speed);
			player.setEquipped(weapon);

			return player;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		throw new Error("Unable to load Player for some reason :(");
	}
}
