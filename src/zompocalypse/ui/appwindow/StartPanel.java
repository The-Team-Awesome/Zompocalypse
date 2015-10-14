package zompocalypse.ui.appwindow;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import zompocalypse.datastorage.Loader;
import zompocalypse.ui.appwindow.custom.CustomUtils;
import zompocalypse.ui.appwindow.custom.ZButton;

/**
 * StartPanel contains the components for the start screen of the game.
 *
 * @author Danielle Emygdio
 *
 */
public class StartPanel extends JPanel {

	private static final long serialVersionUID = -4214067156057894477L;
	private ZButton singlePlayerButton;
	private ZButton multiplayerButton;
	private ZButton loadGameButton;

	private ActionListener action;

	private static final Image BACKGROUND = Loader.LoadImage("logo.png");

	public StartPanel(ActionListener action) {
		this.setSize(1000, 1000);
		this.action = action;

		arrangeComponents();
	}

	/**
	 * Sets and arranges position of components into the content panel.
	 */
	private void arrangeComponents() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		Insets topInset = new Insets(-100, 0, 0, 0);
		Insets buttonsInset = new Insets(0, 0, 40, 0);
		int positionY = 0;

		// SINGLE PLAYER BUTTON
		singlePlayerButton = new ZButton("Single Player");
		singlePlayerButton.setActionCommand(UICommand.SINGLEPLAYER.getValue());
		singlePlayerButton.addActionListener(action);
		c.insets = topInset;
		c.gridy = positionY++;
		this.add(singlePlayerButton, c);

		// MULTIPLAYER PLAYER BUTTON
		multiplayerButton = new ZButton("Multiplayer");
		multiplayerButton.setActionCommand(UICommand.MULTIPLAYER.getValue());
		c.insets = buttonsInset;
		c.gridy = positionY++;
		multiplayerButton.addActionListener(action);
		this.add(multiplayerButton, c);

		// LOAD GAME BUTTON
		loadGameButton = new ZButton("Load game");
		loadGameButton.setActionCommand(UICommand.LOADGAME.getValue());
		loadGameButton.addActionListener(action);
		c.gridy = positionY++;
		c.insets = buttonsInset;
		this.add(loadGameButton, c);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(CustomUtils.yellowTwo);
		g.drawImage(BACKGROUND, 0, 0, null);
	}
}
