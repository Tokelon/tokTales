package com.tokelon.toktales.core.game.controller;

public interface IController {

	
	/** Called to assign a controller manager to this controller.
	 * 
	 * @param cm
	 */
	public void setup(IControllerManager cm);	// Maybe rename to setupManager or registerManager

	
	/** Called when a controller is changed on the controller manager.
	 * 
	 * @param controllerId
	 */
	public void onControllerChange(IControllerManager cm, String controllerId);
	
	
	// TODO: Return boolean to signify something ?
	public void action(String action, Object... args);
	
	
}
