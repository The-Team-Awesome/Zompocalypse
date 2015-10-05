package zompocalypse.ui.appwindow;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.EventListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import zompocalypse.controller.Client;
import zompocalypse.controller.Clock;
import zompocalypse.controller.SinglePlayer;
import zompocalypse.datastorage.Loader;
import zompocalypse.datastorage.Parser;
import zompocalypse.gameworld.world.World;
import zompocalypse.ui.appwindow.multiplayer.ClientPanel;
import zompocalypse.ui.appwindow.multiplayer.MultiplayerPanel;
import zompocalypse.ui.appwindow.multiplayer.ServerPanel;

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
	private MultiplayerPanel multiplayerCard;
	private ClientPanel clientCard;
	private ServerPanel serverCard;
	private JPanel cards;
	//private SinglePlayer player;
	private World game;

	/**
	 * This will be the listener for all action events which are triggered,
	 * such as button clicks or field entries. For example, when creating a button,
	 * it should be added using button.addActionListener(action);
	 */
	private ActionListener action;

	/**
	 * TODO: This info has just been copied over from the Main class. This
	 * is where it will now be relevant, since we are essentially changing
	 * the functionality over from Main to MainFrame
	 */
	private int numClients = 1;
	private int port = 32768;
	private int gameClock = 200;
	private int clientClock = 100;
	private int serverClock = 50;

	public MainFrame(ActionListener listener) {
		super("Zompocalypse");

		if(listener instanceof ActionListener) {
			action = (ActionListener) listener;
		}

		// creating default panel which uses cards
		layout = new CardLayout();
		cards = new JPanel(layout);

		// start menu and server menu
		startCard = new StartPanel(action);
		multiplayerCard = new MultiplayerPanel(action);
		clientCard = new ClientPanel(action);
		serverCard = new ServerPanel(port, numClients, gameClock, serverClock);

		cards.add(startCard, "2");
		cards.add(multiplayerCard, "3");
		cards.add(clientCard, "4");
		cards.add(serverCard, "5");

		// setting Start menu to be the first thing to show up
		layout.show(cards, "2");

		// setting content as default content for this frame
		setContentPane(cards);

		customizeWindow();

		setLocationRelativeTo(null); // center the screen
	}

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
		startCard = new StartPanel(action);
		clientCard = new ClientPanel(action);
		cards.add(gameCard, "1");
		cards.add(startCard, "2");
		cards.add(clientCard, "3");

		// setting GameScreen to be the first thing to show up
		layout.show(cards, "2");

		// setting content as default content for this frame

		setContentPane(cards);

		customizeWindow();

		setLocationRelativeTo(null); // center the screen

		// TODO: From Sam. This is not always called successfully...
		//this.requestFocus();
	}

	private void customizeWindow() {
		// window customization
		Image img = Loader.LoadImage("zombie-icon.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1000, 800));
		setResizable(false);

		pack();
		setVisible(true);
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
		if(command.equals(UICommand.ROTATEANTICLOCKWISE.getValue())
				|| command.equals(UICommand.ROTATECLOCKWISE.getValue())) {
			gameCard.rotateView(command);
			return true;
		} else if(command.equals(UICommand.OPTIONS.getValue())) {
			saveGame();
			return true;
		} else if(command.equals(UICommand.LOADGAME.getValue())) {
			loadGame();
			return true;
		} else if(command.equals(UICommand.SINGLEPLAYER.getValue())) {
			singlePlayer();
			return true;
		} else if(command.equals(UICommand.MULTIPLAYER.getValue())) {
			showMultiplayer();
			return true;
		} else if(command.equals(UICommand.SERVER.getValue())) {
			startServer();
			return true;
		} else if(command.equals(UICommand.CLIENT.getValue())) {
			showClient();
			return true;
		} else if(command.equals(UICommand.ENTERIP.getValue())) {
			multiPlayer();
			return true;
		} else if(command.equals(UICommand.BACKPACK.getValue())) {
			showBackpack();
			return true;
		}

		return false;
	}

	/**
	 * This method shows the selection screen for a networked game.
	 */
	private void showMultiplayer() {
		layout.show(cards, "3");
	}

	/**
	 * This method shows the Client connection screen for a networked game.
	 */
	private void showClient() {
		layout.show(cards, "4");
	}

	/**
	 * This method shows the Server screen for a networked game and starts that server running.
	 */
	private void startServer() {

		if(game == null) {
			try {
				game = Parser.ParseMap(Loader.mapFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		layout.show(cards, "5");

		serverCard.startServer(game);
	}

	/**
	 * This gets the entered url, creates a Client and connects it to the server at that url.
	 * It then starts the Clients game running!
	 */
	private void multiPlayer() {
		String ip = clientCard.getIp();

		try {
			Socket socket = new Socket(ip, port);
			System.out.println("Client successfully connected to URL: " + ip + ", port: " + port);

			Client client = new Client(socket, clientClock, this);

			client.setup();
			updateListeners(client);

			gameCard = new GamePanel(client.id(), client.game(), client);

			cards.add(gameCard, "1");

			layout.show(cards, "1");

			client.start();

		} catch (IOException e) {
			System.out.println("I/O error: " + e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * This method starts up a single player game. If a map has been loaded
	 * in, it will use that, otherwise it will load the default map file.
	 */
	private void singlePlayer() {
		if(game == null) {
			try {
				game = Parser.ParseMap(Loader.mapFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int id = game.registerPlayer();

		SinglePlayer player = new SinglePlayer(game, id);

		player.setID(id);
		player.setFrame(this);
		player.setGame(game);
		updateListeners(player);

		gameCard = new GamePanel(id, game, player);

		cards.add(gameCard, "1");

		layout.show(cards, "1");

		Clock clock = new Clock(this, game, gameClock);

		clock.start();
	}

	/**
	 * This method updates the current listeners of the game with the provided
	 * EventListener. This allows a simple listener to be used initially, but a
	 * more complicated one to be substituted in as needed.
	 *
	 * @param listener
	 */
	private void updateListeners(EventListener listener) {
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
	}

	private void showBackpack() {
		InventoryPanel inventory = new InventoryPanel(action, game, 0);
		inventory.showInputDialog("test");
	}

	/**
	 * This method gives the user a file chooser to pick an xml file to load
	 * into the game, replacing whatever world existed there beforehand.
	 * The player can destroy worlds with this method!
	 */
	private void loadGame() {
		JFileChooser chooser = new JFileChooser();
		int value = chooser.showOpenDialog(this);

		if(value == JFileChooser.APPROVE_OPTION) {
			String filename = chooser.getSelectedFile().getName();

			try {
				game = Parser.ParseMap(filename);
			} catch (IOException e) {
				System.out.println("Invalid game file! Try again");
			}
		}
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
