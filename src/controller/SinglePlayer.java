package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import userInterface.appWindow.MainFrame;
import gameWorld.World;

/**
 * This is the Single Player Event Listener which passes input from the user
 * to the game.
 * 
 * @author Sam Costigan
 */
public class SinglePlayer extends GameListener {
	
	private final int id;
	private final World game;
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

		if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
			game.processKeyPress(id, "left");
		} else if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP) {
			game.processKeyPress(id, "up");
		} else if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
			game.processKeyPress(id, "right");
		} else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_DOWN) {
			game.processKeyPress(id, "down");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();

		game.processMouseClick(id, x, y);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		game.processAction(id, command);

		// After processing an action, give control back to the frame
		frame.requestFocus();
	}

}
