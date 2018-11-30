package com.tokelon.toktales.android.input.events;

import com.tokelon.toktales.core.engine.input.IInputEvent;

interface ITextInputEvent extends IInputEvent {
	// TODO: Implement
	
	
	//public View getSource();
	public int getCodepoint();
	
}
