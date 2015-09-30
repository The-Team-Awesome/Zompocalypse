package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import userInterface.appWindow.MainFrame;
import gameWorld.world.World;

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
			game.processCommand(id, "East");
		} else if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP) {
			game.processCommand(id, "North");
		} else if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
			game.processCommand(id, "West");
		} else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_DOWN) {
			game.processCommand(id, "South");
		} else if(code == KeyEvent.VK_COMMA) {
			frame.processKeyPress(code, "clockwise");
		} else if (code == KeyEvent.VK_PERIOD) {
			frame.processKeyPress(code, "anticlockwise");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();

		if (game.processMouseClick(id, x, y)) {

		} else {

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if(!game.processCommand(id, command)) {
			frame.processAction(id, command);
		}

		// After processing an action, give control back to the frame
		frame.requestFocus();
	}

}
