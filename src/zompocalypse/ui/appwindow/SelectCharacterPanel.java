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
 * SelectCharacterPanel lets you create a new player or load an old one.
 *
 * @author Danielle Emygdio & David Thomsen
 *
 */
public class SelectCharacterPanel extends JPanel {
	private ZButton newCharacterButton;
	private ZButton loadCharacterButton;
	private ZButton previousPageButton;

	private ActionListener action;

	private static final Image BACKGROUND = Loader
			.LoadImage("logo.png");

	public SelectCharacterPanel(ActionListener action) {
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
		Insets topInset = new Insets(400, 0, 0, 0);
		Insets buttonsInset = new Insets(20, 0, 0, 0);
		int positionY = 0;

		// NEW CHARACTER BUTTON
		newCharacterButton = new ZButton("New Character");
		newCharacterButton.setActionCommand(UICommand.NEWCHARACTER.getValue());
		newCharacterButton.addActionListener(action);
		c.insets = topInset;
		c.gridy = positionY++;
		this.add(newCharacterButton, c);

		// LOAD CHARACTER BUTTON
		loadCharacterButton = new ZButton("Load Character");
		loadCharacterButton.setActionCommand(UICommand.LOADCHARACTER.getValue());
		c.insets = buttonsInset;
		c.gridy = positionY++;
		loadCharacterButton.addActionListener(action);
		this.add(loadCharacterButton, c);

		// BACK A PAGE BUTTON
		previousPageButton = new ZButton("Home");
		previousPageButton.setActionCommand(UICommand.HOME.getValue());
		c.insets = buttonsInset;
		c.gridy = positionY++;
		previousPageButton.addActionListener(action);
		this.add(previousPageButton, c);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(CustomUtils.frameBackground);
		g.drawImage(BACKGROUND, 0, 0, null);
	}
}
