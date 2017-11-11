package com.tokelon.toktales.core.game.control;

public class TimeTracker implements ITimeTracker {

	
	
	private final ITimeProvider mTimeProvider;
	
	
	private int state = STATE_PAUSED;
	
	private long pastTimeMillis = 1;	// Start with 1 or 0 ?
	private long lastResumeTime;
	
	
	public TimeTracker(ITimeProvider timeProvider) {
		mTimeProvider = timeProvider;
	}

	
	
	private long currentPast() {
		long currTime = mTimeProvider.getCurrentTimeMillis();
		return currTime - lastResumeTime;
	}


	@Override
	public int getState() {
		return state;
	}
	
	
	@Override
	public synchronized void pause() {
		if(state == STATE_PAUSED) {
			
			// What happens here?
			return;
		}
		
		
		pastTimeMillis += currentPast();
		
		state = STATE_PAUSED;
	}

	@Override
	public synchronized void resume() {
		if(state == STATE_RUNNING) {
			
			// What happens here?
			return;
		}
		
		
		lastResumeTime = mTimeProvider.getCurrentTimeMillis();

		state = STATE_RUNNING;
	}

	
	@Override
	public long getPastTimeMillis() {
		long pastTime;
		if(state == STATE_RUNNING) {
			pastTime = pastTimeMillis + currentPast();
		}
		else if(state == STATE_PAUSED) {
			pastTime = pastTimeMillis;
		}
		else {
			pastTime = -1;
		}
		
		return pastTime;
	}
	

}
