package com.tokelon.toktales.extensions.core.game.controller;

import com.tokelon.toktales.core.game.controller.IController;

public interface IModelController<T> extends IController {

	public T getModel();
	
	//public void setModel(T object);
	
}
