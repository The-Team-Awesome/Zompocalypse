package userInterface.appWindow;

import gameWorld.world.World;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
/**
 * InsertServerPanel contains components to the server input screen.
 *
 * @author Danielle Emygdio
 *
 */
public class InsertServerPanel extends JPanel {
	private JTextField txtServerIp;
	private JButton btnEnter;
	private JLabel lblInformation;

	private ActionListener action;
	private World game;
	private int id;

	// IPv4 pattern for JTextField
	static final Pattern pat = Pattern.compile("\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");

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

		// creating and setting components
		lblInformation = new JLabel("Insert IP address of the server:");

		btnEnter = new JButton("Enter");
		btnEnter.addActionListener(action);
		btnEnter.setEnabled(false);

		txtServerIp = new JFormattedTextField();
		txtServerIp.setColumns(20);
		// using listener to watch the user input dynamically
		txtServerIp.getDocument().addDocumentListener(new DocumentListener() {
	            void checkDocument(DocumentEvent e) {
	                try {
	                    String text = e.getDocument().getText(0, e.getDocument().getLength());
	                    // enables button if IP matches pattern
	                    btnEnter.setEnabled(checkIPString(text));
	                } catch (BadLocationException ex) {
	                    //Do something, OK?
	                }
	            }
	            public void insertUpdate(DocumentEvent e) {
	                checkDocument(e);
	            }
	            public void removeUpdate(DocumentEvent e) {
	                checkDocument(e);
	            }
	            public void changedUpdate(DocumentEvent e) {
	                checkDocument(e);
	            }
	        });

		// verifies if the pattern is matching the IP pattern
		 txtServerIp.setInputVerifier(new InputVerifier() {
	            public boolean shouldYieldFocus(JComponent input) {
	                boolean inputOK = verify(input);
	                if (inputOK) {
	                    return true;
	                } else {
	                    Toolkit.getDefaultToolkit().beep();
	                    return false;
	                }
	            }
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

	/**
	 * Verifies the in put string matches IPv4 pattern.
	 * @param s
	 * @return true when the input string matches the IPv4 pattern.
	 */
	static boolean checkIPString(String s) {
        Matcher m = pat.matcher(s);
        return m.matches();
    }
}
