package zompocalypse.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import zompocalypse.gameworld.world.World;
import zompocalypse.ui.appwindow.MainFrame;
import zompocalypse.ui.appwindow.UICommand;

/**
 * This is the Client-side Thread which listens for Events and then sends those
 * events in an appropriate format to the corresponding Server Thread. It also
 * relays information about the game to the user viewing each Client Thread.
 *
 * @author Sam Costigan
 *
 */
public class Client extends GameListenerThread {

	private final Socket socket;
	private final int gameClock;
	private int currentTime;
	private int id;
	private MainFrame frame;
	private World game;

	private DataInputStream input;
	private DataOutputStream output;
	private ObjectInputStream objectInput;

	public Client(Socket socket, int gameClock, MainFrame frame) {
		this.socket = socket;
		this.gameClock = gameClock;
		this.frame = frame;
	}

	public int id() {
		return id;
	}

	public World game() {

		return game;
	}

	/**
	 * This method is required to set up the Client without running it initially.
	 * It populates the required fields with information from the server so
	 * that the Clients frame can be updated with that information too.
	 */
	public void setup() {
		try {
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());

			id = input.readInt();

			objectInput = new ObjectInputStream(input);
			game = (World) objectInput.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Problem connect to Server, closing down Client");
			System.exit(-1);
		}
	}

	@Override
	public void run() {
		try {
			boolean running = true;
			currentTime = (int) System.currentTimeMillis();

			while(running) {
				int nextTime = (int) System.currentTimeMillis();
				int change = (nextTime - currentTime);

				if(change > gameClock) {
					// Make sure the frame is in focus, so key presses are processed
					frame.requestFocus();

					// Read in the new world and update the frame and render panel with it
					game = (World) objectInput.readObject();
					frame.updateGame(game);

					frame.repaint();

					currentTime = (int) System.currentTimeMillis();
				}
			}

			objectInput.close();
			socket.close();

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Server disconnected, closing down Client");
			System.exit(-1);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			int code = e.getKeyCode();

			String key = "";

			if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
				key = UICommand.WEST.getValue();
			} else if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP) {
				key = UICommand.NORTH.getValue();
			} else if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
				key = UICommand.EAST.getValue();
			} else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_DOWN) {
				key = UICommand.SOUTH.getValue();
			} else if(code == KeyEvent.VK_E || code == KeyEvent.VK_SPACE) {
				key = UICommand.USE.getValue();
			} else if (code == KeyEvent.VK_COMMA) {
				frame.processCommand(id, UICommand.ROTATECLOCKWISE.getValue());
				key = UICommand.ROTATECLOCKWISE.getValue();
			} else if (code == KeyEvent.VK_PERIOD) {
				frame.processCommand(id, UICommand.ROTATEANTICLOCKWISE.getValue());
				key = UICommand.ROTATEANTICLOCKWISE.getValue();
			}

			if(key.length() > 0) {
				output.writeInt(1);
				output.writeInt(key.length());
				output.writeChars(key);

				output.flush();
			}
		} catch (IOException exception) {
			// Problem sending information to the Server, just ignore this
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String command = e.getActionCommand();

			frame.processCommand(id, command);

			output.writeInt(2);
			output.writeInt(command.length());
			output.writeChars(command);

			output.flush();

			// After processing an action, give control back to the frame
			frame.requestFocus();
		} catch(IOException exception) {
			// Problem sending information to the Server, just ignore this
		}
	}

}
