package com.tokelon.toktales.core.game.logic.motion;

import com.tokelon.toktales.core.game.graphic.animation.IGameAnimation;


public interface IAnimatedMotion extends IGameMotion {

	
	public void setAnimation(IGameAnimation animation);
	public IGameAnimation getAnimation();

	public void removeAnimation();
	public boolean hasAnimation();
	
}
