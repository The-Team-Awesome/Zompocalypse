package zompocalypse.datastorage;

import javax.sound.sampled.Clip;

/**
 * Manages the playing of sound in the game. Given extra time, this would
 * include things like door creaks, zombie sounds and the screams of innocent
 * citizens as they are torn to shreds!
 *
 * @author Sam Costigan
 *
 */
public class SoundManager {

	private static final Clip theme = Loader.LoadSound("theme.wav");

	/**
	 * Starts a theme.
	 */
	public static void playTheme() {
		theme.start();
	}

	/**
	 * Stops a theme.
	 */
	public static void stopTheme() {
		theme.stop();
	}

}
