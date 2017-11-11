package com.tokelon.toktales.core.game.logic.motion;

public interface IMotionCallback<T extends IGameMotion> {

	public void motionStarted(T motion);
	
	public void motionFinished(T motion);
	
}
