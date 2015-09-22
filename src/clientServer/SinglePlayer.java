package clientServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import userInterface.appWindow.GamePanel;
import gameWorld.World;

public class SinglePlayer implements KeyListener, MouseListener, ActionListener {
	private final World game;
	private final int id;
	private GamePanel panel;

	public SinglePlayer(World game, int id) {
		this.game = game;
		this.id = id;
	}

	public void setPanel(GamePanel panel) {
		this.panel = panel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		System.out.println(code);

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

		System.out.println(e.getPoint());
		int x = e.getX();
		int y = e.getY();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		System.out.println(command);
		// After processing an action, give control back to the frame
		panel.requestFocus();
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
