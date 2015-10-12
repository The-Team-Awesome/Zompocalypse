package zompocalypse.ui.appwindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.Direction;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.characters.Player;
import zompocalypse.gameworld.items.Item;
import zompocalypse.gameworld.world.World;
import zompocalypse.ui.appwindow.custom.CustomUtils;
import zompocalypse.ui.appwindow.custom.ZButton;
import zompocalypse.ui.rendering.RenderPanel;

/**
 * Contains the components for the main game screen.
 * @author Danielle Emygdio
 *
 */
public class GamePanel extends JPanel {
	// Main panels
	private JPanel dialoguePanel;
	private JPanel menuPanel;
	private RenderPanel renderingPanel;

	// menuPanel components
	private ImageIcon itemImage;
	private JLabel lblItem;
	private ZButton btnBackpack;
	private ZButton btnUse;
	private ZButton btnNorth;
	private ZButton btnSouth;
	private ZButton btnEast;
	private ZButton btnWest;
	private ZButton btnRotateClockwise;
	private ZButton btnRotateAnticlockwise;
	private ZButton btnOptions;
	private JProgressBar progressDamage;
	private ZButton btnExamine;
	private JLabel lblScore;

	// dialogPanel components
	private JTextArea txtDialog;

	// icons
	private static final Image ITEM = Loader.LoadIcon("sword.png");
	private static final Image NORTH = Loader.LoadIcon("north.png");
	private static final Image SOUTH = Loader.LoadIcon("south.png");
	private static final Image WEST = Loader.LoadIcon("west.png");
	private static final Image EAST = Loader.LoadIcon("east.png");
	private static final Image CLOCKWISE = Loader.LoadIcon("turnClockwise.png");
	private static final Image ANTICLOCKWISE = Loader.LoadIcon("turnAnticlockwise.png");

	/**
	 * This will be the listener for all action events which are triggered,
	 * such as button clicks or field entries. For example, when creating a button,
	 * it should be added using button.addActionListener(action);
	 */
	private ActionListener action;
	private World game;
	private int id;
	private Player player;
	private String dialog = "Welcome to Zompocalypse!\n";

	public GamePanel(int id, World game, ActionListener action) {
		this.setSize(1000, 1000);
		this.game = game;
		this.action = action;
		this.id = id;

		this.player = game.getPlayer(id);

		this.setBackground(CustomUtils.frameBackground);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		renderingPanel = new RenderPanel(id, game);  //TODO background img goes here
		renderingPanel.setPreferredSize(new Dimension(800, 600));
		renderingPanel.setBackground(Color.BLACK);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		this.add(renderingPanel, c);

		menuPanel = new JPanel();
		menuPanel.setPreferredSize(new Dimension(150, 700));
		menuPanel.setBackground(CustomUtils.menuPanelBackground);
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 2;
		c.gridx = 1;
		c.gridy = 0;
		addMenuPanelComponents();
		this.add(menuPanel, c);

		dialoguePanel = new JPanel();
		dialoguePanel.setPreferredSize(new Dimension(800, 100));
		dialoguePanel.setBackground(CustomUtils.blueBackground);
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
		txtDialog = new JTextArea(5, 65);
		txtDialog.setFont(CustomUtils.textAreaFont);
		txtDialog.setText(dialog);
		txtDialog.setEditable(false);
		txtDialog.setBackground(CustomUtils.lightYellow);
		txtDialog.setVisible(true);
		JScrollPane dialog = new JScrollPane(txtDialog);
		dialoguePanel.add(dialog);
	}

