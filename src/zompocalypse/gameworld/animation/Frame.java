package zompocalypse.gameworld.animation;

import javax.swing.ImageIcon;

/**
 * This small class contains the image, and how long to display that image for.
 * @author kellypaul1
 *
 */
public class Frame {

	private ImageIcon frame;
	private int duration;

	public Frame(ImageIcon frame, int duration) {
		this.frame = frame;
		this.duration = duration;
	}

	public ImageIcon getFrame() {
		return frame;
	}

	public void setFrame(ImageIcon frame) {
		this.frame = frame;
	}

	public int getDuration(){
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
