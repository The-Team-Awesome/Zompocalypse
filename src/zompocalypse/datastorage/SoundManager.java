package zompocalypse.datastorage;

import javafx.scene.media.MediaPlayer;

public class SoundManager {

	private static final String theme = "theme.mp3";
	
	public static void playTheme() {
		MediaPlayer theme = Loader.LoadMP3("theme.mp3");
		theme.play();
	}
	
}
