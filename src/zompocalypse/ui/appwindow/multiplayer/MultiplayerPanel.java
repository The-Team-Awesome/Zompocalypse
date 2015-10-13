package zompocalypse.ui.appwindow.multiplayer;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import zompocalypse.datastorage.Loader;
import zompocalypse.ui.appwindow.UICommand;
import zompocalypse.ui.appwindow.custom.CustomUtils;
import zompocalypse.ui.appwindow.custom.ZButton;

/**
 * This panel presents the options for Multiplayer games to the user. Those
 * options are either:
 * <ul>
 * <li>Start Server</li>
 * <li>Connect to Server</li>
 * </ul>
 *
 * @author Sam Costigan
 */
public class MultiplayerPanel extends JPanel {

	private static final long serialVersionUID = -8665486495213250493L;
	private ZButton btnServer;
	private ZButton btnClient;
	private ZButton previousPageButton;

	private static final Image BACKGROUND = Loader.LoadImage("logo.png");

	private ActionListener action;

	public MultiplayerPanel(ActionListener action) {
		super();
		this.action = action;

		arrangeComponents();

		setBackground(CustomUtils.frameBackground);
	}

	/**
	 * Sets and arranges position of components into the content panel.
	 */
	private void arrangeComponents() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		Insets topInset = new Insets(-100, 0, 0, 0);
		Insets buttonsInset = new Insets(0, 0, 40, 0);
		int positionY = 0;

		btnServer = new ZButton("Start Server");
		btnServer.setActionCommand(UICommand.SERVER.getValue());
		btnServer.addActionListener(action);

		btnClient = new ZButton("Connect to Server");
		btnClient.setActionCommand(UICommand.CLIENT.getValue());
		btnClient.addActionListener(action);

		previousPageButton = new ZButton("Home");
		previousPageButton.setActionCommand(UICommand.HOME.getValue());
		previousPageButton.addActionListener(action);

		constraints.insets = topInset;
		constraints.gridy = positionY++;
		this.add(btnServer, constraints);

		constraints.insets = buttonsInset;
		constraints.gridy = positionY++;
		this.add(btnClient, constraints);

		constraints.gridy = positionY++;
		this.add(previousPageButton, constraints);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(CustomUtils.frameBackground);
		g.drawImage(BACKGROUND, 0, 0, null);
	}

}
