package clientServer;

import userInterface.appWindow.MainFrame;
import gameWorld.World;

/**
 * This class runs the game loop of the entire game. At regular intervals, defined
 * by the delay integer, the Clock Thread will cause the World to tick over and redisplay
 * the application window if one exists. If one doesn't, then this just ticks the game
 * world and the game is running through a Client/Server connection.
 * 
 * @author Sam Costigan
 */
public class Clock extends Thread {

	private MainFrame frame;
	private final World game;
	private final int delay; // delay between pulses
	
	public Clock(MainFrame frame, World game, int delay) {
		this.frame = frame;
		this.game = game;
		this.delay = delay;
	}

	public void run() {
		while(true) {
			try {
				Thread.sleep(delay);
				// trigger the ticking of the game world here
				game.clockTick();
				
				// and repaint if it is this clocks responsibility
				if(frame != null) {
					frame.repaint();
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
