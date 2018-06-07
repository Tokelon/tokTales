package com.tokelon.toktales.extens.def.core.game.controller;

import com.tokelon.toktales.core.game.controller.IController;
import com.tokelon.toktales.core.game.controller.IControllerManager;

public class ModelController<T> implements IModelController<T> {

	
	private IControllerManager controllerManager;
	
	private T model;
	
	public ModelController() {
		// Default constructor
	}
	
	public ModelController(T model) {
		this.model = model;
	}
	
	
	protected IControllerManager getControllerManager() {
		return controllerManager;
	}
	

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
	

	@Override
	public T getModel() {
		return model;
	}

}
