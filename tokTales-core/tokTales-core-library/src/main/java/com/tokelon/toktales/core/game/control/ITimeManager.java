package com.tokelon.toktales.core.game.control;

public interface ITimeManager extends ITimeProvider {

	
	public void startTime();
	
	public void stopTime();
	
	
	//public void addTime(long addMillis);
	//public void subtractTime(long subtractMillis);
	
	//public void setTimeSpeed(double speedMult);
	
	
	public long getGameTimeMillis();
	
	public ITimeTracker getGameTimeTracker();

}
