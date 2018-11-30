package com.tokelon.toktales.desktop.input.dispatch;

import com.tokelon.toktales.core.engine.input.IInputEvent;
import com.tokelon.toktales.desktop.input.events.ICharInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorEnterInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorPosInputEvent;
import com.tokelon.toktales.desktop.input.events.IKeyInputEvent;
import com.tokelon.toktales.desktop.input.events.IMouseButtonInputEvent;

public class DesktopInputProducer implements IDesktopInputProducer {
	// TODO: Forward specific events to generic handle?
	
	private final IDesktopInputDispatch dispatch;

	public DesktopInputProducer(IDesktopInputDispatch dispatch) {
		this.dispatch = dispatch;
	}

	
	@Override
	public void postInput(IInputEvent event) {
		dispatch.getInputConsumer().handle(event);
	}

	@Override
	public void postMouseButtonInput(IMouseButtonInputEvent event) {
		dispatch.getInputConsumer().handleMouseButtonInput(event);
	}

	@Override
	public void postCursorEnterInput(ICursorEnterInputEvent event) {
		dispatch.getInputConsumer().handleCursorEnterInput(event);
	}

	@Override
	public void postCursorPosInput(ICursorPosInputEvent event) {
		dispatch.getInputConsumer().handleCursorPosInput(event);
	}

	@Override
	public void postKeyInput(IKeyInputEvent event) {
		dispatch.getInputConsumer().handleKeyInput(event);
	}

	@Override
	public void postCharInput(ICharInputEvent event) {
		dispatch.getInputConsumer().handleCharInput(event);
	}
	
	
	public static class DesktopInputProducerFactory implements IDesktopInputProducerFactory {
		
		@Override
		public IDesktopInputProducer create(IDesktopInputDispatch dispatch) {
			return new DesktopInputProducer(dispatch);
		}
	}

}
