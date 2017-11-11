package com.tokelon.toktales.core.game.model;

import com.tokelon.toktales.core.game.model.entity.IGameEntity;


public interface IPlayer extends IGameEntity {


	public static final String GRAPHIC_IDLE_LEFT = "graphic_idle_left";
	public static final String GRAPHIC_IDLE_UP = "graphic_idle_up";
	public static final String GRAPHIC_IDLE_RIGHT = "graphic_idle_right";
	public static final String GRAPHIC_IDLE_DOWN = "graphic_idle_down";
	
	public static final String ANIMATION_WALK_LEFT = "animation_walk_left";
	public static final String ANIMATION_WALK_UP = "animation_walk_up";
	public static final String ANIMATION_WALK_RIGHT = "animation_walk_right";
	public static final String ANIMATION_WALK_DOWN = "animation_walk_down";
	
	
	/* Maybe add a looking angle to support 360 degree looking*/
	
	
	
	/* Important !
	 * 
	 * Player should be a wrapper for an actor ??
	 * 
	 */
	
	
	
	
	
	
	
	
	//public CrossDirection getLookingDirection();
	
	/* Is the concrete interface IPlayerEntity needed or is IMapEntity sufficient?
	 * 
	 */
	//public IPlayerEntity getPlayerEntity();
	
	
	//public boolean isResponsive();

	//public IMutablePlayerGraphicState getGraphicState();
	 
	
	//public boolean isAnimating();
	//public boolean isBusy();
	
}
