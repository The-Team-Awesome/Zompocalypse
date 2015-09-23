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

	public abstract void keyPressed(KeyEvent e);

	public abstract void mousePressed(MouseEvent e);

	public abstract void actionPerformed(ActionEvent e);

	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

}
