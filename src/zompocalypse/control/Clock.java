package zompocalypse.control;

public class Clock extends Thread {

	private final int delay; // delay between pulses
	
	public Clock(int delay) {
		this.delay = delay;
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(delay);
				// trigger the ticking of the game world here
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
