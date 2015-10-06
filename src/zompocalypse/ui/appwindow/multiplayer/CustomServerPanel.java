package zompocalypse.ui.appwindow.multiplayer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import zompocalypse.ui.appwindow.UICommand;

public class CustomServerPanel extends JPanel {
	
	JLabel clientsLabel;
	JFormattedTextField clients;
	JButton start;
	
	ActionListener action;
	
	public CustomServerPanel(ActionListener action) {
		super();
		
		this.action = action;
		
		arrangeComponents();
	}
	
	private void arrangeComponents() {
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		//Insets topInset = new Insets(400, 0, 0, 0);
		Insets buttonsInset = new Insets(20, 0, 0, 0);
		int positionY = 0;
		
		NumberFormat clientFormat = NumberFormat.getNumberInstance();
		
		clientsLabel = new JLabel("Enter the number of people to play:");
		
		clients = new JFormattedTextField(clientFormat);
		clients.setValue(new Integer(1));
		clients.setColumns(8);
		
		start = new JButton("Start!");
		start.setActionCommand(UICommand.STARTSERVER.getValue());
		start.addActionListener(action);
		
		constraints.insets = buttonsInset;
		constraints.gridy = positionY++;
		this.add(clientsLabel, constraints);
		
		constraints.gridy = positionY++;
		this.add(clients, constraints);
		
		constraints.gridy = positionY++;
		this.add(start, constraints);
	}
	
	public int getNumClients() {
		return Integer.parseInt(clients.getText());
	}
}
