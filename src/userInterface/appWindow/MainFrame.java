package userInterface.appWindow;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import userInterface.renderWindow.Renderer;

public class MainFrame {

	/**
	 * This will be the listener for all action events which are triggered,
	 * such as button clicks or field entries. For example, when creating a button,
	 * it should be added using button.addActionListener(action);
	 */

	private JFrame frame;
	private ActionListener action;
	private CardLayout layout;
	private GamePanel gameScreenCard;
	private JPanel cards;

	// menu panel
	public MainFrame(int id, EventListener listener) {
		this.frame = new JFrame("Zompocalypse");

		// Set up the given EventListener to process Key, Mouse and Action events
		if(listener instanceof KeyListener) {
			KeyListener key = (KeyListener) listener;
			frame.addKeyListener(key);
		}

		if(listener instanceof MouseListener) {
			MouseListener mouse = (MouseListener) listener;
			frame.addMouseListener(mouse);
		}

		if(listener instanceof ActionListener) {
			action = (ActionListener) listener;
		}

		// creating default panel which uses cards
		layout = new CardLayout();
		cards = new JPanel(layout);

		// adding GameScreen to content
		gameScreenCard = new GamePanel();
		cards.add(gameScreenCard, "1");

		// setting GameScreen to be the first thing to show up
		layout.show(cards, "1");

		// setting content as default content for this frame
		frame.setContentPane(cards);

		// window customization
		ImageIcon img = new ImageIcon("img/zombie-icon.png");
		frame.setIconImage(img.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setLocationRelativeTo(null); // center the screen

		frame.pack();
		frame.setVisible(true);
	}

	public void requestFocus() {
		this.frame.requestFocus();
	}

 }
