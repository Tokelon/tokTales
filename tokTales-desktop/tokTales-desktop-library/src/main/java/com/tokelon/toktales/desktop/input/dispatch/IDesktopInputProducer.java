package com.tokelon.toktales.desktop.input.dispatch;

import com.tokelon.toktales.core.engine.input.IInputProducer;
import com.tokelon.toktales.desktop.input.events.ICharInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorEnterInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorPosInputEvent;
import com.tokelon.toktales.desktop.input.events.IKeyInputEvent;
import com.tokelon.toktales.desktop.input.events.IMouseButtonInputEvent;

public interface IDesktopInputProducer extends IInputProducer {
	
	
	public void postMouseButtonInput(IMouseButtonInputEvent event);
	
	public void postCursorEnterInput(ICursorEnterInputEvent event);
	
	public void postCursorPosInput(ICursorPosInputEvent event);
	
	public void postKeyInput(IKeyInputEvent event);
	
	public void postCharInput(ICharInputEvent event);
	
	
	public interface IDesktopInputProducerFactory {
		
		public IDesktopInputProducer create(IDesktopInputDispatch dispatch);
	}
	
}
