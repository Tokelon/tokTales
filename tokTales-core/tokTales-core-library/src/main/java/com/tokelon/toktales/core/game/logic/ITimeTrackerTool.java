package com.tokelon.toktales.core.game.logic;

public interface ITimeTrackerTool {

	
	public void start(long startTime);
	
	public int timePassed(long currentTime);
	
	
	/* This method makes no sense because this class does not count down (like a timer),
	 * and has no concept of a final time
	 * 
	 */
	//public long timeRemaining(long currentTime);
	
}
