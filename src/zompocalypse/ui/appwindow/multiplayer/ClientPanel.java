package zompocalypse.ui.appwindow.multiplayer;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import zompocalypse.datastorage.Loader;
import zompocalypse.ui.appwindow.UICommand;
import zompocalypse.ui.appwindow.custom.CustomUtils;
import zompocalypse.ui.appwindow.custom.ZButton;

/**
 * ClientPanel contains components to the server input screen.
 *
 * @author Danielle Emygdio
 *
 */
public class ClientPanel extends JPanel {

	private static final long serialVersionUID = -5774750842625075768L;
	private JTextField txtServerIp;
	private ZButton btnEnter;
	private ZButton homeButton;
	private JLabel lblInformation;

	private static final Image BACKGROUND = Loader.LoadImage("logo.png");

	private ActionListener action;

	// IPv4 pattern for JTextField
	static final Pattern pat = Pattern
			.compile("\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
					+ "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
					+ "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
					+ "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");

	public ClientPanel(ActionListener action) {
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

		// creating and setting components
		lblInformation = new JLabel("Insert IP address of the server:");
		lblInformation.setForeground(CustomUtils.white);

		btnEnter = new ZButton("Enter");
		btnEnter.setActionCommand(UICommand.ENTERIP.getValue());
		btnEnter.addActionListener(action);
		btnEnter.setEnabled(true);

		homeButton = new ZButton("Home");
		homeButton.setActionCommand(UICommand.HOME.getValue());
		homeButton.addActionListener(action);
		homeButton.setEnabled(true);

		txtServerIp = new JFormattedTextField();
		txtServerIp.setColumns(20);
		txtServerIp.setText("127.0.0.1");

		// using listener to watch the user input dynamically
		txtServerIp.getDocument().addDocumentListener(new DocumentListener() {
			void checkDocument(DocumentEvent e) {
				try {
					String text = e.getDocument().getText(0,
							e.getDocument().getLength());
					// enables button if IP matches pattern
					btnEnter.setEnabled(checkIPString(text));
				} catch (BadLocationException ex) {

				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				checkDocument(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkDocument(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				checkDocument(e);
			}
		});

		// verifies if the pattern is matching the IP pattern
		txtServerIp.setInputVerifier(new InputVerifier() {
			@Override
			public boolean shouldYieldFocus(JComponent input) {
				boolean inputOK = verify(input);
				if (inputOK) {
					return true;
				} else {
					Toolkit.getDefaultToolkit().beep();
					return false;
				}
			}

			@Override
			public boolean verify(JComponent input) {
				JTextField field = (JTextField) input;
				return checkIPString(field.getText());
			}
		});

		GridBagConstraints constraints = new GridBagConstraints();
		Insets buttonsInset = new Insets(-10, 0, 30, 0);
		Insets topInset = new Insets(-100, 0, 0, 0);
		int positionY = 0;

		// adding components to panel
		constraints.insets = topInset;
		constraints.gridy = positionY++;
		this.add(lblInformation, constraints);

		constraints.insets = buttonsInset;
		constraints.gridy = positionY++;
		this.add(txtServerIp, constraints);

		constraints.gridy = positionY++;
		this.add(btnEnter, constraints);

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
	 * Gets the IP input in the text area.
	 *
	 * @return IP address inserted.
	 */
	public String getIp() {
		return txtServerIp.getText();
	}

	/**
	 * Verifies the in put string matches IPv4 pattern.
	 *
	 * @param s
	 * @return true when the input string matches the IPv4 pattern.
	 */
	static boolean checkIPString(String s) {
		Matcher m = pat.matcher(s);
		return m.matches();
	}
}
