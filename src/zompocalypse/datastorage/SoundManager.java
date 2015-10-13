package zompocalypse.datastorage;

import javax.sound.sampled.Clip;

public class SoundManager {

	private static final Clip theme = Loader.LoadSound("theme.wav");
	
	public static void playTheme() {
		theme.start();
	}
	
	public static void stopTheme() {
		theme.stop();
	}
	
}
