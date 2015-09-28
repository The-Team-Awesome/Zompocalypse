package dataStorage;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class is a central place for loading all images, text, sound, font
 * etc. files. This keeps repetition of functionality down to a minimum and
 * ensures that all files are correctly loaded from the same place.
 *
 * @author Sam Costigan
 */
public class Loader {

	/**
	 * These represent common file names which are used to refer
	 * to different types of files throughout the project.
	 */
	public static final String assetsDir = "assets";
	public static final String spritesDir = "sprites";
	public static final String iconsDir = "icons";
	public static final String mapDir = "map";

	public static final String mapFile = "TestMap2.xml";

	/**
	 * This method is used for the safe loading of files. File paths
	 * are often different when a file is loaded using Eclipse compared
	 * to when running an exported .jar file, so this handles that.
	 *
	 * @param filename - A string representing the files name.
	 * @return The loaded file using the given string
	 */
	public static File LoadFile(String filename) {
		String name = assetsDir + File.separatorChar + filename;

		// Using an InputStream rather than simply loading files by filename
		// allows the Loader to work when exported to a .jar as well as in Eclipse.
		InputStream stream = Loader.class.getClassLoader().getResourceAsStream(name);

		File file = null;
		try {
			// It just comes with the overhead of needing to read in the file this way!
			// Files are created, then populated with data by being read in through
			// the InputStream and output to the temporary file using an OutputStream.
			file = File.createTempFile("tempfile", ".tmp");

			int read;
			byte[] bytes = new byte[1024];

			// If the file was not successfully found, a NullPointerException will occur at this point.
			// This usually means the file doesn't exist within the assets folder - try moving it there,
			// changing permissions on the file or making sure you're using the correct name.

			OutputStream out = new FileOutputStream(file);

			while ((read = stream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			file.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}

	/**
	 * Safely loads in an image and falls back to printing out the
	 * Exception if one should happen.
	 *
	 * @param filename
	 * @return
	 */
	public static Image LoadSprite(String filename) {
		return LoadImage(spritesDir + File.separatorChar + filename);
	}

	public static Image LoadIcon(String filename) {

		Image image = LoadImage(iconsDir + File.separatorChar + filename);

		return image;
	}

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
