package zompocalypse.ui.rendering;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Gui {

	public Gui(){
		JFrame frame = new JFrame("Zompocalypse");
		ImageIcon img = new ImageIcon("img/zombie-icon.png");
		frame.setIconImage(img.getImage());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//int id = 1; //id for player
		//World world = new World(id, id, null);

		//RenderPanel renderPanel = new RenderPanel(id, world, null);
		RenderPanel renderPanel = new RenderPanel(0, null);
		renderPanel.setPreferredSize(new Dimension(800, 600));

		frame.getContentPane().add(renderPanel,  BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}
}
