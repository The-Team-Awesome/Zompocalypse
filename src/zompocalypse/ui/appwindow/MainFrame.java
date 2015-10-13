package zompocalypse.ui.appwindow;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.EventListener;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import zompocalypse.controller.Client;
import zompocalypse.controller.Clock;
import zompocalypse.controller.SinglePlayer;
import zompocalypse.datastorage.Loader;
import zompocalypse.datastorage.Parser;
import zompocalypse.datastorage.PlayerFileManager;
import zompocalypse.datastorage.SoundManager;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.items.Container;
import zompocalypse.gameworld.items.Item;
import zompocalypse.gameworld.world.World;
import zompocalypse.ui.appwindow.custom.CustomUtils;
import zompocalypse.ui.appwindow.multiplayer.ClientPanel;
import zompocalypse.ui.appwindow.multiplayer.CustomServerPanel;
import zompocalypse.ui.appwindow.multiplayer.MultiplayerPanel;
import zompocalypse.ui.appwindow.multiplayer.ServerPanel;

/**
 * MainFrame is the main window of the application. It holds the other panels
 * containing the different screens of the game.
 *
 * @author Danielle Emygdio
 *
 */
public class MainFrame extends JFrame implements WindowListener {

	private static final long serialVersionUID = 7712464743432107210L;
	private CardLayout layout;
	private GamePanel gameCard;
	private StartPanel startCard;
	private MultiplayerPanel multiplayerCard;
	private ClientPanel clientCard;
	private ServerPanel serverCard;
	private CustomServerPanel customServerCard;
	private SelectCharacterPanel selectCharacterCard;
	private JPanel cards;
	private World game;
	private boolean multi;

	/**
	 * This will be the listener for all action events which are triggered, such
	 * as button clicks or field entries. For example, when creating a button,
	 * it should be added using button.addActionListener(action);
	 */
	private ActionListener action;

	/**
	 * This information is used for setting up a Networked games' update speed
	 * and any games clock speed, as well as a central place for networking
	 * details.
	 */
	private static final String icon = "zombie-icon.png";
	private int port = 32768;
	private int gameClock = 200;
	private int networkClock = 50;

	/**
	 * Creates a frame without assigning a world. Used for the first time a
	 * frame is called.
	 *
	 * @param listener
	 */
	public MainFrame(EventListener listener) {
		super("Zompocalypse");

		if (listener instanceof ActionListener) {
			action = (ActionListener) listener;
		}

		ContainerPanel.setupContainerPanel(action);

		// creating default panel which uses cards
		layout = new CardLayout();
		cards = new JPanel(layout);

		// start menu and server menu
		startCard = new StartPanel(action);
		multiplayerCard = new MultiplayerPanel(action);
		clientCard = new ClientPanel(action);
		serverCard = new ServerPanel(port, gameClock, networkClock);
		customServerCard = new CustomServerPanel(action);
		selectCharacterCard = new SelectCharacterPanel(action);

		cards.add(startCard, "2");
		cards.add(multiplayerCard, "3");
		cards.add(clientCard, "4");
		cards.add(customServerCard, "5");
		cards.add(serverCard, "6");
		cards.add(selectCharacterCard, "7");

		// setting Start menu to be the first thing to show up
		layout.show(cards, "2");

		SoundManager.playTheme();

		// setting content as default content for this frame
		setContentPane(cards);

		customizeWindow();

		setLocationRelativeTo(null); // center the screen
	}

