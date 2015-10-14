package zompocalypse.ui.appwindow.multiplayer;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import zompocalypse.datastorage.Loader;
import zompocalypse.ui.appwindow.UICommand;
import zompocalypse.ui.appwindow.custom.CustomUtils;
import zompocalypse.ui.appwindow.custom.ZButton;

/**
 * Interface for client to input how many players are gonna be accepted for the
 * starting server.
 *
 * @author Danielle Emygdio and Sam Costigan
 *
 */
public class CustomServerPanel extends JPanel {

	private static final long serialVersionUID = 3418853607101157836L;
	JLabel clientsLabel;
	JFormattedTextField clients;
	ZButton start;
	ZButton homeButton;

	private static final Image BACKGROUND = Loader.LoadImage("logo.png");

	ActionListener action;

	public CustomServerPanel(ActionListener action) {
		super();

		this.action = action;

		arrangeComponents();

		setBackground(CustomUtils.yellowTwo);
	}

	/**
	 * Sets and arranges position of components into the content panel.
	 */
	private void arrangeComponents() {
		this.setLayout(new GridBagLayout());

		NumberFormat clientFormat = NumberFormat.getNumberInstance();

		clientsLabel = new JLabel("Enter the number of people to play:");
		clientsLabel.setForeground(CustomUtils.white);

		clients = new JFormattedTextField(clientFormat);
		clients.setValue(new Integer(1));
		clients.setColumns(8);

		start = new ZButton("Start!");
		start.setActionCommand(UICommand.STARTSERVER.getValue());
		start.addActionListener(action);

		homeButton = new ZButton("Home");
		homeButton.setActionCommand(UICommand.HOME.getValue());
		homeButton.addActionListener(action);

		GridBagConstraints constraints = new GridBagConstraints();
		Insets buttonsInset = new Insets(-10, 0, 30, 0);
		Insets topInset = new Insets(-100, 0, 0, 0);
		int positionY = 0;

		constraints.insets = topInset;
		constraints.gridy = positionY++;
		this.add(clientsLabel, constraints);

		constraints.insets = buttonsInset;
		constraints.gridy = positionY++;
		this.add(clients, constraints);

		constraints.gridy = positionY++;
		this.add(start, constraints);

		constraints.gridy = positionY++;
		this.add(homeButton, constraints);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(CustomUtils.yellowTwo);
		g.drawImage(BACKGROUND, 0, 0, null);
	}

	/**
	 * Gets the number of clients from the text input.
	 *
	 * @return number of clients.
	 */
	public int getNumClients() {
		return Integer.parseInt(clients.getText());
	}
}
