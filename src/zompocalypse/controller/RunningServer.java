package zompocalypse.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import zompocalypse.gameworld.world.World;
import zompocalypse.ui.appwindow.ServerPanel;

public class RunningServer extends Thread {

	private ServerPanel panel;
	private World game;
	private int port;
	private int numClients;
	private int gameClock;
	private int serverClock;

	private Server[] connections;
	private ServerSocket socketServer;
	private Clock clock;

	public RunningServer(ServerPanel panel, int port, int numClients, int gameClock, int serverClock) {
		super();

		this.panel = panel;
		this.port = port;
		this.numClients = numClients;
		this.gameClock = gameClock;
		this.serverClock = serverClock;

	}


	/**
	 * This method gets the public IP address for a Server creator so that
	 * they can pass that information on the Client connections.
	 *
	 * @return
	 */
	public String getPublicIp() {

		try {
			URL getIp = new URL("http://curlmyip.com");

			URLConnection connection = getIp.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String ip = in.readLine(); //you get the IP as a String

			in.close();

			return ip;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void startServer(World game) {
		this.game = game;

		clock = new Clock(null, game, gameClock);

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
	 * Starts up a game for multiple players. Includes starting the clock,
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

				int id = game.registerPlayer();

				connections[--numClients] = new Server(game, panel, socket, id, serverClock);
				connections[numClients].start();

				if(numClients == 0) {
					panel.updateContent("Accepted all client connections, no longer accepting.");
					multiplePlayerGame();
					panel.updateContent("All clients disconnected, closing server");
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
	 * This checks the given Server connections to see if they are still alive
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