	/**
	 * Apply settings to this frame.
	 */
	private void customizeWindow() {
		Image img = Loader.LoadImage(icon);
		setIconImage(img);
		// TODO: bring DO_NOTHING_ON_EXIT back when we finish testing
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1000, 750));
		setResizable(false);
		addWindowListener(this);
		setBackground(CustomUtils.frameBackground);
		pack();
		setVisible(true);
	}

	/**
	 * Updates the World for this frame.
	 *
	 * @param game
	 *            - updated instance of World.
	 */
	public void updateGame(World game) {
		this.game = game;
		gameCard.updateGame(game);
	}

	/**
	 * Processes action received or key press.
	 *
	 * @param id
	 * @param command
	 */
	public synchronized void processCommand(int id, String command) {
		if (command.equals(UICommand.OPTIONS.getValue())) {
			saveGame();
		} else if (command.equals(UICommand.LOADGAME.getValue())) {
			loadGame();
		} else if (command.equals(UICommand.SINGLEPLAYER.getValue())) {
			multi = false;
			// singlePlayer("gina");
			selectCharacter();
		} else if (command.equals(UICommand.NEWCHARACTER.getValue())) {
			newCharacter();
		} else if (command.equals(UICommand.LOADCHARACTER.getValue())) {
			loadCharacter();
		} else if (command.equals(UICommand.MULTIPLAYER.getValue())) {
			multi = true;
			showMultiplayer();
		} else if (command.equals(UICommand.HOME.getValue())) {
			layout.show(cards, "2");
		} else if (command.equals(UICommand.SERVER.getValue())) {
			customiseServer();
		} else if (command.equals(UICommand.STARTSERVER.getValue())) {
			startServer();
		} else if (command.equals(UICommand.CLIENT.getValue())) {
			// showClient();
			selectCharacter();
		} else if (command.equals(UICommand.ENTERIP.getValue())) {
			multiPlayer();
		} else if (command.equals(UICommand.BACKPACK.getValue())) {
			showBackpack(id);
		} else if (command.equals(UICommand.ROTATECLOCKWISE.getValue())) {
			gameCard.rotateView(UICommand.ROTATECLOCKWISE.getValue());
		} else if (command.equals(UICommand.ROTATEANTICLOCKWISE.getValue())) {
			gameCard.rotateView(UICommand.ROTATEANTICLOCKWISE.getValue());
		} else if (command.equals(UICommand.OPTIONS.getValue())) {
			saveGame();
		} else if (command.equals(UICommand.SAVEPLAYER.getValue())) {
			savePlayer();
		} else if (command.equals(UICommand.EXAMINE.getValue())) {
			gameCard.examine();
		} else if (command.equals(UICommand.USE.getValue())) {
			use(id);
		}
	}

	/**
	 * Loads a character for the single player game
	 */
	private void loadCharacter() {
		JFileChooser chooser = new JFileChooser();
		int value = chooser.showOpenDialog(this);

		String fileName = null;

		if (value == JFileChooser.APPROVE_OPTION) {
			fileName = chooser.getSelectedFile().getName();
		} else {
			selectCharacter(); // cancel and return ot this page
			return;
		}

		File playerFile = Loader.LoadFile(Loader.playersDir + Loader.separator
				+ fileName);

		if (playerFile == null) {
			JOptionPane.showMessageDialog(null, "Failed to load Player");
			return;
		}

		if (game == null) {
			try {
				game = Parser.ParseMap(Loader.mapFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (multi)
			showClient(); // TODO SAM can you plz help me here? :(
		else {

			Player player = PlayerFileManager.loadPlayer(playerFile, game);

			if (player == null) {
				JOptionPane.showMessageDialog(null, "Failed to load Player");
				return;
			}

			int id = game.registerLoadedPlayer(player);

			SinglePlayer singlePlayer = new SinglePlayer(game, id);

			singlePlayer.setID(id);
			singlePlayer.setFrame(this);
			singlePlayer.setGame(game);
			updateListeners(singlePlayer);

			gameCard = new GamePanel(id, game, singlePlayer);

			cards.add(gameCard, "1");

			layout.show(cards, "1");

			Clock clock = new Clock(this, game, gameClock);

			clock.start();
		}
	}

	/**
	 * This displays a pop-up character selection box.
	 */
	private void newCharacter() {
		Object[] possibilities = { "amy", "bob", "cordi", "duncan",
				"elizabeth", "fred", "gina", "harold" };
		// TODO this works, but I am uncomfortable with these null values!
		Component frame = null;
		Icon icon = null;
		String fileName = (String) JOptionPane.showInputDialog(frame,
				"Pliz choice a peeps", "Choice a peeps",
				JOptionPane.PLAIN_MESSAGE, icon, possibilities,
				"wall_brown_1_door_closed_ew.png");
		if (fileName != null)
			if (multi)
				showClient(); // TODO Sam can you help me with this plz?
			else
				singlePlayer(fileName);
		else
			selectCharacter();
	}

	/**
	 * This method show the selection screen to create a new character or load
	 * an old one.
	 */
	private void selectCharacter() {
		layout.show(cards, "7");
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
	 * This method shows the customize server screen.
	 */
	private void customiseServer() {
		layout.show(cards, "5");
	}

	/**
	 * This method shows the Server screen for a networked game and starts that
	 * server running.
	 */
	private void startServer() {

		int numClients = customServerCard.getNumClients();
		serverCard.setNumClients(numClients);

		if (game == null) {
			try {
				game = Parser.ParseMap(Loader.mapFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		layout.show(cards, "6");

		serverCard.startServer(game);
	}

	/**
	 * This gets the entered url, creates a Client and connects it to the server
	 * at that url. It then starts the Clients game running!
	 */
	private void multiPlayer() {
		String ip = clientCard.getIp();

		try {
			Socket socket = new Socket(ip, port);
			System.out.println("Client successfully connected to URL: " + ip
					+ ", port: " + port);

			Client client = new Client(socket, gameClock, networkClock, this);

			client.setup();
			updateListeners(client);

			gameCard = new GamePanel(client.id(), client.game(), client);

			cards.add(gameCard, "1");

			layout.show(cards, "1");

			client.start();

		} catch (IOException e) {
			System.out.println("IO exception:" + e);
			JOptionPane
					.showMessageDialog(
							this,
							"Couldn't connect to server, check if the IP address is from an existing server!",
							"Oooops!", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * This method starts up a single player game. If a map has been loaded in,
	 * it will use that, otherwise it will load the default map file.
	 *
	 * @param fileName
	 */
	private void singlePlayer(String fileName) {
		if (game == null) {
			try {
				game = Parser.ParseMap(Loader.mapFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int id = game.registerPlayer(fileName);

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
		if (listener instanceof KeyListener) {
			KeyListener key = (KeyListener) listener;
			addKeyListener(key);
		}

		if (listener instanceof MouseListener) {
			MouseListener mouse = (MouseListener) listener;
			addMouseListener(mouse);
		}

		if (listener instanceof ActionListener) {
			action = (ActionListener) listener;
		}

		ContainerPanel.setupContainerPanel(action);
	}

	/**
	 * This method opens up a new message dialog, just like the showBackpack
	 * method, for a unique container with the given id.
	 *
	 * @param id
	 *            - The unique ID of the Container
	 */
	private void use(int id) {
		Player player = (Player) game.getCharacterByID(id);

		for (GameObject o : player.getObjectsInfront()) {
			if (o instanceof Container) {
				showContainer((Container) o, id);
				return;
			}
		}
	}

	/**
	 * This method displays the contents of the backpack for the Player which
	 * belongs to the given id.
	 *
	 * @param id
	 *            - The unique ID of the Player
	 */
	private void showBackpack(int id) {

		Player player = (Player) game.getCharacterByID(id);
		List<Item> objects = player.getInventory();

		ContainerPanel inventory = ContainerPanel.getContainerPanel(objects,
				UICommand.USEITEM.getValue());

		String[] options = { "Drop", "Use" };

		int option = (int) JOptionPane.showOptionDialog(null, inventory,
				player.getName() + "'s Inventory", JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		String command;

		if (option == 0) {
			command = UICommand.BACKPACKDROP.getValue();
		} else {
			command = UICommand.BACKPACKUSE.getValue();
		}

		ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				command);
		action.actionPerformed(event);
	}

	private void showContainer(Container container, int id) {
		ContainerPanel inventory = ContainerPanel.getContainerPanel(
				container.getHeldItems(), UICommand.TAKEITEM.getValue());

		String[] options = { "Take" };
		JOptionPane.showOptionDialog(null, inventory, container.getName(),
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				options, options[0]);

		ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				UICommand.CONTAINER.getValue());
		action.actionPerformed(event);
	}

	/**
	 * This method gives the user a file chooser to pick an xml file to load
	 * into the game, replacing whatever world existed there beforehand. The
	 * player can destroy worlds with this method!
	 */
	private void loadGame() {
		JFileChooser chooser = new JFileChooser();
		int value = chooser.showOpenDialog(this);

		if (value == JFileChooser.APPROVE_OPTION) {
			String filename = chooser.getSelectedFile().getName();

			try {
				game = Parser.ParseMap(filename);
				JOptionPane.showMessageDialog(null,
						"Failed to load Game, default map will be used");
				return;
			} catch (IOException e) {
				System.out.println("Invalid game file! Try again");
			}
		}
	}

	/**
	 * This method shows a dialog to ask the client if he wants to save the
	 * game. In case the client chooses yes, it saves the game. Otherwise, it
	 * does nothing.
	 */
	private void saveGame() {
		Object[] options = { "Yes, please", "No way!" };

		int option = JOptionPane.showOptionDialog(this,
				"Are you sure you wanna save the game?", "Save Game",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, // the titles of buttons
				options[0]); // default button title

		if (option == 0) {
			gameCard.saveGame();
		}
	}

	/**
	 * This method shows a dialog to ask the client if he wants to save the
	 * game. In case the client chooses yes, it saves the game. Otherwise, it
	 * does nothing.
	 */
	private void savePlayer() {
		Object[] options = { "Yes, please", "No way!" };

		int option = JOptionPane.showOptionDialog(this,
				"Are you sure you wanna save the player?", "Save Player",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, // the titles of buttons
				options[0]); // default button title

		if (option == 0) {
			gameCard.savePlayer();
		}
	}

	/**
	 * This method shows a dialog to ask the client if he wants to quit the
	 * game. It disposes the window and exit the application when the player
	 * chooses yes, otherwise it does nothing.
	 */
	private void quitGame() {
		Object[] options = { "Yes, please", "No way!" };

		int option = JOptionPane.showOptionDialog(this,
				"Are you sure you quit the game?", "Quit Game",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, // the titles of buttons
				options[0]); // default button title

		if (option == 0) {
			dispose();
			System.exit(0);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO: bring this method back when we finish testing
		// quitGame();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
