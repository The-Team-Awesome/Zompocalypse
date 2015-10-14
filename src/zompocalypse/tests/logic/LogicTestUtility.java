package zompocalypse.tests.logic;

import java.util.concurrent.PriorityBlockingQueue;

import zompocalypse.datastorage.Loader;
import zompocalypse.datastorage.Parser;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.items.Key;
import zompocalypse.gameworld.items.Money;
import zompocalypse.gameworld.items.Torch;
import zompocalypse.gameworld.items.Weapon;
import zompocalypse.gameworld.world.World;

public class LogicTestUtility {

	public static World getGame() {
		World game = Parser.ParseMap(Loader.testFile, false);
		return game;
	}

	public static Weapon getWeapon(World game) {
		PriorityBlockingQueue<GameObject>[][] objects = game.getObjects();

		for (int x = 0; x < objects.length; x++) {
			for (int y = 0; y < objects[0].length; y++) {
				for (GameObject object : objects[x][y]) {
					if (object instanceof Weapon) {
						return (Weapon) object;
					}
				}
			}
		}

		return null;
	}

	public static Torch getTorch(World game) {
		PriorityBlockingQueue<GameObject>[][] objects = game.getObjects();

		for(int x = 0; x < objects.length; x++) {
			for(int y = 0; y < objects[0].length; y++) {
				for(GameObject object : objects[x][y]) {
					if(object instanceof Torch) {
						return (Torch) object;
					}
				}
			}
		}

		return null;
	}

	public static Key getKey(World game) {
		PriorityBlockingQueue<GameObject>[][] objects = game.getObjects();

		for(int x = 0; x < objects.length; x++) {
			for(int y = 0; y < objects[0].length; y++) {
				for(GameObject object : objects[x][y]) {
					if(object instanceof Key) {
						return (Key) object;
					}
				}
			}
		}

		return null;
	}

	public static Money getMoney(World game) {
		PriorityBlockingQueue<GameObject>[][] objects = game.getObjects();

		for(int x = 0; x < objects.length; x++) {
			for(int y = 0; y < objects[0].length; y++) {
				for(GameObject object : objects[x][y]) {
					if(object instanceof Money) {
						return (Money) object;
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
