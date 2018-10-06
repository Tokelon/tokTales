package com.tokelon.toktales.core.game.controller;

public abstract class AbstractController implements IController {

	
	private IControllerManager controllerManager;

	
	@Override
	public void onControllerAdd(IControllerManager manager, String controllerId, IController addedController) {
		if(addedController == this) { // When this controller is added
			controllerManager = manager;
		}
	}
	
	@Override
	public void onControllerRemove(IControllerManager manager, String controllerId, IController removedController) {
		if(removedController == this) { // When this controller is removed
			controllerManager = null;
		}
	}
	
	
	protected IControllerManager getControllerManager() {
		return controllerManager;
	}
	
}
