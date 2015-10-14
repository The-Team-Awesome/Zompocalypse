package zompocalypse.ui.appwindow.custom;

import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Stackable;

/**
 * This is a custom radio button used for the game. It displays an
 * image representing an object in a container.
 *
 * @author Sam Costigan
 */
public class ZRadioButton extends JRadioButton {

	private static final long serialVersionUID = -6271355627745921777L;

	public ZRadioButton(GameObject object, ActionListener action) {
		super();

		setIcon(Loader.LoadSpriteIcon(object.getFileName()));
		addActionListener(action);

		if(object instanceof Stackable) {
			Stackable stack = (Stackable) object;
			setText(Integer.toString(stack.getCount()));
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(isSelected()) {
			setBackground(CustomUtils.radioSelected);
		} else {
			setBackground(CustomUtils.radioUnselected);
		}


	}

}
