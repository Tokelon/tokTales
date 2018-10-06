package com.tokelon.toktales.core.game.logic.entity;

import com.tokelon.toktales.core.game.graphic.IBaseGraphic;
import com.tokelon.toktales.core.game.graphic.animation.IGameAnimation;

public interface IGraphicsImage {
	
	// Analog to IPhysicsBody for graphics
	// Assigned graphics, animations etc.

	// Alternative where this class contains the logic
	//public void adjustEntity(IGameEntity entity);

	
	// To implement these I would have to add a observation/participation to this class (since there is no access to the game entity)
	//public static final String CHANGE_GRAPHICS_IMAGE_IMAGE_GRAPHIC = "change_graphics_image_image_graphic";
	//public static final String CHANGE_GRAPHICS_IMAGE_STATIC_GRAPHIC = "change_graphics_image_static_graphic";

	
	/**
	 * 
	 * @return True if this image is enabled, false if not.
	 */
	public boolean isEnabled();

	/** Enables or disabled this image. 
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled);


	/**
	 * 
	 * @param timeMillis
	 * @param timePassedMillis
	 * @return The image graphic for the given time, or null if there is none.
	 */
	public IBaseGraphic getImageGraphic(long timeMillis);

	public IBaseGraphic getImageGraphic(long timeMillis, String name);
	
	// More support for multiple graphics in one frame
	//public Map<String, IBaseGraphic> getImageGraphicMap();
	//public IBaseGraphic[] getImageGraphicAll();
	//public String[] getImageGraphicNames();

	
	
	
	public void setStaticGraphic(IBaseGraphic graphic);
	public void setStaticGraphic(IBaseGraphic graphic, String name);
	
	public IBaseGraphic getStaticGraphic();
	public IBaseGraphic getStaticGraphic(String name);
	
	public IBaseGraphic removeStaticGraphic();
	public IBaseGraphic removeStaticGraphic(String name);
	
	
	public void startAnimation(IGameAnimation animation, long startTimeMillis);
	public void startAnimation(IGameAnimation animation, long startTimeMillis, String name);
	//public void startAnimation(IGameAnimation animation, IAnimationCallback<IGameAnimation> callback);
	
	public IGameAnimation getAnimation();
	public IGameAnimation getAnimation(String name);
	
	public IGameAnimation removeAnimation();
	public IGameAnimation removeAnimation(String name);

	
	
	/* Assigned graphics and animations */
	
	/** Stores the give graphic with the given name.
	 * 
	 * @param name
	 * @param graphic
	 */
	public void assignGraphic(String name, IBaseGraphic graphic);
	
	/** Returns a stored graphic.
	 * 
	 * @param name
	 * @return The graphic for the given name, or null if there is none.
	 */
	public IBaseGraphic getAssignedGraphic(String name);
	
	/** Removes a stored graphic.
	 * 
	 * @param name
	 * @return The previous graphic for the given name, or null if there was none.
	 */
	public IBaseGraphic removeAssignedGraphic(String name);
	

	/** Stores the given animation with the given name.
	 * 
	 * @param name
	 * @param animation
	 */
	public void assignAnimation(String name, IGameAnimation animation);
	
	/** Returns a stored animation.
	 * 
	 * @param name
	 * @return The animation for the given name, or null if there is none.
	 */
	public IGameAnimation getAssignedAnimation(String name);

	/** Removes a stored animation.
	 * 
	 * @param name
	 * @return The previous animation for the given name, or null if there was none.
	 */
	public IGameAnimation removeAssignedAnimation(String name);

	
}
