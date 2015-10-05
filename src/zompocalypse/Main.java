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
 * This is the entry point for playing the networked zompocalypse. It processes commands
 * provided by the user and translates those into running the game as a server or client.
 *
 * @author Sam Costigan, much obliged to David J. Pearce's Pacman code:
 * https://github.com/DavePearce/Pacman
 */
public class Main {

	public static void main(String[] args) {

		boolean server = false;
		int numClients = 0;
		String url = null;
		int port = 32768;
		int gameClock = 200;
		int serverClock = 50;

		// Run through the arguments, processing each type of command individually
		for(int i = 0; i < args.length; i++) {
			if(args[i].startsWith("-")) {
				String argument = args[i];
				if(argument.equals("-help") || argument.equals("-h")) {
					help();
				} else if(argument.equals("-server") || argument.equals("-s")) {
					server = true;
					numClients = Integer.parseInt(args[++i]);
				} else if(argument.equals("-client") || argument.equals("-c")) {
					url = args[++i];
				}
			}
		}

		// Check that invalid commands haven't been provided before trying to setup the server/client
		if(url != null && server) {
			System.out.println("Cannot be a server and connect to another server");
			System.exit(1);
		} else if(numClients == 0 && server) {
			System.out.println("A server must be expecting at least one connection");
			System.exit(1);
		}

		try {
			if(server) {
				World game = Parser.ParseMap(Loader.mapFile);
				runServer(port, numClients, gameClock, serverClock, game);
			} else {
				start();
			}
		} catch(IOException e) {
			System.out.println("I/O error: " + e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * This prints out some helpful information for setting up a server and connecting clients.
	 */
	private static void help() {
		String[][] commands = {
				{"-help/-h", "Prints out a list of commands and their function"},
				{"-server/-s <n>", "Start a server, expecting n clients to connect"},
				{"-client/-c <url>", "Create a client connection to the server at url"}
		};

		System.out.println("Welcome to Zompocalypse, created by the Team Awesome:");
		System.out.println("Kieran Mckay, Danielle Emygdio, David Thomsen, Pauline Kelly and Sam Costigan.\n");
		System.out.println("To play, type into the command line: \n\tjava -jar zompocalypse.jar <commands>");
		System.out.println("Typing in no commands will run the game in single player mode.");
		System.out.println("Commands:");

		for(String[] command : commands) {
			// Simple method of lining up text
			String space = "\t\t";
			if(command[0].length() > 14) space = "\t";

			System.out.println("\t" + command[0] + space + command[1]);
		}

		System.exit(0);
	}

	/**
	 * This starts up a server for players to connect to, then waits for all connections.
	 *
	 * @param port - The port number for the application
	 * @param numClients - The number of clients that the server is expecting
	 * @param gameClock - The rate at which the game should be refreshed
	 * @param networkClock - The rate at which data should be transferred
	 * @param game - The game!
	 */
	private static void runServer(int port, int numClients, int gameClock, int networkClock, World game) {
		Clock clock = new Clock(null, game, gameClock);

		System.out.println("Server listening on port " + port);
		System.out.println("Server awaiting " + numClients + " clients");

		try {
			Server[] connections = new Server[numClients];
			ServerSocket socketServer = new ServerSocket(port);
			while(true) {
				Socket socket = socketServer.accept();
				System.out.println("Accepted client: " + socket.getInetAddress());
				int id = game.registerPlayer();

				connections[--numClients] = new Server(game, socket, id, networkClock);
				connections[numClients].start();

				if(numClients == 0) {
					System.out.println("Accepted all client connections, no longer accepting.");
					multiplePlayerGame(clock, connections);
					System.out.println("All clients disconnected, closing server");
					socketServer.close();
					System.exit(1);
					return;
				}

			}
		} catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Starts up a single player game with one player. Includes starting the clock,
	 * which runs the game loop and then yielding control to that clock.
	 *
	 * @param gameClock
	 * @param game
	 */
	private static void start() {
		MenuListener user = new MenuListener();
		MainFrame frame = new MainFrame(user);
		user.setFrame(frame);
	}

	/**
	 * Starts up a game for multiple players. Includes starting the clock,
	 * which runs the game loop and then yielding control to that clock.
	 *
	 * @param clock
	 * @param connections
	 */
	private static void multiplePlayerGame(Clock clock, Server... connections) {

		clock.start();

		while(checkConnections(connections)) {
			Thread.yield();
		}

		return;
	}

	/**
	 * This checks the given Server connections to see if they are still alive
	 * returning a boolean for the connections state.
	 *
	 * @param connections
	 * @return
	 */
	private static boolean checkConnections(Server... connections) {
		for(Server server : connections) {
			if(server.isAlive()) {
				return true;
			}
		}

		return false;
	}

}
