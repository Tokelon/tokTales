package com.tokelon.toktales.core.game.logic.motion;

import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;

public interface IBaseMotion {

	public static final int STATE_NONE = 1;
	public static final int STATE_STILL = 2;
	public static final int STATE_MOVING = 3;
	public static final int STATE_FINISHED = 4;
	//public static final int STATE_PAUSED = 4;
	
	public int getState(int timePassedMillis);

	
	
	//public IPoint getPosition(long timePassed);
	public void getPosition(int timePassedMillis, IMutablePoint2f result);

	public float getHorizontalPosition(int timePassedMillis);
	public float getVerticalPosition(int timePassedMillis);
	
	
}
