package com.tokelon.toktales.core.game.model;

public interface IPlayer {
	// TODO: Probably need observable for when the actor changes
	
	/* Maybe add a looking angle to support 360 degree looking*/

	public static final String GRAPHIC_IDLE_LEFT = "graphic_idle_left";
	public static final String GRAPHIC_IDLE_UP = "graphic_idle_up";
	public static final String GRAPHIC_IDLE_RIGHT = "graphic_idle_right";
	public static final String GRAPHIC_IDLE_DOWN = "graphic_idle_down";
	
	public static final String ANIMATION_WALK_LEFT = "animation_walk_left";
	public static final String ANIMATION_WALK_UP = "animation_walk_up";
	public static final String ANIMATION_WALK_RIGHT = "animation_walk_right";
	public static final String ANIMATION_WALK_DOWN = "animation_walk_down";
	
	
	
	
	public IActor getActor();
	public void setActor(IActor actor); // Really have this?
	
	
	
	//public CrossDirection getLookingDirection();
	
	//public boolean isResponsive();

	//public IMutablePlayerGraphicState getGraphicState();
	 
	
	//public boolean isAnimating();
	//public boolean isBusy();
	
}
