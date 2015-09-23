package userInterface.renderWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui {

	public Gui(){
		JFrame frame = new JFrame("Zompocalypse");
		ImageIcon img = new ImageIcon("img/zombie-icon.png");
		frame.setIconImage(img.getImage());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel renderPanel = new JPanel();
		renderPanel.setPreferredSize(new Dimension(800, 600));
		
		RenderPanel r = new RenderPanel(renderPanel);
		
		frame.getContentPane().add(renderPanel,  BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);
		
		//get the tick method, update r
//		while(1 == 1){
//			
//			System.out.println("tick");
//			//get information from client - orientations, actions, everything
//			r.update();
//		}
	}
}
