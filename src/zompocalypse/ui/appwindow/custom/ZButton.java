package zompocalypse.ui.appwindow.custom;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ZButton extends JButton {

	public ZButton(String name) {
		super(name);
	}

	public ZButton(ImageIcon icon) {
		super(icon);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		setBackground(CustomUtils.button);
	}

}
