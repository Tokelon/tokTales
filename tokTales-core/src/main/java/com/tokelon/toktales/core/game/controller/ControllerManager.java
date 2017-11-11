package com.tokelon.toktales.core.game.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ControllerManager implements IControllerManager {
	
	private final Map<String, IController> controllerMap;
	
	
	public ControllerManager() {
		controllerMap = Collections.synchronizedMap(new HashMap<String, IController>());
	}
	
	
	@Override
	public void setController(String controllerID, IController controller) {
		if(controllerID == null) {
			throw new NullPointerException();
		}
		
		controllerMap.put(controllerID, controller);
		controller.setup(this);
		reloadControllers(controllerID);
	}
	
	@Override
	public IController getController(String controllerID) {
		return controllerMap.get(controllerID);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IController> T getControllerAs(String id, Class<T> type) {
		IController controller = controllerMap.get(id);
		if(type.isInstance(controller)) {
			return (T) controller;
		}
		else {
			return null;
		}
	}
	
	
	private void reloadControllers(String controllerID) {
		synchronized (controllerMap) {
			for(IController controller: controllerMap.values()) {
				if(controller != null) controller.onControllerChange(this, controllerID);
			}
		}
	}
	

}
