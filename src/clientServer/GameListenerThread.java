package clientServer;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class GameListenerThread extends GameListener {

	public abstract void keyPressed(KeyEvent e);

	public abstract void mousePressed(MouseEvent e);

	public abstract void actionPerformed(ActionEvent e);

}
