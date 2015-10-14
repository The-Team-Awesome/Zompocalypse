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

	private static final long serialVersionUID = 9092344271771295021L;

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
		setForeground(CustomUtils.white);

		if(getModel().isPressed()) {
			g.setColor(CustomUtils.redTwo);
		} else if (getModel().isRollover()) {
			g.setColor(CustomUtils.redOne);
		} else if (!getModel().isEnabled()) {
			g.setColor(CustomUtils.blueFive);
		} else {
			g.setColor(CustomUtils.redThree);
		}
		g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
	}
}
