package zompocalypse.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import zompocalypse.ui.appwindow.MainFrame;

public class MenuListener implements ActionListener {

	private MainFrame frame;

	public void setFrame(MainFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		frame.processAction(0, command);

		frame.requestFocus();
	}

}
