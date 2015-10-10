package zompocalypse.ui.appwindow;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JRadioButton;

import zompocalypse.gameworld.GameObject;
import zompocalypse.ui.appwindow.custom.ZRadioButton;

public class ContainerPane {
	private ActionListener action;
	private List<? extends GameObject> objects;

	public ContainerPane(List<? extends GameObject> objects, ActionListener action) {
		super();
		this.objects = objects;
		this.action = action;
	}

	public JComponent[] inputs() {
		JComponent[] inputs = new JComponent[objects.size()];

		int count = 0;

		ButtonGroup options = new ButtonGroup();

		for(GameObject object : objects) {
			JRadioButton button = new ZRadioButton(object, action);
			inputs[count] = button;
			options.add(button);
			count++;
		}

		return inputs;
	}
}
