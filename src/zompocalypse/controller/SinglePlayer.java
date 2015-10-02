package zompocalypse.controller;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.KeyStroke;

import zompocalypse.gameworld.world.World;
import zompocalypse.ui.appwindow.MainFrame;
import zompocalypse.ui.appwindow.UICommand;

/**
 * This is the Single Player Event Listener which passes input from the user to
 * the game.
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
		boolean editable = game.getEditMode();

		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT
				|| code == KeyEvent.VK_KP_LEFT) {
			game.processCommand(id, UICommand.WEST.getValue());

		} else if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP
				|| code == KeyEvent.VK_KP_UP) {
			game.processCommand(id, UICommand.NORTH.getValue());

		} else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT
				|| code == KeyEvent.VK_KP_RIGHT) {
			game.processCommand(id, UICommand.EAST.getValue());

		} else if ((code == KeyEvent.VK_S) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			frame.processKeyPress(code, UICommand.OPTIONS.getValue());

		} else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN
				|| code == KeyEvent.VK_KP_DOWN) {
			game.processCommand(id, UICommand.SOUTH.getValue());

		} else if (code == KeyEvent.VK_COMMA) {
			frame.processKeyPress(code, UICommand.ROTATECLOCKWISE.getValue());

		} else if (code == KeyEvent.VK_PERIOD) {
			frame.processKeyPress(code,
					UICommand.ROTATEANTICLOCKWISE.getValue());

			// TODO this is just something for me to work with to be able to
			// start editing screens
		} else if (code == KeyEvent.VK_F8) { // expand north
			game.setEditMode();
		} else if (code == KeyEvent.VK_P && editable) { // expand north
			game.toggleWalls();
		} else if (code == KeyEvent.VK_Y && editable) { // expand north
			game.expandMap("north");
		} else if (code == KeyEvent.VK_H && editable) { // shrink north
			game.shrinkMap("north");
		} else if (code == KeyEvent.VK_U && editable) { // expand east
			game.expandMap("east");
		} else if (code == KeyEvent.VK_J && editable) { // shrink east
			game.shrinkMap("east");
		} else if (code == KeyEvent.VK_I && editable) { // expand south
			game.expandMap("south");
		} else if (code == KeyEvent.VK_K && editable) { // shrink south
			game.shrinkMap("south");
		} else if (code == KeyEvent.VK_O && editable) { // expand west
			game.expandMap("west");
		} else if (code == KeyEvent.VK_L && editable) { // shrink west
			game.shrinkMap("west");
		} else if (code == KeyEvent.VK_Z && editable) { // toggle zombie spawn point
			game.toggleZombieSpawnPoint();
		} else if (code == KeyEvent.VK_X && editable) { // toggle zombie spawn point
			game.togglePlayerSpawnPoint();
		} else if (code == KeyEvent.VK_T && editable) { // edit tile
			game.editTile();
		} else if (code == KeyEvent.VK_R && editable) { // rotate tile
			game.rotateTile();
		} else if (code == KeyEvent.VK_G && editable) { // edit wall
			game.editWall();
		} else if (code == KeyEvent.VK_F && editable) { // rotate Wall or Object
			game.rotateObject();
		} else if (code == KeyEvent.VK_B && editable) { // edit object
			game.editObject();
		} else if (code == KeyEvent.VK_C && editable) { // copy location
			game.copyLocation();
		} else if (code == KeyEvent.VK_V && editable) { // paste location
			game.pasteLocation();
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

		if (!game.processCommand(id, command)) {
			frame.processAction(id, command);
		}

		// After processing an action, give control back to the frame
		frame.requestFocus();
	}

}