	/**
	 * Add components to the menuPanel.
	 */
	private void addMenuPanelComponents() {
		this.menuPanel.setLayout(new GridBagLayout());
		GridBagConstraints c =  new GridBagConstraints();
		int positionY = 0;

		if(player.getEquipped() != null) {
			itemImage = Loader.LoadSpriteIcon(player.getEquipped().getFileName());
		} else {
			itemImage = Loader.LoadSpriteIcon(Loader.defaultEquipped);
		}

		Insets bottomInset = new Insets(0, 0, 40, 0);
		Insets generalInset = new Insets(0,0,10,0);

		int minimum = 0;
	    int maximum = 100;
		progressDamage = new JProgressBar(minimum, maximum);
		progressDamage.setStringPainted(true);
		progressDamage.setString("LIFE");
		c.gridx = 1;
		c.gridy = positionY++;
		c.ipadx = 100;
		c.ipady = 15;
		c.insets = bottomInset;
		menuPanel.add(progressDamage, c);

		JPanel scoreDecoration = new JPanel();
		scoreDecoration.setBackground(CustomUtils.blueBackground);
		scoreDecoration.setSize(50,50);
		lblScore = new JLabel();
		lblScore.setText("Score\n");
		lblScore.setBackground(CustomUtils.blueBackground);
		lblScore.setOpaque(true);
		lblScore.setForeground(CustomUtils.textInButton);
		c.gridx = 1;
		c.gridy = positionY++;
		c.ipadx = 0;
		c.ipady = 0;
		c.weightx = 1.0;
		c.insets = bottomInset;
		scoreDecoration.add(lblScore);
		menuPanel.add(scoreDecoration, c);

		lblItem = new JLabel(itemImage);
		lblItem.setText("Equipped");
		c.gridx = 1;
		c.gridy = positionY++;
		c.ipadx = 3;
		c.weightx = 1.0;
		c.insets = bottomInset;
		menuPanel.add(lblItem, c);

		// OPTIONS
		btnOptions = new ZButton("Save Game");
		btnOptions.setActionCommand(UICommand.OPTIONS.getValue());
		btnOptions.addActionListener(action);
		c.gridx = 1;
		c.gridy = positionY++;
		c.ipadx = 2;
		c.insets = generalInset;
		menuPanel.add(btnOptions, c);

		btnBackpack = new ZButton("Backpack");
		btnBackpack.setActionCommand(UICommand.BACKPACK.getValue());
		btnBackpack.addActionListener(action);
		c.gridx = 1;
		c.gridy = positionY++;
		c.ipadx = 2;
		c.insets = generalInset;
		menuPanel.add(btnBackpack, c);

		btnExamine = new ZButton("Examine");
		btnExamine.setActionCommand(UICommand.EXAMINE.getValue());
		btnExamine.addActionListener(action);
		c.gridx = 1;
		c.gridy = positionY++;
		c.ipadx = 2;
		c.insets = generalInset;
		menuPanel.add(btnExamine, c);

		btnUse = new ZButton("Use");
		btnUse.setActionCommand(UICommand.USE.getValue());
		btnUse.addActionListener(action);
		c.gridx = 1;
		c.gridy = positionY++;
		c.insets = bottomInset;
		menuPanel.add(btnUse, c);

		Insets eastInset = new Insets(0, -50, 10, 0);
		Insets westInset = new Insets(0, 0, 10, -50);

		// DIRECTIONS
		ImageIcon iconNorth = new ImageIcon(NORTH);
		btnNorth = new ZButton(iconNorth);
		btnNorth.setActionCommand(UICommand.NORTH.getValue());
		btnNorth.addActionListener(action);
		btnNorth.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 1;
		c.gridy = positionY;
		c.insets = generalInset;
		menuPanel.add(btnNorth, c);

		ImageIcon iconClockwise = new ImageIcon(CLOCKWISE);
		btnRotateClockwise = new ZButton(iconClockwise);
		btnRotateClockwise.setActionCommand(UICommand.ROTATECLOCKWISE.getValue());
		btnRotateClockwise.addActionListener(action);
		btnRotateClockwise.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 0;
		c.gridy = positionY;
		c.ipadx = 1;
		c.insets = westInset;
		menuPanel.add(btnRotateClockwise, c);

		ImageIcon iconAnticlockwise = new ImageIcon(ANTICLOCKWISE);
		btnRotateAnticlockwise = new ZButton(iconAnticlockwise);
		btnRotateAnticlockwise.setActionCommand(UICommand.ROTATEANTICLOCKWISE.getValue());
		btnRotateAnticlockwise.addActionListener(action);
		btnRotateAnticlockwise.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 3;
		c.gridy = positionY++;
		c.ipadx = 1;
		c.insets = eastInset;
		menuPanel.add(btnRotateAnticlockwise, c);

		ImageIcon iconWest = new ImageIcon(WEST);
		btnWest = new ZButton(iconWest);
		btnWest.setActionCommand(UICommand.WEST.getValue());;
		btnWest.addActionListener(action);
		btnWest.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 0;
		c.gridy = positionY;
		c.ipadx = 1;
		c.insets = westInset;
		btnWest.setOpaque(false);
		btnWest.setSize(50, 50);
		menuPanel.add(btnWest, c);

		ImageIcon iconEast = new ImageIcon(EAST);
		btnEast = new ZButton(iconEast);
		btnEast.setActionCommand(UICommand.EAST.getValue());
		btnEast.addActionListener(action);
		btnEast.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 3;
		c.gridy = positionY++;
		c.ipadx = 1;
		c.insets = eastInset;
		menuPanel.add(btnEast, c);

		c.insets = generalInset;
		ImageIcon iconSouth = new ImageIcon(SOUTH);
		btnSouth = new ZButton(iconSouth);
		btnSouth.setActionCommand(UICommand.SOUTH.getValue());
		btnSouth.addActionListener(action);
		btnSouth.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 1;
		c.gridy = positionY++;
		c.ipadx = 2;
		menuPanel.add(btnSouth, c);
	}

