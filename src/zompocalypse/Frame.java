package zompocalypse;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.*;

/**
 * 
 * @author Sam
 *
 */
public class Frame extends JFrame {

	// This will be the listener for all action events which are triggered,
	// such as button clicks or field entries. For example, when creating a button,
	// it should be added using button.addActionListener(action);
	private ActionListener action;
	
	public Frame(String title, int id, EventListener listener) {
		super(title);
		
		if(listener instanceof KeyListener) {
			KeyListener key = (KeyListener) listener;
			this.addKeyListener(key);
		}
		
		if(listener instanceof MouseListener) {
			MouseListener mouse = (MouseListener) listener;
			this.addMouseListener(mouse);
		}
		
		if(listener instanceof ActionListener) {
			action = (ActionListener) listener;
		}
		
		JPanel panel = new JPanel();
		
		JButton button = new JButton("Test");
		button.setActionCommand("test");
		button.addActionListener(action);
		
		panel.add(button);
		
		add(panel);
		
		setSize(new Dimension(400, 400));
		
		setVisible(true);
		requestFocus();
	}
	
}
