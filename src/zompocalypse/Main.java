package zompocalypse;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import zompocalypse.controller.Client;
import zompocalypse.controller.Clock;
import zompocalypse.controller.MenuListener;
import zompocalypse.controller.Server;
import zompocalypse.controller.SinglePlayer;
import zompocalypse.datastorage.Loader;
import zompocalypse.datastorage.Parser;
import zompocalypse.gameworld.world.World;
import zompocalypse.ui.appwindow.MainFrame;
import zompocalypse.ui.appwindow.StartFrame;

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
