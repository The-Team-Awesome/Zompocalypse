package zompocalypse.ui.appwindow.multiplayer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import zompocalypse.controller.RunningServer;
import zompocalypse.gameworld.world.World;

/**
 * The ServerPanel is what a user sees when they start a server running.
 * It creates the server and starts it when appropriate, and relays information
 * to the user from the server when it is received.
 *
 * @author Sam Costigan
 */
public class ServerPanel extends JPanel {

	private JTextArea field;
	private String content = "";

	private RunningServer server;

	public ServerPanel(int port, int numClients, int gameClock, int serverClock) {
		super();

		server = new RunningServer(this, port, numClients, gameClock, serverClock);

		arrangeComponents();
	}

	private void arrangeComponents() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		//Insets topInset = new Insets(400, 0, 0, 0);
		Insets buttonsInset = new Insets(20, 0, 0, 0);
		int positionY = 0;

		field = new JTextArea();
		field.setText(content);
		field.setEditable(false);
		field.setText(content);

		constraints.insets = buttonsInset;
		constraints.gridy = positionY++;
		this.add(field, constraints);
	}

	public void updateContent(String text) {
		content += text + "\n";
		field.setText(content);
	}

	public void startServer(World game) {
		server.startServer(game);
	}

}
