package com.tokelon.toktales.extens.def.core.game.controller;

import com.tokelon.toktales.core.game.controller.IControllerManager;

public class ModelController<T> implements IModelController<T> {

	private T model;
	
	private IControllerManager cm;
	
	
	public ModelController() { }
	
	public ModelController(T model) {
		this.model = model;
	}
	
	
	protected IControllerManager getControllerManager() {
		return cm;
	}
	
	
	@Override
	public void setup(IControllerManager cm) {
		this.cm = cm;
	}

	@Override
	public void onControllerChange(IControllerManager cm, String controllerId) {
		// Nothing
	}

	@Override
	public void action(String action, Object... args) {
		// Nothing
	}

	@Override
	public T getModel() {
		return model;
	}

	
}
