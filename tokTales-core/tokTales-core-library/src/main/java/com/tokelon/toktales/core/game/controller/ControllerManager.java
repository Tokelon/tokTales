package com.tokelon.toktales.core.game.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ControllerManager implements IControllerManager {
	
	
	private final Map<String, IController> controllerMap;
	private final Set<IControllerChangeListener> changeListenerSet;
	
	public ControllerManager() {
		controllerMap = Collections.synchronizedMap(new HashMap<>());
		changeListenerSet = Collections.synchronizedSet(new HashSet<>());
	}
	
	
	@Override
	public void setController(String controllerId, IController controller) {
		if(controllerId == null || controller == null) {
			throw new NullPointerException();
		}
		
		IController previousController = controllerMap.put(controllerId, controller);
		
		changeListenerSet.add(controller); // Add controller to change listener set
		
		synchronized (changeListenerSet) {
			for(IControllerChangeListener listener: changeListenerSet) {
				if(previousController == null) {
					listener.onControllerAdd(this, controllerId, controller);	
				}
				else {
					listener.onControllerChange(this, controllerId, previousController, controller);
				}
			}
		}
	}
	

	@Override
	public IController removeController(String controllerId) {
		if(controllerId == null) {
			throw new NullPointerException();
		}
		
		IController removedController = controllerMap.get(controllerId);
		
		if(removedController != null) {
			synchronized (changeListenerSet) {
				for(IControllerChangeListener listener: changeListenerSet) {
					listener.onControllerRemove(this, controllerId, removedController);
				}
			}
		}
		
		changeListenerSet.remove(removedController); // Remove controller from change listener set
		
		return removedController;
	}


	@Override
	public boolean hasController(String controllerId) {
		if(controllerId == null) {
			throw new NullPointerException();
		}
		
		return controllerMap.containsKey(controllerId);
	}

	@Override
	public boolean hasControllerAs(String controllerId, Class<?> type) {
		if(controllerId == null || type == null) {
			throw new NullPointerException();
		}
		
		return type.isInstance(controllerMap.containsKey(controllerId));
	}
	
	
	@Override
	public IController getController(String controllerId) {
		if(controllerId == null) {
			throw new NullPointerException();
		}
		
		return controllerMap.get(controllerId);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IController> T getControllerAs(String controllerId, Class<T> type) {
		if(controllerId == null || type == null) {
			throw new NullPointerException();
		}
		
		IController controller = controllerMap.get(controllerId);
		if(type.isInstance(controller)) {
			return (T) controller;
		}
		else {
			return null;
		}
	}
	

	@Override
	public void addListener(IControllerChangeListener listener) {
		if(listener == null) {
			throw new NullPointerException();
		}
		
		changeListenerSet.add(listener);
	}

	@Override
	public void removeListener(IControllerChangeListener listener) {
		if(listener == null) {
			throw new NullPointerException();
		}
		
		changeListenerSet.remove(listener);
	}

	@Override
	public boolean hasListener(IControllerChangeListener listener) {
		if(listener == null) {
			throw new NullPointerException();
		}
		
		return changeListenerSet.contains(listener);
	}

}
