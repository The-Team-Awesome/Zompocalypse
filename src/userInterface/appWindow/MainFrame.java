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

public class MainFrame extends JFrame {

	private CardLayout layout;
	private GamePanel gameScreenCard;
	private JPanel cards;

	// menu panel
	public MainFrame(int id, EventListener listener) {
		super("Zompocalypse");

		// creating default panel which uses cards
		layout = new CardLayout();
		cards = new JPanel(layout);

		// adding GameScreen to content
		gameScreenCard = new GamePanel(id, listener);
		cards.add(gameScreenCard, "1");

		// setting GameScreen to be the first thing to show up
		layout.show(cards, "1");

		// setting content as default content for this frame
		this.setContentPane(cards);

		// window customization
		ImageIcon img = new ImageIcon("img/zombie-icon.png");
		this.setIconImage(img.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 1000);
		this.setLocationRelativeTo(null); // center the screen

		this.pack();
		this.setVisible(true);

		gameScreenCard.requestFocus();
	}

	public GamePanel getGameScreenCard() {
		return gameScreenCard;
	}

 }
