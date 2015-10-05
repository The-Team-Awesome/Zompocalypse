package zompocalypse.ui.appwindow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import zompocalypse.controller.Clock;
import zompocalypse.controller.Server;
import zompocalypse.gameworld.world.World;

public class ServerPanel extends JPanel {
	
	private JTextArea field;
	private String content = "";
	
	private World game;
	private int port;
	private int numClients;
	private int gameClock;
	private int serverClock;
	
	public ServerPanel(int port, int numClients, int gameClock, int serverClock) {
		super();

		this.port = port;
		this.numClients = numClients;
		this.gameClock = gameClock;
		this.serverClock = serverClock;
		
		arrangeComponents();
	}
	
	private void arrangeComponents() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		//Insets topInset = new Insets(400, 0, 0, 0);
		Insets buttonsInset = new Insets(20, 0, 0, 0);
		int positionY = 0;
		
		field = new JTextArea();
		field.setText(content);
		field.setEditable(false);
		field.setText(content);
		
		constraints.insets = buttonsInset;
		constraints.gridy = positionY++;
		this.add(field, constraints);
	}
	
	/**
	 * Starts up a game for multiple players. Includes starting the clock,
	 * which runs the game loop and then yielding control to that clock.
	 *
	 * @param clock
	 * @param connections
	 */
	public void runServer(World game) {
		
		this.game = game;
		
		Clock clock = new Clock(null, game, gameClock);

		content += "\nServer listening on port " + port;
		content += "\nServer awaiting " + numClients + " clients";
		
		field.setText(content);

		try {
			Server[] connections = new Server[numClients];
			ServerSocket socketServer = new ServerSocket(port);
			while(true) {
				Socket socket = socketServer.accept();
				System.out.println("Accepted client: " + socket.getInetAddress());
				int id = game.registerPlayer();

				connections[--numClients] = new Server(game, socket, id, serverClock);
				connections[numClients].start();

				/*if(numClients == 0) {
					System.out.println("Accepted all client connections, no longer accepting.");
					multiplePlayerGame(clock, connections);
					System.out.println("All clients disconnected, closing server");
					socketServer.close();
					System.exit(1);
					return;
				}*/

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
