package zompocalypse.gameworld.animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Animates the sprite.
 *  * http://gamedev.stackexchange.com/questions/53705/how-can-i-make-a-sprite-sheet-based-animation-system
 * ^^this code contains exactly what we needed.
 * @author kellypaul1
 *
 */
public class Animation {

	private int frameCount;  //counts the ticks for changing
	private int frameDelay;
	private int currentFrame;
	private int animationDirection;  //counting forwards or backwards steps
	private int totalFrames;

	private boolean stopped;

	private List<Frame> frames = new ArrayList<Frame>();

	public Animation(BufferedImage[] frames, int frameDelay){
		this.frameDelay = frameDelay;
		this.stopped = true;

		for(int i = 0; i < frames.length; ++i){
			addFrame(frames[i], frameDelay);
		}

		this.frameCount = 0;  //start from the start
		this.frameDelay = frameDelay;
		this.currentFrame = 0;
		this.animationDirection = 1;	//start by looking in front
		this.totalFrames = this.frames.size();  //however big it ends up being
	}

	/** Starts off the animation. */
	public void start(){
		if(!stopped){
			return;
		}
		if(frames.size() == 0){  //if you've run out of frames
			return;
		}
		stopped = false;
	}

	/** Stops the animation. */
	public void stop(){
		if(frames.size() == 0){
			return;
		}
		stopped = true;
	}

	/** Restarts the animation. */
	public void restart(){
		if(frames.size() == 0){
			return;
		}
		stopped = false;
		currentFrame = 0;  //back to the initial frame
	}

	/** Resets the animation. */
	public void reset(){
		stopped = true;
		frameCount = 0;  //back to the initial frame
		currentFrame = 0;
	}

	/** Adds the frame onto the frame */
	private void addFrame(BufferedImage frame, int duration) {
		if(duration <= 0){
			System.err.println("Duration can't be this number:" + duration);
			throw new RuntimeException();
		}
		frames.add(new Frame(frame, duration));
		currentFrame = 0;
	}

	/** Retrieves the sprite from the frame */
	public BufferedImage getSprite(){
		return frames.get(currentFrame).getFrame();
	}

	/** Update the animation to the next frame */
	public void update(){
		if(!stopped){
			frameCount++;
			if(frameCount > frameDelay){
				frameCount = 0;
				currentFrame += animationDirection;

				if(currentFrame > totalFrames-1){
					currentFrame = 0;
				}
				else if(currentFrame < 0){
					currentFrame = totalFrames - 1;
				}
			}
		}
	}
}
