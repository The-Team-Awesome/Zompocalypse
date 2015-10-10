package zompocalypse.ui.appwindow;

import java.awt.event.ActionListener;
import java.util.List;

import javafx.beans.binding.SetBinding;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.items.Item;
import zompocalypse.ui.appwindow.custom.ZRadioButton;

/**
 * ContainerPanel represents the panel containing components from containers
 * from Zompocalypse, e.g. inventory.
 *
 * @author Sam Costigan
 *
 */
public class ContainerPanel extends JPanel {
	private ActionListener action;
	private List<? extends GameObject> objects;

	public ContainerPanel(List<? extends GameObject> objects,
			ActionListener action) {
		super();
		this.objects = objects;
		this.action = action;

		this.arrangeComponents();
	}

	/**
	 * Sets and arranges position of components into the content panel.
	 */
	public void arrangeComponents() {
		ButtonGroup options = new ButtonGroup();

		for (GameObject object : objects) {
			if (object instanceof Item) {
				Item item = (Item) object;
				JRadioButton button = new ZRadioButton(object, action);
				button.setActionCommand(UICommand.USEITEM.getValue()
						+ Integer.toString(item.getUniqueID()));
				add(button, null);
				options.add(button);
			}
		}
	}
}
