package dataStorage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

import gameWorld.Door;
import gameWorld.Floor;
import gameWorld.Tile;
import gameWorld.Wall;

public class Parser {

	public static Tile[][] ParseMap(String mapFile) throws IOException {

		Tile[][] map = new Tile[1][1];
		int x, y;

		File mapCSV = new File(mapFile);
		BufferedReader mapReader = null;

		try {
			mapReader = new BufferedReader(new FileReader(mapCSV));
			String line = mapReader.readLine();
			String[] split = line.split(",");
			x = Integer.parseInt(split[0]);
			y = Integer.parseInt(split[1]);
			map = new Tile[x][y];
			int i = 0;
			while ((line = mapReader.readLine()) != null) {
				split = line.split(",");
				for (int j = 0; j < x; j++) {
					parseTile(map, split[j], i, j);
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			mapReader.close();
		}

		return map;
	}

	private static void parseTile(Tile[][] map, String string, int i, int j) {
		String[] line = string.split("-");
		switch (line[0]) {
		case "0":
			map[i][j] = new Floor(i, j, "Kieran is great", null);
			return;
		case "1":
			map[i][j] = new Wall();
			return;
		case "2":
			map[i][j] = new Door(i, j, "I love Kieran", false);
			return;
		}
	}

	private static String getCSVMap(Tile[][] map, int x, int y) {

		String mapOutput = x + "," + y + "\n";

		for (int i = 0; i < y; i++) {
			mapOutput = mapOutput + map[i][0].getCode();
			for (int j = 1; j < x; j++) {
				mapOutput = mapOutput + "," + map[i][j].getCode();
			}
			mapOutput = mapOutput + "\n";
		}

		return mapOutput;
	}

	public static void SaveMap(Tile[][] map, int x, int y) throws IOException {
		JFileChooser c = new JFileChooser();
		int fc = c.showSaveDialog(c);
		if (fc == JFileChooser.APPROVE_OPTION) {
			BufferedWriter out = new BufferedWriter(new FileWriter(c
					.getSelectedFile().getAbsolutePath()));
			out.write(getCSVMap(map, x, y));
			out.close();
		}
	}

	/**
	 * Prints this map to the console as it would
	 */
	public static void PrintMap(Tile[][] map, int x, int y) {
		System.out.println(getCSVMap(map, x, y));
	}

}
