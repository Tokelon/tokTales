package com.tokelon.toktales.core.game.control;

public class TimeManager implements ITimeManager, ITimeProvider {

	
	private final TimeTracker globalGameTime;
	
	public TimeManager() {
		globalGameTime = new TimeTracker(this);
	}

	
	
	@Override
	public synchronized void startTime() {
		globalGameTime.resume();
		
	}

	@Override
	public synchronized void stopTime() {
		globalGameTime.pause();
	}
	
	
	
	

	@Override
	public long getGameTimeMillis() {
		return globalGameTime.getPastTimeMillis();
	}
	
	@Override
	public ITimeTracker getGameTimeTracker() {
		return globalGameTime;
	}
	
	
	@Override
	public long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}
	
	
	
	
	private static class TimeMillis {
		
	}

}
