package dataStorage;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Loader {

	public static final String assetsDir = "assets";
	public static final String mapDir = "map";

	/**
	 * This method is used for the safe loading of files. File paths
	 * are often different when a file is loaded using Eclipse compared
	 * to when running an exported .jar file, so this handles that.
	 *
	 * @param filename - A string representing the files name.
	 * @return The loaded file using the given string
	 */
	public static File LoadFile(String filename) {
		File newFile = new File(assetsDir + File.separatorChar + filename);

		return newFile;
	}

	/**
	 * Safely loads in an image and falls back to printing out the
	 * Exception if one should happen.
	 *
	 * @param filename
	 * @return
	 */
	public static Image LoadImage(String filename) {

		File imageFile = LoadFile(filename);
		Image image = null;

		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			System.out.println(e);
		}

		return image;
	}

	/**
	 * Safely loads in a clip and falls back to printing out
	 * the Exception if one should happen.
	 *
	 * @param filename
	 * @return
	 */
	public static Clip LoadSound(String filename) {
		try {
			Clip clip = AudioSystem.getClip();
			File soundFile = LoadFile(filename);
	        AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundFile);
			clip.open(inputStream);

			return clip;

		} catch(LineUnavailableException e) {
			System.out.println(e);
		} catch(UnsupportedAudioFileException e) {
			System.out.println(e);
		} catch(IOException e) {
			System.out.println(e);
		}

		return null;
	}

}
