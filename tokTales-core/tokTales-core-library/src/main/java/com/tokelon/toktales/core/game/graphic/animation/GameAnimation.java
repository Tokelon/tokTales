package com.tokelon.toktales.core.game.graphic.animation;

import com.tokelon.toktales.core.game.graphic.IBaseGraphic;

public class GameAnimation implements IGameAnimation {

	
	private IBaseGraphic[] graphicArray;
	
	private int[] durationArray;
	
	private int frameDuration;
	private int frameCount;
	
	private int fullAnimationDuration = -1;
	
	private boolean hasSingleFrameDuration;
	

	private boolean mAnimationStopped = false;
	private boolean mAnimationLoop = false;
	
	
	
	@Override
	public void setupGraphics(IBaseGraphic... graphics) {
		this.graphicArray = graphics;
	}

	@Override
	public void setupFramesWithDurations(int... durations) {
		this.frameCount = durations.length;
		this.durationArray = durations;
		
		fullAnimationDuration = 0;
		for(int d: durations) {
			if(d < 0) {
				throw new IllegalArgumentException("All values in durations must be >= 0");		// TODO: Test for numbers == 0
			}
			
			fullAnimationDuration += d; 
		}
		
		hasSingleFrameDuration = false;
	}

	@Override
	public void setupFramesWithOneDuration(int frameCount, int frameDuration) {
		if(frameCount <= 0) {
			throw new IllegalArgumentException("frameCount must be > 0");
		}
		if(frameDuration <= 0) {
			throw new IllegalArgumentException("frameDuration must be > 0");
		}
		
		this.frameCount = frameCount;
		this.frameDuration = frameDuration;
		
		fullAnimationDuration = frameCount * frameDuration;
		
		hasSingleFrameDuration = true;
	}

	@Override
	public int getState(int timePassedMillis) {
		
		if(fullAnimationDuration < 0) {
			return STATE_NONE;
		}
		else if(timePassedMillis <= 0) {
			return STATE_STILL;
		}
		else if(mAnimationStopped) {
			return STATE_FINISHED;
		}
		else if(mAnimationLoop) {
			return STATE_RUNNING;
		}
		// If not animation looping and timePassed the full duration
		else if(timePassedMillis >= fullAnimationDuration) {
			return STATE_FINISHED;
		}
		else {
			return STATE_RUNNING;
		}
		
	}

	@Override
	public IBaseGraphic getKeyframe(int timePassedMillis) {
		if(hasSingleFrameDuration && graphicArray.length != frameCount) {
			throw new IllegalStateException("The graphics arrays size must match the frame count");
		}
		else if(!hasSingleFrameDuration && graphicArray.length != durationArray.length) {
			throw new IllegalStateException("The graphics and the durations arrays must be of the same size");
		}
		
		
		if(mAnimationLoop) {
			if(timePassedMillis <= 0) {
				return graphicArray[0];
			}
			else {
				if(hasSingleFrameDuration) {
					int gIndex = (int) (timePassedMillis / frameDuration);
					// TODO: Important - Sanitize timePassed!
					
					if(gIndex >= graphicArray.length) {
						gIndex = gIndex % graphicArray.length; //% gIndex;	// Modulo
					}
					
					return graphicArray[gIndex];
				}
				else {
					// TODO: Maybe do without loop
					//This will be performance costly if timePassed is big  
					
					int gCounter = 0;
					int timeCounter = durationArray[0];
					while(timeCounter < timePassedMillis) {
						if(gCounter >= durationArray.length) {
							gCounter = 0;
						}
						
						timeCounter += durationArray[++gCounter];
					}
					
					return graphicArray[gCounter];
				}
			}
		}
		else {
			if(timePassedMillis <= 0) {
				return graphicArray[0];
			}
			else if(timePassedMillis >= fullAnimationDuration) {
				return graphicArray[graphicArray.length-1];
			}
			else {
				if(hasSingleFrameDuration) {
					int gIndex = (int) (timePassedMillis / frameDuration);
					// TODO: Important - Sanitize timePassed!
					
					if(gIndex >= graphicArray.length) {
						gIndex = graphicArray.length - 1;
					}
					
					return graphicArray[gIndex];
				}
				else {
					int gCounter = 0;
					int timeCounter = durationArray[0];
					while(timeCounter < timePassedMillis) {
						timeCounter += durationArray[++gCounter];
					}
					
					return graphicArray[gCounter];
				}
			}
		}
		
		
		
	}

	@Override
	public int getFrameCount() {
		return frameCount;
	}
	
	@Override
	public int getAnimationDuration() {
		return fullAnimationDuration;
	}

	
	@Override
	public void enableLoop(boolean doLoop) {
		mAnimationLoop = doLoop; 
	}
	
	@Override
	public boolean isAnimationLooping() {
		return mAnimationLoop;
	}
	
	
	@Override
	public void stopAnimation() {
		mAnimationStopped = true;
	}
	@Override
	public void resetAnimation() {
		mAnimationStopped = false;
	}
	@Override
	public boolean isAnimationStopped() {
		return mAnimationStopped;
	}
	
	
}
