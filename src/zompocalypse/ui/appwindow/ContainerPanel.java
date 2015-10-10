package zompocalypse.ui.appwindow;

import java.awt.event.ActionListener;
import java.util.List;

import javafx.beans.binding.SetBinding;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JPanel;

import zompocalypse.gameworld.GameObject;
import zompocalypse.ui.appwindow.custom.ZRadioButton;

public class ContainerPanel extends JPanel {
	private ActionListener action;
	private List<? extends GameObject> objects;

	public ContainerPanel(List<? extends GameObject> objects, ActionListener action) {
		super();
		this.objects = objects;
		this.action = action;

		this.setup();
	}

	public void setup() {
		ButtonGroup options = new ButtonGroup();

		for(GameObject object : objects) {
			JRadioButton button = new ZRadioButton(object, action);
			add(button, null);
			options.add(button);
		}
	}
}
