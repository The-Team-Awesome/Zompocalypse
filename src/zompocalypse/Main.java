package zompocalypse;

import zompocalypse.controller.MenuListener;
import zompocalypse.ui.appwindow.MainFrame;

/**
 * This is the entry point for playing the zompocalypse game.
 *
 * @author Sam Costigan
 */
public class Main {

	public static void main(String[] args) {
		start();
	}

	/**
	 * This is entry point for the Zompocalypse game. It starts up the basic MenuListener
	 * which will send events to the MainFrame.
	 */
	private static void start() {
		MenuListener user = new MenuListener();
		MainFrame frame = new MainFrame(user);
		user.setFrame(frame);
	}
}