	@Override
	public void paintComponent(Graphics g) {
		player = game.getPlayer(id);
		updatePlayersEquipped();
		updatePlayersDamage();
		updatePlayersScore();
	}

	/**
	 * Updates the World for visualization at the renderingPanel.
	 *
	 * @param game in the current state.
	 */
	public void updateGame(World game) {
		this.game = game;

		renderingPanel.updateGame(game);
	}

	/**
	 * Updated dialog text in the text area.
	 *
	 * @param text to be inserted into the dialog.
	 */
	public void updateDialog(String text) {
		dialog += text + "\n";
		txtDialog.setText(dialog);
	}

	/**
	 * Rotates the rendered world at the renderingPanel.
	 *
	 * @param command is the actionCommand of the button pressed.
	 */
	public void rotateView(String command) {
		if(command == UICommand.ROTATECLOCKWISE.getValue()) {
			renderingPanel.rotate(Direction.CLOCKWISE);
		} else {
			renderingPanel.rotate(Direction.ANTICLOCKWISE);
		}
	}

	/**
	 * Saves the current state of the game in its file.
	 */
	public void saveGame() {
		try {
			zompocalypse.datastorage.Parser.SaveMap(this.game);
		} catch (IOException e) {
			new JOptionPane("Something happened and we couldn't save your game :(", JOptionPane.ERROR_MESSAGE);
			System.out.println("Unexpected problem. Couldn't save the game. :(");
		}
	}

	/**
	 * Finds the item to be examined to be shown in the dialog.
	 */
	public void examine() {
		String examineText = "";
		// Process any objects the player is standing on first
		for (GameObject o : player.getObjectsHere()) {
			if (o instanceof Item) {
				examineText = ((Item) o).examine();
			}
		}

		// Then, if no objects were used before, process any in front of the player
		for (GameObject o : player.getObjectsInfront()) {
			if (o instanceof Item) {
				examineText = ((Item) o).examine();
			}
		}

		updateDialog(examineText);
	}

	/**
	 * Updates player's health display.
	 */
	private void updatePlayersDamage() {
		progressDamage.setValue(player.getHealth());
	}

	/**
	 * Updates the equipment being used by the player in the display.
	 */
	private void updatePlayersEquipped() {
		if(player.getEquipped() != null) {
			itemImage = Loader.LoadSpriteIcon(player.getEquipped().getFileName());
		} else {
			itemImage = Loader.LoadSpriteIcon(Loader.defaultEquipped);
		}

		lblItem.setIcon(itemImage);
	}

	/**
	 * Updates player's score.
	 */
	private void updatePlayersScore() {
		String scoreText = "Score: "+player.getScore();
		lblScore.setText(scoreText);
	}

	/**
	 * Ends the game.
	 */
	public void endGame() {
		// TODO: "game over -score" message and closes the client
	}
}

