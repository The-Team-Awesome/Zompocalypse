package zompocalypse.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import zompocalypse.gameworld.world.World;
import zompocalypse.ui.appwindow.multiplayer.ServerPanel;

/**
 * This is the Server-side Thread which receives input from Clients via
 * the Socket and sends it to the game. Game updates are also sent back to
 * the Client.
 *
 * @author Sam Costigan
 *
 */
public class Server extends Thread {

	private final Socket socket;
	private final int id;
	private final int networkClock;
	private final World game;
	private final ServerPanel panel;

	public Server(World game, ServerPanel panel, Socket socket, int id, int networkClock) {
		this.game = game;
		this.panel = panel;
		this.socket = socket;
		this.id = id;
		this.networkClock = networkClock;
	}

	@Override
	public void run() {
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());

			boolean running = true;
			output.writeInt(id);
			output.flush();

			ObjectOutputStream objOut = new ObjectOutputStream(output);
			objOut.writeObject(game);
			objOut.flush();

			int time = (int) System.currentTimeMillis();
			int gap = time - (int) System.currentTimeMillis();

			while(running) {
				if(input.available() != 0) {
					int code = input.readInt();
					switch(code) {
						case 1:
							// In this case, a key was pressed
							String key = readInputString(input);

							panel.updateContent("Player " + id + " sent command " + key);

							game.processCommand(id, key);

							break;
						case 2:
							// In this case, a Swing component was
							// triggered, such as a button press.
							// The command is given and will be passed on
							String command = readInputString(input);

							panel.updateContent("Player " + id + " sent command " + command);

							game.processCommand(id, command);

							break;
					}
				}

				// This makes sure the game state is only broadcast every
				// time the time passed has exceeded the value of networkClock.
				gap = (int) System.currentTimeMillis() - time;
				if(gap > networkClock) {

					// The objOut stream needs to be reset in order to send the correct data.
					// By default, serialized objects are cached for the duration of the output
					// stream. Resetting the streams means the correctly updated info is sent!
					objOut.writeObject(game);
					//objOut.
					objOut.flush();
					objOut.reset();
					time = (int) System.currentTimeMillis();
				}
			}

			objOut.close();
			socket.close();

		} catch (IOException e) {
			panel.updateContent("Player " + id + " has disconnected");
			game.disconnectPlayer(id);
		}

	}

	private String readInputString(DataInputStream input) throws IOException {
		int length = input.readInt();
		char[] string = new char[length];
		for(int i = 0; i < length; i++) {
			string[i] = input.readChar();
		}

		String value = String.copyValueOf(string);
		return value;
	}

}
