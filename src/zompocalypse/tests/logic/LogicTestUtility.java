package zompocalypse.tests.logic;

import java.awt.Point;
import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;

import zompocalypse.datastorage.Loader;
import zompocalypse.datastorage.Parser;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.items.Weapon;
import zompocalypse.gameworld.world.World;

public class LogicTestUtility {

	public static World getGame() {
		try {
			World game = Parser.ParseMap(Loader.testFile, false);
			return game;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Weapon getWeapon(World game) {
		PriorityBlockingQueue<GameObject>[][] objects = game.getObjects();

		for(int x = 0; x < objects.length; x++) {
			for(int y = 0; y < objects[0].length; y++) {
				for(GameObject object : objects[x][y]) {
					if(object instanceof Weapon) {
						return (Weapon) object;
					}
				}
			}
		}

		return null;
	}

	public static Player getPlayer(World game) {
		int id = game.registerPlayer("elizabeth");
		return game.getPlayer(id);
	}

}
