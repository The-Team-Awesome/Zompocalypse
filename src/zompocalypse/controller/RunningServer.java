package zompocalypse.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import zompocalypse.gameworld.world.World;
import zompocalypse.ui.appwindow.multiplayer.ServerPanel;

/**
 * The RunningServer thread manages the running of a server with
 * all the client connections associated with it. It talks to the
 * ServerPanel GUI element to let the Server creator know about
 * connections and interactions with the Server.
 *
 * @author Sam Costigan
 */
public class RunningServer extends Thread {

	// The URL to fetch the servers public IP address from
	private final String ipUrl = "http://184.106.112.172";

	// The running server uses this GUI element to display information about itself
	private ServerPanel panel;
	// This game is shared with all server threads, which update their connected client
	// threads with it when appropriate
	private World game;

	private int port;
	private int numClients;
	private int gameClock;
	private int serverClock;

	private Server[] connections;
	private ServerSocket socketServer;
	private Clock clock;

	public RunningServer(ServerPanel panel, int port, int gameClock, int serverClock) {
		super();

		this.panel = panel;
		this.port = port;
		this.gameClock = gameClock;
		this.serverClock = serverClock;
	}

	public void updateNumClients(int numClients) {
		this.numClients = numClients;
	}

	/**
	 * This method gets the public IP address for a Server creator so that
	 * they can pass that information on the Client connections.
	 *
	 * @return
	 */
	public String getPublicIp() {
		try {
			URL getIp = new URL(ipUrl);

			URLConnection connection = getIp.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String ip = in.readLine();

			in.close();

			return ip;

		} catch (IOException e) {
			//e.printStackTrace();
		}

		return null;
	}

	/**
	 * This method sets up the Server threads and the Socket server
	 * to which all clients will be connecting to.
	 *
	 * @param game
	 */
	public void startServer(World game) {
		this.game = game;

		clock = new Clock(null, game, gameClock);

		// TODO: the getPublicIp() method currently fails because it can't reach
		// the url to get a public IP from. Not sure how to fix this, it seems
		// like it is being blocked by a firewall. Will test at home!
		/*String ip = getPublicIp();

		if(ip != null) {
			panel.updateContent("Server running on IP: " + ip);
		}*/
		panel.updateContent("Server listening on port " + port);
		panel.updateContent("Server awaiting " + numClients + " clients");

		try {
			connections = new Server[numClients];
			socketServer = new ServerSocket(port);
		} catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
			System.exit(1);
		}

		start();
	}


	/**
	 * Starts running a game for multiple players. Includes starting the clock,
	 * which runs the game loop and then yielding control to that clock.
	 *
	 * @param clock
	 * @param connections
	 */
	@Override
	public void run() {
		try {
			while(true) {
				Socket socket = socketServer.accept();

				panel.updateContent("Accepted client: " + socket.getInetAddress());

				int id = game.registerPlayer("gina");

				connections[--numClients] = new Server(game, panel, socket, id, serverClock);
				connections[numClients].start();

				if(numClients == 0) {
					panel.updateContent("Accepted all client connections, no longer accepting.");
					multiplePlayerGame();
					panel.updateContent("All clients disconnected, the server has been closed.");
					socketServer.close();
					return;
				}

			}
		} catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Starts up a game for multiple players. Includes starting the clock,
	 * which runs the game loop and then yielding control to that clock.
	 *
	 * @param clock
	 * @param connections
	 */
	private void multiplePlayerGame() {

		clock.start();

		while(checkConnections()) {
			Thread.yield();
		}

		return;
	}

	/**
	 * This checks the Server connections to see if they are still alive
	 * returning a boolean for the connections state.
	 *
	 * @param connections
	 * @return
	 */
	private boolean checkConnections() {
		for(Server server : connections) {
			if(server.isAlive()) {
				return true;
			}
		}

		return false;
	}

}
