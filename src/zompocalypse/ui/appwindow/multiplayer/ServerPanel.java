package zompocalypse.ui.appwindow.multiplayer;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

	public ServerPanel(int port, int gameClock, int serverClock) {
		super();

		server = new RunningServer(this, port, gameClock, serverClock);

		arrangeComponents();
	}

	private void arrangeComponents() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		Insets buttonsInset = new Insets(20, 0, 0, 0);
		int positionY = 0;

		field = new JTextArea();
		field.setEditable(false);
		field.setText(content);

		JScrollPane scroller = new JScrollPane(field);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setPreferredSize(new Dimension(800, 600));

		constraints.insets = buttonsInset;
		constraints.gridy = positionY++;
		this.add(scroller, constraints);
	}

	public void setNumClients(int numClients) {
		server.updateNumClients(numClients);
	}

	public void updateContent(String text) {
		content += text + "\n";
		field.setText(content);
	}

	public void startServer(World game) {
		server.startServer(game);
	}

}
