package zompocalypse.controller;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
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

	private int id;
	private World game;
	private MainFrame frame;

	public SinglePlayer(World game, int id) {
		this.game = game;
		this.id = id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public void setGame(World game) {
		this.game = game;
	}

	public void setFrame(MainFrame frame) {
		this.frame = frame;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		boolean editable = game.getEditMode();

		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT
				|| code == KeyEvent.VK_KP_LEFT) { // player moves west
			game.processCommand(id, UICommand.WEST.getValue());

		} else if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP
				|| code == KeyEvent.VK_KP_UP) { // player moves north
			game.processCommand(id, UICommand.NORTH.getValue());

		} else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT
				|| code == KeyEvent.VK_KP_RIGHT) { // player moves east
			game.processCommand(id, UICommand.EAST.getValue());

		} else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN
				|| code == KeyEvent.VK_KP_DOWN) { // player moves south
			game.processCommand(id, UICommand.SOUTH.getValue());

		} else if(code == KeyEvent.VK_E || code == KeyEvent.VK_SPACE) { // player uses
			frame.processCommand(id, UICommand.USE.getValue());
			game.processCommand(id, UICommand.USE.getValue());
		} else if ((code == KeyEvent.VK_S) && ((e.getModifiers() & InputEvent.CTRL_MASK) != 0)) {
			frame.processCommand(id, UICommand.OPTIONS.getValue());

		} else if (code == KeyEvent.VK_COMMA) {
			frame.processCommand(id, UICommand.ROTATECLOCKWISE.getValue());
			game.processCommand(id, UICommand.ROTATECLOCKWISE.getValue());
		} else if (code == KeyEvent.VK_PERIOD) {
			frame.processCommand(id, UICommand.ROTATEANTICLOCKWISE.getValue());
			// Hi. I did this because processCommand/KeyPress/MouseClick should
			// be the only interactions between players and the World/Frame
			game.processCommand(id, UICommand.ROTATEANTICLOCKWISE.getValue());
			// TODO this is just something for me to work with to be able to
			// start editing screens
		} else if(code == KeyEvent.VK_F9) {
			game.toggleGodmode();
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
		} else if (code == KeyEvent.VK_B && editable) { // edit door
			game.editDoor();
		} else if (code == KeyEvent.VK_N && editable) { // open/close door
			game.toggleDoor();
		} else if (code == KeyEvent.VK_G && editable) { // edit wall
			game.editWall();
		} else if (code == KeyEvent.VK_F && editable) { // rotate Wall or Object
			game.rotateObject();
		} else if (code == KeyEvent.VK_9 && editable) { // edit object
			game.editObject();
		} else if(code == KeyEvent.VK_0 && editable) {
			game.editItem(false);
		} else if (code == KeyEvent.VK_C && editable) { // copy location
			game.copyLocation();
		} else if (code == KeyEvent.VK_V && editable) { // paste location
			game.pasteLocation();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		frame.processCommand(id, command);
		game.processCommand(id, command);

		// After processing an action, give control back to the frame
		frame.requestFocus();
	}

}
