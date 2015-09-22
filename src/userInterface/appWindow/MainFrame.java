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

	private JFrame frame;
	private CardLayout layout;
	private GamePanel gameScreenCard;
	private JPanel cards;

	// menu panel
	public MainFrame(int id, EventListener listener) {
		this.frame = new JFrame("Zompocalypse");

		// creating default panel which uses cards
		layout = new CardLayout();
		cards = new JPanel(layout);

		// adding GameScreen to content
		gameScreenCard = new GamePanel(id, listener);
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

		gameScreenCard.requestFocus();
	}

	public GamePanel getGameScreenCard() {
		return gameScreenCard;
	}

 }
