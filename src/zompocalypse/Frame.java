package zompocalypse;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.JFrame;

/**
 * 
 * @author Sam
 *
 */
public class Frame extends JFrame {

	public Frame(String title, int id, EventListener... listeners) {
		super(title);
		
		for(EventListener listener : listeners) {
			if(listener instanceof KeyListener) {
				KeyListener key = (KeyListener) listener;
				this.addKeyListener(key);
			}
			
			if(listener instanceof MouseListener) {
				MouseListener mouse = (MouseListener) listener;
				this.addMouseListener(mouse);
			}
		}
		
		setSize(new Dimension(400, 400));
		
		setVisible(true);
		requestFocus();
	}
	
}
