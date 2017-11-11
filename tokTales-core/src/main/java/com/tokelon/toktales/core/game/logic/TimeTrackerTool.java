package com.tokelon.toktales.core.game.logic;

public class TimeTrackerTool implements ITimeTrackerTool {

	// TimeCounter, TimeKeeper
	
	
	public static final int STATE_STOPPED = 1;
	public static final int STATE_RUNNING = 2;
	public static final int STATE_PAUSED = 3;
	
	
	private long startingTime;
	private int state = STATE_STOPPED;
	

	
	@Override
	public void start(long startTime) {
		this.startingTime = startTime;
		
		state = STATE_RUNNING;
	}
	
	@Override
	public int timePassed(long currentTime) {
		// Maybe check if cast will fail and return integer.maxint
		return (int) (currentTime - startingTime);
	}
	
	
	public int getState() {
		return state;
	}
	
	
	public void pause(long currentTime) {
		// TODO: Implement
	}
	
	public void unpause() {
		// TODO: Implement
	}
	
	public void stop() {
		// TODO: Implement
	}


	
}
