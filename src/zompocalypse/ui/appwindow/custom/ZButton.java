package zompocalypse.ui.appwindow.custom;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * ZButton is responsible for customizing the appearance of JButton with this game theme.
 * @author Danielle Emygdio
 *
 */
public class ZButton extends JButton {

	public ZButton(String name) {
		super(name);
		super.setContentAreaFilled(false);
	}

	public ZButton(ImageIcon icon) {
		super(icon);
		super.setContentAreaFilled(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		//setBackground(CustomUtils.button);
		setForeground(CustomUtils.textInButton);

		if(getModel().isPressed()) {
			g.setColor(CustomUtils.buttonPressed);
		} else if (getModel().isRollover()) {
			g.setColor(CustomUtils.buttonRollover);
		} else if (!getModel().isEnabled()) {
			g.setColor(CustomUtils.buttonUnabled);
		} else {
			g.setColor(CustomUtils.button);
		}
		g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
	}
}
