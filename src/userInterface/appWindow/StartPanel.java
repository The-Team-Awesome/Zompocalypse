package userInterface.appWindow;

import gameWorld.world.World;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;

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

	private static final Image BACKGROUND = Loader.LoadImage("background03.png");

	public StartPanel(int id, World game, ActionListener action) {
		this.setSize(1000, 1000);
		this.game = game;
		this.action = action;
		this.id = id;

		addComponentsToPane(this);
	}

	private void addComponentsToPane(Container pane) {
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		Insets topInset = new Insets(400, 0, 0, 0);
		Insets buttonsInset = new Insets(20, 0, 0, 0);
		int positionY = 0;

		// SINGLE PLAYER BUTTON
		singlePlayerButton = new JButton("Single Player");
		singlePlayerButton.addActionListener(action);
		c.insets = topInset;
		c.gridy = positionY++;
		pane.add(singlePlayerButton, c);

		// MULTIPLAYER PLAYER BUTTON
		multiplayerButton = new JButton("Multiplayer");
		c.insets = buttonsInset;
		c.gridy = positionY++;
		multiplayerButton.addActionListener(action);
		pane.add(multiplayerButton, c);

		// LOAD GAME BUTTON
		loadGameButton = new JButton("Load game");
		loadGameButton.addActionListener(action);
		c.gridy = positionY++;
		pane.add(loadGameButton, c);
	}

	@Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	        g.drawImage(BACKGROUND, 0, 0, null);
	}
}

