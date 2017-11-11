package com.tokelon.toktales.core.game.logic.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.game.graphic.IBaseGraphic;
import com.tokelon.toktales.core.game.graphic.animation.IGameAnimation;

public class GraphicsImage implements IGraphicsImage {

	
	private final Map<String, IBaseGraphic> imageAssignedGraphicMap;
	private final Map<String, IGameAnimation> imageAssignedAnimationMap;

	private boolean imageEnabled = true;
	
	private IBaseGraphic imageStaticGraphic;
	private final Map<String, IBaseGraphic> imageStaticGraphicMap;
	
	private IGameAnimation imageAnimation;
	private final Map<String, IGameAnimation> imageAnimationMap;
	private long initAnimationTime = 0L;
	private final Map<String, Long> initAnimationTimeMap;
	
	
	public GraphicsImage() {
		imageStaticGraphicMap = Collections.synchronizedMap(new HashMap<String, IBaseGraphic>(0));
		imageAnimationMap = Collections.synchronizedMap(new HashMap<String, IGameAnimation>(0));
		initAnimationTimeMap = Collections.synchronizedMap(new HashMap<String, Long>(0));
		imageAssignedGraphicMap = Collections.synchronizedMap(new HashMap<String, IBaseGraphic>());
		imageAssignedAnimationMap = Collections.synchronizedMap(new HashMap<String, IGameAnimation>());
	}
	
	
	@Override
	public boolean isEnabled() {
		return imageEnabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.imageEnabled = enabled;
	}

	
	@Override
	public IBaseGraphic getImageGraphic(long timeMillis) {
		return getGraphic(imageStaticGraphic, imageAnimation, initAnimationTime, timeMillis);
	}

	@Override
	public IBaseGraphic getImageGraphic(long timeMillis, String name) {
		return getGraphic(imageStaticGraphicMap.get(name), imageAnimationMap.get(name), initAnimationTimeMap.get(name), timeMillis);
	}

	
	private IBaseGraphic getGraphic(IBaseGraphic staticGraphic, IGameAnimation animation, long initAnimation, long timeMillis) {
		IBaseGraphic graphic = null;
		
		if(animation != null) {
			int timePassed = (int) (timeMillis - initAnimation);
			if(animation.getState(timePassed) != IGameAnimation.STATE_FINISHED) {
				
				graphic = animation.getKeyframe(timePassed);
			}
		}
		
		if(graphic == null) {
			graphic = staticGraphic;
		}
		
		return graphic;
	}
	
	
	
	@Override
	public void setStaticGraphic(IBaseGraphic graphic) {
		this.imageStaticGraphic = graphic;
	}

	@Override
	public void setStaticGraphic(IBaseGraphic graphic, String name) {
		imageStaticGraphicMap.put(name, graphic);
	}

	@Override
	public IBaseGraphic getStaticGraphic() {
		return imageStaticGraphic;
	}

	@Override
	public IBaseGraphic getStaticGraphic(String name) {
		return imageStaticGraphicMap.get(name);
	}
	
	@Override
	public IBaseGraphic removeStaticGraphic() {
		IBaseGraphic res = imageStaticGraphic;
		imageStaticGraphic = null;
		return res;
	}
	
	@Override
	public IBaseGraphic removeStaticGraphic(String name) {
		return imageStaticGraphicMap.remove(name);
	}

	
	
	@Override
	public void startAnimation(IGameAnimation animation, long startTimeMillis) {
		animation.resetAnimation(); // Do this in here, or ignore?
		
		imageAnimation = animation;
		initAnimationTime = startTimeMillis;
	}

	@Override
	public void startAnimation(IGameAnimation animation, long startTimeMillis, String name) {
		animation.resetAnimation(); // Do this in here, or ignore?
		
		imageAnimationMap.put(name, animation);
		initAnimationTimeMap.put(name, startTimeMillis);
	}

	@Override
	public IGameAnimation getAnimation() {
		return imageAnimation;
	}

	@Override
	public IGameAnimation getAnimation(String name) {
		return imageAnimationMap.get(name);
	}

	@Override
	public IGameAnimation removeAnimation() {
		IGameAnimation res = imageAnimation;
		imageAnimation = null;
		initAnimationTime = 0L;
		return res;
	}
	
	@Override
	public IGameAnimation removeAnimation(String name) {
		initAnimationTimeMap.remove(name);
		return imageAnimationMap.remove(name);
	}
	
	
	@Override
	public void assignGraphic(String name, IBaseGraphic graphic) {
		imageAssignedGraphicMap.put(name, graphic);
	}

	@Override
	public IBaseGraphic getAssignedGraphic(String name) {
		return imageAssignedGraphicMap.get(name);
	}

	@Override
	public IBaseGraphic removeAssignedGraphic(String name) {
		return imageAssignedGraphicMap.remove(name);
	}

	
	@Override
	public void assignAnimation(String name, IGameAnimation animation) {
		imageAssignedAnimationMap.put(name, animation);
	}

	@Override
	public IGameAnimation getAssignedAnimation(String name) {
		return imageAssignedAnimationMap.get(name);
	}

	@Override
	public IGameAnimation removeAssignedAnimation(String name) {
		return imageAssignedAnimationMap.remove(name);
	}

	
}
