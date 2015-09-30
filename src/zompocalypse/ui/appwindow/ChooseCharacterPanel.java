package zompocalypse.ui.appwindow;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

import zompocalypse.gameworld.world.World;

public class ChooseCharacterPanel extends JPanel {
	private ActionListener action;
	private World game;
	private int id;
	
	public ChooseCharacterPanel(int id, World game, ActionListener action) {
		this.game = game;
		this.id = id;
		this.action = action;
		
		this.setSize(1000, 1000);
	}
}
