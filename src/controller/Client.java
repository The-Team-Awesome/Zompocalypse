package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import userInterface.appWindow.MainFrame;
import gameWorld.world.Tile;
import gameWorld.world.World;

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
	private int id;
	private MainFrame frame;
	private World game;

	private DataInputStream input;
	private DataOutputStream output;

	public Client(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			boolean running = true;

			id = input.readInt();

			/*int width = input.readInt();
			int height = input.readInt();

			frame = new MainFrame(id, game, this);
			game = new World(width, height, null);
			int length = input.readInt();
			byte[] gameData = new byte[length];

			game.fromByteArray(gameData);*/

			ObjectInputStream objIn = new ObjectInputStream(input);
			game = (World) objIn.readObject();
			frame = new MainFrame(id, game, this);

			while(running) {
				/*length = input.readInt();
				byte[] data = new byte[length];
				game.fromByteArray(data);*/
				game = (World) objIn.readObject();

				/*for(Tile[] t : game.getMap()) {
					for(Tile tile : t) {
						System.out.println(tile);
					}
				}*/

				frame.updateGame(game);

				//System.out.println(game);

				frame.repaint();
			}

			objIn.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			int code = e.getKeyCode();


			String key = "";

			if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
				key = "left";
			} else if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP) {
				key = "up";
			} else if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
				key = "right";
			} else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_DOWN) {
				key = "down";
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
	public void mousePressed(MouseEvent e) {
		try {
			int x = e.getX();
			int y = e.getY();

			output.writeInt(2);
			output.writeInt(x);
			output.writeInt(y);

			output.flush();
		} catch (IOException exception) {
			// Problem sending information to the Server, just ignore this
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String command = e.getActionCommand();

			output.writeInt(3);
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
