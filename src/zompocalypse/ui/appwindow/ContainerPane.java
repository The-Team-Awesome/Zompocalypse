package zompocalypse.ui.appwindow;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.GameObject;
import zompocalypse.ui.appwindow.custom.ZRadioButton;

public class ContainerPane extends JOptionPane {
	private ActionListener action;
	private int id;
	private ArrayList<GameObject> objects;

	private JPanel choosingContainer;
	private JPanel hubContainer;

	public ContainerPane(int id, ArrayList<GameObject> objects, ActionListener action) {
		super();
		this.id = id;
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
