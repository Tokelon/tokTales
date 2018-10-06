package com.tokelon.toktales.core.game.graphic.animation;

import com.tokelon.toktales.core.game.graphic.IBaseGraphic;

public interface IGameAnimation extends IBaseAnimation {

	// TODO: Add various modes for animation: loop, reverse, reverse_loop, random etc.
	
	public void enableLoop(boolean doLoop);
	public boolean isAnimationLooping();
	
	public void stopAnimation();
	public void resetAnimation();
	public boolean isAnimationStopped();
	
	
	
	public int getFrameCount();

	public int getAnimationDuration();
	
	
	
	public void setupGraphics(IBaseGraphic... graphics);

	/**
	 * 
	 * @param durations
	 * @throws IllegalArgumentException If one of durations is < 0
	 */
	public void setupFramesWithDurations(int... durations);
	
	
	/**
	 * 
	 * @param frameCount
	 * @param frameDuration
	 * @throws IllegalArgumentException If frameCount <= 0 or frameDuration <= 0.
	 */
	public void setupFramesWithOneDuration(int frameCount, int frameDuration);
	
	
	
	
	@Override
	/**
	 * Returns:<br>
	 * STATE_NONE - If the animation has not been initialized.<br>
	 * STATE_STILL - If timePassedMillis <= 0.<br>
	 * STATE_RUNNING - If the animation has not been manually stopped or if loop is disabled and the time passed is less than the animation duration.<br>
	 * STATE_FINISHED - If the animation has been manually stopped or if loop is disabled and the time passed is more than the animation duration.<br>
	 * 
	 * @param timePassedMillis
	 * @return An int indicating the state
	 */
	public int getState(int timePassedMillis);
	
}
