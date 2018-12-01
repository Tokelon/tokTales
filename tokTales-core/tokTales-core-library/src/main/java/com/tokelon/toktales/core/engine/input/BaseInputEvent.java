package com.tokelon.toktales.core.engine.input;

public class BaseInputEvent implements IInputEvent {
	
	
	private boolean isHandled = false;


	@Override
	public boolean markHandledIf(boolean condition) {
		if(condition) {
			return markHandled();
		}
		else {
			return false;
		}
	}

	@Override
	public boolean markHandled() {
		boolean prevHandled = isHandled;
		isHandled = true;
		
		return prevHandled;
	}

	@Override
	public boolean resetHandled() {
		boolean prevHandled = isHandled;
		isHandled = false;
		
		return prevHandled;
	}
	

	@Override
	public boolean isHandled() {
		return isHandled;
	}

}
