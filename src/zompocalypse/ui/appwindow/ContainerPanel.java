package zompocalypse.ui.appwindow;

import java.awt.event.ActionListener;
import java.util.List;

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

	private static final long serialVersionUID = 4299272622772973432L;

	private static ActionListener constanctAction;

	private ActionListener action;
	private List<? extends GameObject> objects;

	private ContainerPanel(List<? extends GameObject> objects,
			ActionListener action, String key) {
		super();
		this.objects = objects;
		this.action = action;

		this.arrangeComponents(key);
	}

	public static void setupContainerPanel(ActionListener action) {
		constanctAction = action;
	}

	public static ContainerPanel getContainerPanel(
			List<? extends GameObject> objects, String key) {
		return new ContainerPanel(objects, constanctAction, key);
	}

	/**
	 * Sets and arranges position of components into the content panel.
	 */
	public void arrangeComponents(String key) {
		ButtonGroup options = new ButtonGroup();

		for (GameObject object : objects) {
			if (object instanceof Item) {
				Item item = (Item) object;
				JRadioButton button = new ZRadioButton(object, action);
				button.setActionCommand(key
						+ Integer.toString(item.getUniqueID()));
				add(button, null);
				options.add(button);
			}
		}
	}
}
