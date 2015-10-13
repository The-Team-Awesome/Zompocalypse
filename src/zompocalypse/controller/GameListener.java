package zompocalypse.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class simply implements the necessary Listener interfaces for a Single player
 * game and then overrides the methods that won't end up being used. This cuts down
 * the amount of clutter in the extending class.
 *
 * @author Sam Costigan
 */
public abstract class GameListener implements KeyListener, ActionListener {

	@Override
	public abstract void keyPressed(KeyEvent e);

	@Override
	public abstract void actionPerformed(ActionEvent e);

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}
