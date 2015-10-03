package zompocalypse.gameworld.animation;

import java.awt.image.BufferedImage;

/**
 * This small class contains the image, and how long to display that image for.
 * @author kellypaul1
 *
 */
public class Frame {

	private BufferedImage frame;
	private int duration;

	public Frame(BufferedImage frame, int duration) {
		this.frame = frame;
		this.duration = duration;
	}

	public BufferedImage getFrame() {
		return frame;
	}

	public void setFrame(BufferedImage frame) {
		this.frame = frame;
	}

	public int getDuration(){
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
