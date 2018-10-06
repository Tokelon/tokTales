package com.tokelon.toktales.core.game.graphic.animation;

public interface IAnimationCallback<T extends IBaseAnimation> {

	public void animationStarted(T animation);
	
	public void animationFinished(T animation);
	
}
