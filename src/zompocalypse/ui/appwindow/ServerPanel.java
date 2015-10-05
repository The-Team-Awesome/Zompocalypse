package zompocalypse.ui.appwindow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import zompocalypse.controller.Clock;
import zompocalypse.controller.RunningServer;
import zompocalypse.controller.Server;
import zompocalypse.gameworld.world.World;

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
		content += "\n" + text;
		field.setText(content);
	}

	public void startServer(World game) {
		server.startServer(game);
	}

}
