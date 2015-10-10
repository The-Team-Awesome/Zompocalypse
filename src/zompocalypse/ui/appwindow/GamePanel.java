package zompocalypse.ui.appwindow;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.Direction;
import zompocalypse.gameworld.characters.Player;
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
	private JPanel minimapPanel;
	private RenderPanel renderingPanel;

	// menuPanel components
	private JLabel lblItem;
	private JLabel lblBackpack;
	private ZButton btnBackpack;
	private ZButton btnUse;
	private ZButton btnItemOne;
	private ZButton btnItemTwo;
	private ZButton btnItemThree;
	private ZButton btnNorth;
	private ZButton btnSouth;
	private ZButton btnEast;
	private ZButton btnWest;
	private ZButton btnRotateClockwise;
	private ZButton btnRotateAnticlockwise;
	private ZButton btnOptions;

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
	private String dialog;
	private ZButton btnExamine;


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
		txtDialog.setText("Welcome to Zompocalypse!\n");
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

		ImageIcon itemImage = Loader.LoadSpriteIcon(player.getEquipped().getFileName());


		Insets bottomInset = new Insets(0, 0, 40, 0);
		Insets generalInset = new Insets(0,0,10,0);

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

		// BACKPACK HUB
		lblItem = new JLabel("Backpack HUB");
		c.gridx = 1;
		c.gridy = positionY++;
		c.insets = generalInset;
		menuPanel.add(lblItem, c);

		ImageIcon iconItemOne = new ImageIcon(ITEM);
		btnItemOne = new ZButton(iconItemOne);
		btnItemOne.setActionCommand(UICommand.ITEMONE.getValue());
		btnItemOne.addActionListener(action);
		btnItemOne.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 1;
		c.gridy = positionY++;
		menuPanel.add(btnItemOne, c);

		ImageIcon iconItemTwo = new ImageIcon(ITEM);
		btnItemTwo = new ZButton(iconItemTwo);
		btnItemTwo.setActionCommand(UICommand.ITEMTWO.getValue());
		btnItemTwo.addActionListener(action);
		btnItemTwo.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 1;
		c.gridy = positionY++;
		menuPanel.add(btnItemTwo, c);

		ImageIcon iconItemThree = new ImageIcon(ITEM);
		btnItemThree = new ZButton(iconItemThree);
		btnItemThree.setActionCommand(UICommand.ITEMTHREE.getValue());
		btnItemThree.addActionListener(action);
		btnItemThree.setBorder(BorderFactory.createEmptyBorder());
		c.gridx = 1;
		c.gridy = positionY++;
		menuPanel.add(btnItemThree, c);

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

		/*minimapPanel = new JPanel();
		minimapPanel.setSize(100, 100);
		minimapPanel.setBackground(Color.cyan);
		c.gridx = 1;
		c.gridy = positionY++;
		c.insets = generalInset;
		menuPanel.add(minimapPanel, c);*/
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

	public void examine() {

	}
}

