package userInterface.appWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import userInterface.renderWindow.Renderer;

public class Gui extends JFrame {

	/**
	 * This will be the listener for all action events which are triggered,
	 * such as button clicks or field entries. For example, when creating a button,
	 * it should be added using button.addActionListener(action);
	 */
	private ActionListener action;

	public Gui(String title, int id, EventListener listener) {
		super(title);

		// Set up the given EventListener to process Key, Mouse and Action events
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

		ImageIcon img = new ImageIcon("img/zombie-icon.png");
		setIconImage(img.getImage());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		JPanel renderPanel = new JPanel();
		renderPanel.setPreferredSize(new Dimension(800, 600));


		JButton button = new JButton("Test");
		button.setActionCommand("test");
		button.addActionListener(action);

		renderPanel.add(button);

		Renderer r = new Renderer(renderPanel);


		getContentPane().add(renderPanel,  BorderLayout.CENTER);

		pack();
		setVisible(true);

		//get the tick method, update r
//		while(1 == 1){
//
//			System.out.println("tick");
//			//get information from client - orientations, actions, everything
//			r.update();
//		}
	}
}
