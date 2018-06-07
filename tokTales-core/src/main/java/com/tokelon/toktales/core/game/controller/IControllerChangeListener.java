package com.tokelon.toktales.core.game.controller;

public interface IControllerChangeListener {

	
	/** Called when a new controller is set for the given id.
	 * 
	 * @param manager
	 * @param controllerId
	 * @param addedController
	 */
	public default void onControllerAdd(IControllerManager manager, String controllerId, IController addedController) { }
	
	/** Called when a controller is replaced for the given id.
	 * 
	 * @param manager
	 * @param controllerId
	 * @param oldController
	 * @param newController
	 */
	public default void onControllerChange(IControllerManager manager, String controllerId, IController oldController, IController newController) { }
	
	/** Called when a controller is removed for the given id.
	 * 
	 * @param manager
	 * @param controllerId
	 * @param removedController
	 */
	public default void onControllerRemove(IControllerManager manager, String controllerId, IController removedController) { }
	
}
