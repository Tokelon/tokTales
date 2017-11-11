package com.tokelon.toktales.core.game.logic.motion;

import com.tokelon.toktales.core.game.graphic.animation.IGameAnimation;

public class StraightAnimatedMotion extends StraightGameMotionImpl implements IAnimatedMotion {

	private IGameAnimation mAnimation;
	
	
	@Override
	public void setAnimation(IGameAnimation animation) {
		mAnimation = animation;
	}
	
	@Override
	public IGameAnimation getAnimation() {
		return mAnimation;
	}
	
	@Override
	public void removeAnimation() {
		mAnimation = null;
	}
	
	@Override
	public boolean hasAnimation() {
		return mAnimation != null;
	}
	
}
