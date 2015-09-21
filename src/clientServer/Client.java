package clientServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import userInterface.appWindow.Gui;
import gameWorld.World;

/**
 * This is the Client-side Thread which listens for Events and then sends those
 * events in an appropriate format to the corresponding Server Thread. It also
 * relays information about the game to the user viewing each Client Thread.
 *
 * @author Sam Costigan
 *
 */
public class Client extends Thread implements KeyListener, MouseListener, ActionListener {

	private final Socket socket;
	private int id;
	private Gui frame;
	private World game;

	private DataInputStream input;
	private DataOutputStream output;

	public Client(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			boolean running = true;

			id = input.readInt();

			frame = new Gui("Client @ " + socket.getInetAddress(), id, this);

			while(running) {

				//System.out.println(id);
			}

			socket.close();

		} catch (IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
			e.printStackTrace(System.err);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			int code = e.getKeyCode();

			if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_RIGHT) {
				output.writeInt(1);
			} else if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP) {
				output.writeInt(2);
			} else if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
				output.writeInt(3);
			} else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_DOWN) {
				output.writeInt(4);
			}

			output.flush();
		} catch (IOException exception) {
			// Problem sending information to the Server, just ignore this
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		try {
			int x = e.getX();
			int y = e.getY();

			output.writeInt(5);
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

			output.writeInt(6);
			output.writeInt(command.length());
			output.writeChars(command);

			output.flush();

			// After processing an action, give control back to the frame
			frame.requestFocus();
		} catch(IOException exception) {
			// Problem sending information to the Server, just ignore this
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
