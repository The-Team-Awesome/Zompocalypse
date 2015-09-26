package userInterface.appWindow;

import gameWorld.world.World;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * StartPanel constains the components for the start
 * screen of the game.
 *
 * @author Danielle Emygdio
 *
 */
public class StartPanel extends JPanel {
	private JButton singlePlayerButton;
	private JButton multiplayerButton;
	private JButton loadGameButton;
	private JButton newGameButton;

	private ActionListener action;
	private World game;
	private int id;

	private static final String IMAGE_PATH = "assets/";
	private static final Image BACKGROUND = loadImage("background02.jpg");

	public StartPanel(int id, World game, ActionListener action) {
		this.setSize(1000, 1000);
		this.game = game;
		this.action = action;
		this.id = id;

		addComponentsToPane(this);
	}

	private void addComponentsToPane(Container pane) {
		pane.setLayout(new GridLayout(3,3));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setSize(400, 300);
		pane.add(buttonsPanel);

		// SINGLE PLAYER BUTTON
		singlePlayerButton = new JButton("Single Player");
		singlePlayerButton.addActionListener(action);
		buttonsPanel.add(singlePlayerButton);

		// MULTIPLAYER PLAYER BUTTON
		multiplayerButton = new JButton("Multiplayer");
		multiplayerButton.addActionListener(action);
		buttonsPanel.add(multiplayerButton);

		// LOAD GAME BUTTON
		newGameButton = new JButton("New game");
		newGameButton.addActionListener(action);
		buttonsPanel.add(newGameButton);

		// LOAD GAME BUTTON
		loadGameButton = new JButton("Load game");
		loadGameButton.addActionListener(action);
		buttonsPanel.add(loadGameButton);

		/*ImageIcon icon = new ImageIcon(BACKGROUND);
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);
		buttonsPanel.add(thumb);*/
	}

	@Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	        g.drawImage(BACKGROUND, 0, 0, null);
	}

	/**
	 * Load an image from the file system, using a given filename.
	 *
	 * @param filename
	 * @return imageloaded
	 */
	public static Image loadImage(String filename) {
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.
		System.out.println(GamePanel.class.getResource("../../"+IMAGE_PATH
				+ filename));
		java.net.URL imageURL = GamePanel.class.getResource("../../"+IMAGE_PATH
				+ filename);

		try {
			Image img = ImageIO.read(imageURL);
			return img;
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + filename);
		}
	}
}

