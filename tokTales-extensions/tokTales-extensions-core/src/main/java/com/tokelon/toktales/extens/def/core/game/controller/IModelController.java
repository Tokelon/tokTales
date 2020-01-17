package com.tokelon.toktales.extens.def.core.game.controller;

import com.tokelon.toktales.core.game.controller.IController;

public interface IModelController<T> extends IController {

	public T getModel();
	
	//public void setModel(T object);
	
}
