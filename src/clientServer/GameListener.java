package clientServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class simply implements the necessary Listener interfaces for a Single player
 * game and then overrides the methods that won't end up being used. This cuts down
 * the amount of clutter in the extending class.
 * 
 * @author Sam Costigan
 */
public abstract class GameListener implements KeyListener, MouseListener, ActionListener {

	@Override
	public abstract void keyPressed(KeyEvent e);

	@Override
	public abstract void mousePressed(MouseEvent e);

	@Override
	public abstract void actionPerformed(ActionEvent e);

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
