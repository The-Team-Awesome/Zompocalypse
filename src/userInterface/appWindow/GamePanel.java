package userInterface.appWindow;

import gameWorld.World;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import userInterface.renderWindow.RenderPanel;

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
	private RenderPanel renderingPanel;

	// menuPanel components
	private JLabel lblItem;
	private JLabel lblBackpack;
	private JButton btnBackpack;
	private JButton btnUse;
	private JButton btnItemOne;
	private JButton btnItemTwo;
	private JButton btnItemThree;
	private JButton btnNorth;
	private JButton btnSouth;
	private JButton btnEast;
	private JButton btnWest;
	private JButton btnTurnRightView;
	private JButton btnTurnLeftView;

	// dialogPanel components
	private JTextArea txtDialog;

	// icons
	private static final String IMAGE_PATH = "images/";
	private static final Image ITEM = loadImage("sword.png");
	private static final Image NORTH = loadImage("north.png");
	private static final Image SOUTH = loadImage("south.png");
	private static final Image WEST = loadImage("west.png");
	private static final Image EAST = loadImage("east.png");
	private static final Image TURNRIGHT = loadImage("turnRight.png");
	private static final Image TURNLEFT = loadImage("turnLeft.png");

	private World game;

	/**
	 * This will be the listener for all action events which are triggered,
	 * such as button clicks or field entries. For example, when creating a button,
	 * it should be added using button.addActionListener(action);
	 */
	private ActionListener action;

	public GamePanel(int id, World game, ActionListener action) {
		this.setSize(1000, 1000);
		this.game = game;
		this.action = action;

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		renderingPanel = new RenderPanel(0, game, null);  //TODO background img goes here
		renderingPanel.setPreferredSize(new Dimension(800, 600));
		renderingPanel.setBackground(Color.ORANGE);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
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
		txtDialog.setBackground(Color.LIGHT_GRAY);
		txtDialog.setVisible(true);
		dialoguePanel.add(txtDialog);
	}

	/**
	 * Add components to the menuPanel.
	 */
	private void addMenuPanelComponents() {
		this.menuPanel.setLayout(new GridBagLayout());
		GridBagConstraints c =  new GridBagConstraints();
		int positionY = 0;

		ImageIcon itemImage = new ImageIcon();
		// ITEMS
		try {
			itemImage = new ImageIcon(ITEM);
		} catch (Exception e) {
			throw new RuntimeException("Error finding image: "+e);
		}

		Insets bottomInset = new Insets(0, 0, 40, 0);
		Insets generalInset = new Insets(0,0,10,0);

		lblItem = new JLabel(itemImage);
		lblItem.setText("iteeeem");
		c.gridx = 1;
		c.gridy = positionY++;
		c.ipadx = 3;
		c.weightx = 1.0;
		c.insets = bottomInset;
		menuPanel.add(lblItem, c);

		// OPTIONS
		btnBackpack = new JButton("Backpack");
		btnBackpack.addActionListener(action);
		c.gridx = 1;
		c.gridy = positionY++;
		c.ipadx = 2;
		c.insets = generalInset;
		menuPanel.add(btnBackpack, c);

		btnUse = new JButton("Use");
		btnUse.addActionListener(action);
		c.gridx = 1;
		c.gridy = positionY++;
		c.insets = bottomInset;
		menuPanel.add(btnUse, c);

		// BACKPACK HUB
		lblItem = new JLabel("Backpack HUB");
		c.gridx = 1;
		c.gridy = positionY++;
		c.insets = generalInset;
		menuPanel.add(lblItem, c);

		ImageIcon iconItemOne = new ImageIcon(ITEM);
		btnItemOne = new JButton(iconItemOne);
		btnItemOne.addActionListener(action);
		btnItemOne.setActionCommand("ItemOne");
		btnItemOne.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 1;
		c.gridy = positionY++;
		menuPanel.add(btnItemOne, c);

		ImageIcon iconItemTwo = new ImageIcon(ITEM);
		btnItemTwo = new JButton(iconItemTwo);
		btnItemTwo.addActionListener(action);
		btnItemTwo.setActionCommand("ItemTwo");
		btnItemTwo.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 1;
		c.gridy = positionY++;
		menuPanel.add(btnItemTwo, c);

		ImageIcon iconItemThree = new ImageIcon(ITEM);
		btnItemThree = new JButton(iconItemThree);
		btnItemThree.setActionCommand("ItemThree");
		btnItemThree.addActionListener(action);
		btnItemThree.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 1;
		c.gridy = positionY++;
		menuPanel.add(btnItemThree, c);

		// DIRECTIONS
		ImageIcon iconNorth = new ImageIcon(NORTH);
		btnNorth = new JButton(iconNorth);
		btnNorth.setActionCommand("North");
		btnNorth.addActionListener(action);
		btnNorth.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 1;
		c.gridy = positionY++;
		c.insets = new Insets(20, 0, 10, 0);
		menuPanel.add(btnNorth, c);

		Insets westInset = new Insets(0, 0, 10, -50);
		ImageIcon iconWest = new ImageIcon(WEST);
		btnWest = new JButton(iconWest);
		btnWest.setActionCommand("West");;
		btnWest.addActionListener(action);
		btnWest.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 0;
		c.gridy = positionY;
		c.ipadx = 1;
		c.insets = westInset;
		btnWest.setOpaque(false);
		btnWest.setSize(50, 50);
		menuPanel.add(btnWest, c);

		Insets eastInset = new Insets(0, -50, 10, 0);
		ImageIcon iconEast = new ImageIcon(EAST);
		btnEast = new JButton(iconEast);
		btnEast.setActionCommand("East");
		btnEast.addActionListener(action);
		btnEast.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 3;
		c.gridy = positionY++;
		c.ipadx = 1;
		c.insets = eastInset;
		menuPanel.add(btnEast, c);

		c.insets = generalInset;
		ImageIcon iconSouth = new ImageIcon(SOUTH);
		btnSouth = new JButton(iconSouth);
		btnSouth.setActionCommand("South");
		btnSouth.addActionListener(action);
		btnSouth.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 1;
		c.gridy = positionY++;
		c.ipadx = 2;
		menuPanel.add(btnSouth, c);

		ImageIcon iconTurnRightView = new ImageIcon(TURNRIGHT);
		btnTurnRightView = new JButton(iconTurnRightView);
		btnTurnRightView.setActionCommand("ViewToRight");
		btnTurnRightView.addActionListener(action);
		btnTurnRightView.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 0;
		c.gridy = positionY;
		c.ipadx = 1;
		c.insets = westInset;
		menuPanel.add(btnTurnRightView, c);

		ImageIcon iconTurnLeftView = new ImageIcon(TURNLEFT);
		btnTurnLeftView = new JButton(iconTurnLeftView);
		btnTurnLeftView.setActionCommand("ViewToLeft");
		btnTurnLeftView.addActionListener(action);
		btnTurnLeftView.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 3;
		c.gridy = positionY++;
		c.ipadx = 1;
		c.insets = eastInset;
		menuPanel.add(btnTurnLeftView, c);

		minimapPanel = new JPanel();
		minimapPanel.setSize(100, 100);
		minimapPanel.setBackground(Color.cyan);
		c.gridx = 1;
		c.gridy = positionY++;
		c.insets = generalInset;
		menuPanel.add(minimapPanel, c);
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

