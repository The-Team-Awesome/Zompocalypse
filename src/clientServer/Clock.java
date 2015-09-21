package clientServer;

import gameWorld.World;

public class Clock extends Thread {

	private final World game;
	private final int delay; // delay between pulses

	public Clock(World game, int delay) {
		this.game = game;
		this.delay = delay;
	}

	public void run() {
		while(true) {
			try {
				Thread.sleep(delay);
				game.clockTick();
				// trigger the ticking of the game world here
				// and repainting
				// game.tick();
				// display.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
