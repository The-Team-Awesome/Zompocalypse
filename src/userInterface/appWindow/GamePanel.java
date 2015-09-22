package userInterface.appWindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import userInterface.renderWindow.Renderer;

/**
 * Contains the components for the main game screen.
 * @author Danielle Emygdio
 *
 */
public class GamePanel extends JPanel {
	// Main panels
	private JPanel dialoguePanel;
	private JPanel menuPanel;
	private JPanel minimapPanel;
	private JPanel renderingPanel;
	private Renderer renderer;

	// menuPanel panels
	private JPanel itemsPanel;
	private JPanel optionsPanel;
	private JPanel backpackPanel;
	private JPanel directionsPanel;

	// menuPanel components
	private JLabel imgItem;
	private JLabel txtItem;
	private JButton btnBackpack;
	private JButton btnUse;
	private JButton btnItemOne;
	private JButton btnItemTwo;
	private JButton btnItemThree;
	private JButton btnNorth;
	private JButton btnSouth;
	private JButton btnEast;
	private JButton btnWest;

	// dialogPanel components
	private JTextArea txtDialog;

	// icons
	private static final String IMAGE_PATH = "images/";
	private static final Image ITEM = loadImage("sword.png");
	private static final Image NORTH = loadImage("north.png");
	private static final Image SOUTH = loadImage("south.png");
	private static final Image WEST = loadImage("west.png");
	private static final Image EAST = loadImage("east.png");

	public GamePanel() {
		this.setSize(1000, 1000);

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		renderingPanel = new JPanel();
		renderingPanel.setPreferredSize(new Dimension(800, 600));
		renderingPanel.setBackground(Color.ORANGE);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		renderer = new Renderer(renderingPanel);
		this.add(renderingPanel, c);

		menuPanel = new JPanel();
		menuPanel.setPreferredSize(new Dimension(150, 700));
		menuPanel.setBackground(Color.MAGENTA);
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 2;
		c.gridx = 1;
		c.gridy = 0;
		addMenuPanelComponents();
		this.add(menuPanel, c);

		dialoguePanel = new JPanel();
		dialoguePanel.setPreferredSize(new Dimension(800, 100));
		dialoguePanel.setBackground(Color.green);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		addDialogPanelComponents();
		this.add(dialoguePanel, c);
	}

	/**
	 * Add components to the dialogPanel.
	 */
	private void addDialogPanelComponents() {
		txtDialog = new JTextArea(4, 50);
		txtDialog.setEditable(false);
		txtDialog.setVisible(true);
		dialoguePanel.add(txtDialog);
	}

	/**
	 * Add components to the menuPanel.
	 */
	private void addMenuPanelComponents() {
		this.menuPanel.setLayout(new GridBagLayout());
		GridBagConstraints c =  new GridBagConstraints();

		JPanel backpackPanel = new JPanel();
		JPanel directionsPanel = new JPanel();

		// ITEMS
		arrangeItemsPanel();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 50;
		c.gridwidth = 50;
		menuPanel.add(itemsPanel, c);

		// OPTIONS
		arrangeOptionsPanel();
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 100;
		c.gridwidth = 50;
		menuPanel.add(optionsPanel, c);
/*
		// TODO: BACKPACK HUB
		ImageIcon iconItemOne = new ImageIcon(ITEM);
		btnItemOne = new JButton(iconItemOne);
		btnItemOne.setBorder(BorderFactory.createEmptyBorder());
		menuPanel.add(btnItemOne);

		ImageIcon iconItemTwo = new ImageIcon(ITEM);
		btnItemTwo = new JButton(iconItemTwo);
		btnItemTwo.setBorder(BorderFactory.createEmptyBorder());
		menuPanel.add(btnItemTwo);

		ImageIcon iconItemThree = new ImageIcon(ITEM);
		btnItemThree = new JButton(iconItemThree);
		btnItemThree.setBorder(BorderFactory.createEmptyBorder());
		menuPanel.add(btnItemThree);

		// TODO: DIRECTIONS
		ImageIcon iconNorth = new ImageIcon(NORTH);
		btnNorth = new JButton(iconNorth);
		btnNorth.setBorder(BorderFactory.createEmptyBorder());
		menuPanel.add(btnNorth);

		ImageIcon iconSouth = new ImageIcon(SOUTH);
		btnSouth = new JButton(iconSouth);
		btnSouth.setBorder(BorderFactory.createEmptyBorder());
		menuPanel.add(btnSouth);

		ImageIcon iconEast = new ImageIcon(EAST);
		btnEast = new JButton(iconEast);
		btnEast.setBorder(BorderFactory.createEmptyBorder());
		menuPanel.add(btnEast);

		ImageIcon iconWest = new ImageIcon(WEST);
		btnWest = new JButton(iconWest);
		btnWest.setBorder(BorderFactory.createEmptyBorder());
		btnWest.setOpaque(false);
		btnWest.setSize(50, 50);
		menuPanel.add(btnWest);*/
	}

	private void arrangeItemsPanel() {
		itemsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		try {
			imgItem = new JLabel(new ImageIcon(ITEM));
			c.ipadx = 0;
			c.ipady = 0;
			itemsPanel.add(imgItem, c);

			txtItem = new JLabel("icon");
			c.ipadx = 1;
			c.ipady = 0;
			itemsPanel.add(txtItem, c);
		} catch (Exception e) {
			throw new RuntimeException("Error finding image: "+e);
		}

		itemsPanel.setSize(150, 300);
	}

	private void arrangeOptionsPanel() {
		optionsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		btnBackpack = new JButton("Backpack");
		c.ipadx = 0;
		c.ipady = 0;
		optionsPanel.add(btnBackpack, c);

		btnUse = new JButton("Use");
		c.ipadx = 0;
		c.ipady = 1;
		optionsPanel.add(btnUse, c);

		optionsPanel.setSize(150, 300);
		optionsPanel.setBackground(Color.RED);
	}

	/**
	 * Load an image from the file system, using a given filename.
	 *
	 * @param filename
	 * @return imageloaded
	 */
	public static Image loadImage(String filename) {
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.
		System.out.println(GamePanel.class.getResource(IMAGE_PATH
				+ filename));
		java.net.URL imageURL = GamePanel.class.getResource(IMAGE_PATH
				+ filename);

		try {
			Image img = ImageIO.read(imageURL);
			return img;
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + filename);
		}
	}
}

