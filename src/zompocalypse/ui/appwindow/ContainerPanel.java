package zompocalypse.ui.appwindow;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.items.Item;
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
			if(object instanceof Item) {
				Item item = (Item) object;
				JRadioButton button = new ZRadioButton(object, action);
				button.setActionCommand(UICommand.USEITEM.getValue() + Integer.toString(item.getUniqueID()));
				add(button, null);
				options.add(button);
			}
		}
	}
}
