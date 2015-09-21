package userInterface.appWindow;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.EventListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import userInterface.renderWindow.Renderer;

public class Gui extends JFrame {

	/**
	 * This will be the listener for all action events which are triggered,
	 * such as button clicks or field entries. For example, when creating a button,
	 * it should be added using button.addActionListener(action);
	 */
	private ActionListener action;
	private JPanel renderingPanel;
	private JPanel dialoguePanel;
	private JPanel menuPanel;
	private JPanel minimapPanel;
	private Renderer renderer;

	// dialog panel
	private JTextArea txtDialog;

	// menu panel


	public Gui(String title, int id, EventListener listener) {
		super(title);

		// Set up the given EventListener to process Key, Mouse and Action events
		if(listener instanceof KeyListener) {
			KeyListener key = (KeyListener) listener;
			this.addKeyListener(key);
		}

		if(listener instanceof MouseListener) {
			MouseListener mouse = (MouseListener) listener;
			this.addMouseListener(mouse);
		}

		if(listener instanceof ActionListener) {
			action = (ActionListener) listener;
		}

		/*JPanel renderPanel = new JPanel();
		renderPanel.setPreferredSize(new Dimension(800, 600));

		JButton button = new JButton("Test");
		button.setActionCommand("test");
		button.addActionListener(action);

		renderPanel.add(button);

		Renderer r = new Renderer(renderPanel);


		getContentPane().add(renderPanel,  BorderLayout.CENTER);*/

		arrangeLayout(getContentPane());

		// Window customization
		ImageIcon img = new ImageIcon("img/zombie-icon.png");
		setIconImage(img.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 1000);
		this.setLocationRelativeTo(null); // center the screen

		pack();
		setVisible(true);
	}

	private void arrangeLayout(Container panel) {
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		renderingPanel = new JPanel();
		renderingPanel.setPreferredSize(new Dimension(800, 600));
		renderingPanel.setBackground(Color.ORANGE);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		renderer = new Renderer(renderingPanel);
		panel.add(renderingPanel, c);

		menuPanel = new JPanel();
		menuPanel.setPreferredSize(new Dimension(150, 700));
		menuPanel.setBackground(Color.MAGENTA);
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 2;
		c.gridx = 1;
		c.gridy = 0;
		panel.add(menuPanel, c);

		dialoguePanel = new JPanel();
		dialoguePanel.setPreferredSize(new Dimension(800, 100));
		dialoguePanel.setBackground(Color.green);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		setDialogPanelComponents();
		panel.add(dialoguePanel, c);
	}

	private void setDialogPanelComponents() {
		this.txtDialog = new JTextArea(4, 50);
		this.txtDialog.setEditable(false);
		this.txtDialog.setVisible(true);
		dialoguePanel.add(txtDialog);
	}

	/*private void setMenuPanelComponents() {
		//BufferedImage myPicture = ImageIO.read(new File("path-to-file"));
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		add(picLabel);
	}*/

}
