package userInterface.appWindow;

import gameWorld.world.World;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dataStorage.Loader;

/**
 * MainFrame is the main window of the application. It holds the other panels containing
 * the different screens of the game.
 *
 * @author Danielle Emygdio
 *
 */
public class MainFrame extends JFrame {

	private CardLayout layout;
	private GamePanel gameCard;
	private StartPanel startCard;
	private InsertServerPanel insertServer;
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
		gameCard = new GamePanel(id, game, action);
		startCard = new StartPanel(id, game, action);
		insertServer = new InsertServerPanel(id, game, action);
		cards.add(gameCard, "1");
		cards.add(startCard, "2");
		cards.add(insertServer, "3");

		// setting GameScreen to be the first thing to show up
		layout.show(cards, "3");

		// setting content as default content for this frame

		setContentPane(cards);

		// window customization
		Image img = Loader.LoadImage("zombie-icon.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 1000);
		setLocationRelativeTo(null); // center the screen

		pack();
		setVisible(true);

		this.requestFocus();
	}

	public void updateGame(World game) {
		gameCard.updateGame(game);
	}

	/**
	 * This method takes an x and y co-ordinate for a click and does shit with it.
	 *
	 * @param id
	 * @param x
	 * @param y
	 */
	public synchronized boolean processMouseClick(int id, int x, int y) {

		return true;
	}

	/**
	 * Processes the given key press event.
	 *
	 * @param id
	 * @param key
	 */
	public synchronized boolean processKeyPress(int id, String key) {
		if(key == "clockwise") {
			gameCard.rotateView(UICommand.ROTATECLOCKWISE.getValue());
		} else if (key == "anticlockwise") {
			gameCard.rotateView(UICommand.ROTATEANTICLOCKWISE.getValue());
		}
		return true;
	}

	/**
	 * Processes action received.
	 *
	 * @param id
	 * @param command
	 */
	public synchronized boolean processAction(int id, String command) {
		System.out.println(id + ", " + command);
		if(command == UICommand.ROTATEANTICLOCKWISE.getValue()
				|| command == UICommand.ROTATECLOCKWISE.getValue()) {
			gameCard.rotateView(command);
		}
		return true;
	}
 }
