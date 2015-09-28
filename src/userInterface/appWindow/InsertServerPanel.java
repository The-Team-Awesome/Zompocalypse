package userInterface.appWindow;

import gameWorld.world.World;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InsertServerPanel extends JPanel {
	private JTextField txtServerIp;
	private JButton btnEnter;
	private JLabel lblInformation;

	private ActionListener action;
	private World game;
	private int id;

	public InsertServerPanel(int id, World game, ActionListener action) {
		super();
		this.action = action;
		this.game = game;
		this.id = id;

		arrangeComponents();
	}

	private void arrangeComponents() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		//Insets topInset = new Insets(400, 0, 0, 0);
		Insets buttonsInset = new Insets(20, 0, 0, 0);
		int positionY = 0;

		lblInformation = new JLabel("Insert IP address of the server:");
		constraints.insets = buttonsInset;
		constraints.gridy = positionY++;
		this.add(lblInformation, constraints);

		txtServerIp = new JTextField();
		txtServerIp.setColumns(20);
		constraints.gridy = positionY++;
		this.add(txtServerIp, constraints);

		btnEnter = new JButton("Enter");
		constraints.gridy = positionY++;
		this.add(btnEnter, constraints);
	}
}
