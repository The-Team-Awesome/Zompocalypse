package zompocalypse.ui.appwindow.multiplayer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

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

	private ZButton btnServer;
	private ZButton btnClient;

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
		Insets buttonsInset = new Insets(20, 0, 0, 0);
		int positionY = 0;

		btnServer = new ZButton("Start Server");
		btnServer.setActionCommand(UICommand.SERVER.getValue());
		btnServer.addActionListener(action);

		btnClient = new ZButton("Connect to Server");
		btnClient.setActionCommand(UICommand.CLIENT.getValue());
		btnClient.addActionListener(action);

		constraints.insets = buttonsInset;
		constraints.gridy = positionY++;
		this.add(btnServer, constraints);

		constraints.gridy = positionY++;
		this.add(btnClient, constraints);
	}

}
