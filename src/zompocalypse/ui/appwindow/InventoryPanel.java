package zompocalypse.ui.appwindow;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

import zompocalypse.gameworld.world.World;

public class InventoryPanel extends JPanel {
	private ActionListener action;
	private World game;
	private int id;

	private JPanel choosingContainer;
	private JPanel hubContainer;

	public InventoryPanel(ActionListener action, World game, int id) {
		super();
		this.action = action;
		this.game = game;
		this.id = id;
	}
}
