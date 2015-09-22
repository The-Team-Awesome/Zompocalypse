package userInterface.appWindow;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
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

	public StartPanel() {
		addComponentsToPane(this);
	}

	private void addComponentsToPane(Container pane) {
		pane.setLayout(new GridLayout(3,3));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setSize(400, 300);
		pane.add(buttonsPanel);

		// SINGLE PLAYER BUTTON
		singlePlayerButton = new JButton("Single Player");
		singlePlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		buttonsPanel.add(singlePlayerButton);

		// MULTIPLAYER PLAYER BUTTON
		multiplayerButton = new JButton("Multiplayer");
		multiplayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		buttonsPanel.add(multiplayerButton);

		// LOAD GAME BUTTON
		newGameButton = new JButton("New game");
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		buttonsPanel.add(newGameButton);

		// LOAD GAME BUTTON
		loadGameButton = new JButton("Load game");
		loadGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		buttonsPanel.add(loadGameButton);
	}
}

