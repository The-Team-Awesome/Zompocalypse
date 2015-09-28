package userInterface.appWindow;

import gameWorld.world.World;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import dataStorage.Loader;

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

	private static final Image BACKGROUND = Loader.LoadImage("background02.jpg");

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
}

