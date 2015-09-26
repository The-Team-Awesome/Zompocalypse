package userInterface.appWindow;

import gameWorld.world.World;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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

	public StartPanel(int id, World game, ActionListener action) {
		this.setSize(1000, 1000);
		this.game = game;
		this.action = action;

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
	}
}

