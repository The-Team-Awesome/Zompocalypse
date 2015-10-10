package zompocalypse.ui.appwindow.custom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import zompocalypse.datastorage.Loader;
import zompocalypse.gameworld.GameObject;

/**
 * This is a custom radio button used for the game. It displays an
 * image representing an object in a container.
 *
 * @author Sam Costigan
 */
public class ZRadioButton extends JRadioButton {

	public ZRadioButton(GameObject object, ActionListener action) {
		super();

		setIcon(Loader.LoadSpriteIcon(object.getFileName()));
		addActionListener(action);
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