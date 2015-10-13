package zompocalypse.datastorage;

import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.Clip;

public class SoundManager {

	private static final String themeMP3 = "theme.mp3";
	private static final String themeWav = "theme.wav";
	private static final Clip theme = Loader.LoadSound(themeWav);
	
	public static void playTheme() {
		theme.start();
	}
	
	public static void stopTheme() {
		theme.stop();
	}
	
}
