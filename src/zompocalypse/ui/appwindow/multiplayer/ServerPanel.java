package zompocalypse.ui.appwindow.multiplayer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import zompocalypse.controller.RunningServer;
import zompocalypse.datastorage.Loader;
import zompocalypse.datastorage.SoundManager;
import zompocalypse.gameworld.world.World;
import zompocalypse.ui.appwindow.custom.CustomUtils;

/**
 * The ServerPanel is what a user sees when they start a server running.
 * It creates the server and starts it when appropriate, and relays information
 * to the user from the server when it is received.
 *
 * @author Sam Costigan
 */
public class ServerPanel extends JPanel {

	private static final long serialVersionUID = 8015424305325191781L;
	private JTextArea field;
	private String content = "";

	private static final Image BACKGROUND = Loader.LoadImage("logo.png");

	private RunningServer server;

	public ServerPanel(int port, int gameClock, int serverClock) {
		super();

		server = new RunningServer(this, port, gameClock, serverClock);

		arrangeComponents();

		setBackground(CustomUtils.frameBackground);
	}

	/**
	 * Arranges position and set components into the content panel.
	 */
	private void arrangeComponents() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		Insets buttonsInset = new Insets(20, 0, 0, 0);
		int positionY = 0;

		field = new JTextArea();
		field.setEditable(false);
		field.setText(content);

		JScrollPane scroller = new JScrollPane(field);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setPreferredSize(new Dimension(800, 600));

		constraints.insets = buttonsInset;
		constraints.gridy = positionY++;
		this.add(scroller, constraints);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(CustomUtils.frameBackground);
		g.drawImage(BACKGROUND, 0, 0, null);
	}

	/**
	 * Send the number of clients to the server.
	 * @param numClients - number of clients.
	 */
	public void setNumClients(int numClients) {
		server.updateNumClients(numClients);
	}

	/**
	 * Updates the content on the text area.
	 * @param text
	 */
	public void updateContent(String text) {
		content += text + "\n";
		field.setText(content);
	}

	/**
	 * Starts a server.
	 * @param game - current game.
	 */
	public void startServer(World game) {
		SoundManager.stopTheme();
		server.startServer(game);
	}

}
