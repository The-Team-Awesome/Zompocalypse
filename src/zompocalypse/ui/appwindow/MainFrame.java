package zompocalypse.ui.appwindow;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.world.World;

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
			System.out.println(mouse);
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
		layout.show(cards, "1");

		// setting content as default content for this frame

		setContentPane(cards);

		// window customization
		Image img = Loader.LoadImage("zombie-icon.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 1000);
		setResizable(false);
		setLocationRelativeTo(null); // center the screen

		pack();
		setVisible(true);

		// TODO: From Sam. This is not always called successfully...
		//this.requestFocus();
	}

	/**
	 * Updates the World for this frame.
	 * @param game - updated instance of World.
	 */
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
		if(key == UICommand.ROTATECLOCKWISE.getValue()) {
			gameCard.rotateView(UICommand.ROTATECLOCKWISE.getValue());
		} else if (key == UICommand.ROTATEANTICLOCKWISE.getValue()) {
			gameCard.rotateView(UICommand.ROTATEANTICLOCKWISE.getValue());
		} else if (key == UICommand.OPTIONS.getValue()) {
			saveGame();
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
		if(command == UICommand.ROTATEANTICLOCKWISE.getValue()
				|| command == UICommand.ROTATECLOCKWISE.getValue()) {
			gameCard.rotateView(command);
			return true;
		} else if(command == UICommand.OPTIONS.getValue()) {
			saveGame();
			return true;
		}
		return false;
	}

	private void saveGame() {
		Object[] options = {"Yes, please",
		                    "No way!"};

		int option = JOptionPane.showOptionDialog(this,
		    "Are you sure you wanna save the game?",
		    "Save Game",
		    JOptionPane.YES_NO_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,     //do not use a custom Icon
		    options,  //the titles of buttons
		    options[0]); //default button title

		if(option == 0) {
			gameCard.saveGame();
		}
	}

	private void openOptions() {
		new JOptionPane();

	}
 }
