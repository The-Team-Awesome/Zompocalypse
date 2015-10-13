package zompocalypse.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import zompocalypse.ui.appwindow.MainFrame;

/**
 * This is a simple listener which sends button clicks during the users
 * navigation of the menu and setting up the game. It is replaced by
 * a more intelligent listener as soon as the game starts running.
 * 
 * @author Sam Costigan
 */
public class MenuListener implements ActionListener {

	private MainFrame frame;

	public void setFrame(MainFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		frame.processCommand(0, command);

		frame.requestFocus();
	}

}
