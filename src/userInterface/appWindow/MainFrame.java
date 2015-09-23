package userInterface.appWindow;

import gameWorld.World;

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

public class MainFrame extends JFrame {

	private CardLayout layout;
	private GamePanel gameScreenCard;
	private JPanel cards;

	/**
	 * This will be the listener for all action events which are triggered,
	 * such as button clicks or field entries. For example, when creating a button,
	 * it should be added using button.addActionListener(action);
	 */
	private ActionListener action;

	public MainFrame(int id, World game, EventListener listener) {
		super("Zompocalypse");

		// Set up the given EventListener to process Key, Mouse and Action events
		if(listener instanceof KeyListener) {
			KeyListener key = (KeyListener) listener;
			addKeyListener(key);
		}

		if(listener instanceof MouseListener) {
			MouseListener mouse = (MouseListener) listener;
			addMouseListener(mouse);
		}

		if(listener instanceof ActionListener) {
			action = (ActionListener) listener;
		}

		// creating default panel which uses cards
		layout = new CardLayout();
		cards = new JPanel(layout);

		// adding GameScreen to content
		gameScreenCard = new GamePanel(id, game, action);
		cards.add(gameScreenCard, "1");

		// setting GameScreen to be the first thing to show up
		layout.show(cards, "1");

		// setting content as default content for this frame

		setContentPane(cards);

		// window customization
		ImageIcon img = new ImageIcon("img/zombie-icon.png");
		setIconImage(img.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 1000);
		setLocationRelativeTo(null); // center the screen

		pack();
		setVisible(true);

		this.requestFocus();
	}

 }
