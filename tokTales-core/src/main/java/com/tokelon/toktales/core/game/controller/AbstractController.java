package com.tokelon.toktales.core.game.controller;

public abstract class AbstractController implements IController {

	private IControllerManager mControllerManager;
	
	
	
	@Override
	public void setup(IControllerManager cm) {
		mControllerManager = cm;
	}
	

	@Override
	public void onControllerChange(IControllerManager cm, String controllerId) {
		// Nothing
	}
	
	
	protected IControllerManager getControllerManager() {
		return mControllerManager;
	}
	
	
	@Override
	public void action(String action, Object... args) {
		// Nothing
	}
	
}
