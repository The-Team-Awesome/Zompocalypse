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
	private JTextField txtServerIp;
	private ZButton btnEnter;
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

		// creating and setting components
		lblInformation = new JLabel("Insert IP address of the server:");

		btnEnter = new ZButton("Enter");
		btnEnter.setActionCommand(UICommand.ENTERIP.getValue());
		btnEnter.addActionListener(action);
		btnEnter.setEnabled(false);

		txtServerIp = new JFormattedTextField();
		txtServerIp.setColumns(20);
		// using listener to watch the user input dynamically
		txtServerIp.getDocument().addDocumentListener(new DocumentListener() {
			void checkDocument(DocumentEvent e) {
				try {
					String text = e.getDocument().getText(0,
							e.getDocument().getLength());
					// enables button if IP matches pattern
					btnEnter.setEnabled(checkIPString(text));
				} catch (BadLocationException ex) {
					// Do something, OK?
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

		// adding components to panel
		constraints.insets = buttonsInset;
		constraints.gridy = positionY++;
		this.add(lblInformation, constraints);

		constraints.gridy = positionY++;
		this.add(txtServerIp, constraints);

		constraints.gridy = positionY++;
		this.add(btnEnter, constraints);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(CustomUtils.frameBackground);
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
