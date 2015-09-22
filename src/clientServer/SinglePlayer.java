package clientServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import userInterface.appWindow.MainFrame;
import gameWorld.World;

public class SinglePlayer implements KeyListener, MouseListener, ActionListener {
	private final World game;
	private final int id;
	private MainFrame frame;

	public SinglePlayer(World game, int id) {
		this.game = game;
		this.id = id;
	}

	public void setFrame(MainFrame frame) {
		this.frame = frame;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_RIGHT) {
			//output.writeInt(1);
		} else if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP) {
			//output.writeInt(2);
		} else if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
			//output.writeInt(3);
		} else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_DOWN) {
			//output.writeInt(4);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();


		// After processing an action, give control back to the frame
		frame.requestFocus();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

}
