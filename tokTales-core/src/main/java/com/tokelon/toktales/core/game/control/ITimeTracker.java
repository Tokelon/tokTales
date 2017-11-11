package com.tokelon.toktales.core.game.control;

public interface ITimeTracker {

	
	public static final int STATE_RUNNING = 1;
	public static final int STATE_PAUSED = 2;
	
	public int getState();
	
	
	public void resume();
	
	public void pause();
	
	
	
	public long getPastTimeMillis();

	
}
