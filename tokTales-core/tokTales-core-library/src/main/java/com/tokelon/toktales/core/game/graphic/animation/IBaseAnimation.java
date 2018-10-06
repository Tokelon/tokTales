package com.tokelon.toktales.core.game.graphic.animation;

import com.tokelon.toktales.core.game.graphic.IBaseGraphic;

public interface IBaseAnimation {

	
	/*
	 * Keep this interface stateless.
	 */
	
	
	public static final int STATE_NONE = 1;
	public static final int STATE_STILL = 2;
	public static final int STATE_RUNNING = 3;
	public static final int STATE_FINISHED = 4;
	//public static final int STATE_PAUSED = 5;
	
	/**
	 * Returns:<br>
	 * STATE_NONE - If the animation has not been initialized.<br>
	 * STATE_STILL - If timePassedMillis <= 0.<br>
	 * STATE_RUNNING - If the time passed is lower than the animation duration.
	 * STATE_FINISHED - If the time passed is higher or equal than the animation duration.
	 * 
	 * @param timePassedMillis
	 * @return An int indicating the state for the given time duration
	 */
	public int getState(int timePassedMillis);
	
	
	/**
	 * 
	 * @param timePassed
	 * @return
	 * @throws IllegalStateException If the animation has not been setup.
	 */
	public IBaseGraphic getKeyframe(int timePassedMillis);
	
	
}
