package com.tokelon.toktales.android.input.dispatch;

import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPointerInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPressInputEvent;
import com.tokelon.toktales.core.engine.input.IInputProducer;

public interface IAndroidInputProducer extends IInputProducer {

	
	public void postScreenButtonInput(IScreenButtonInputEvent event);
	
	public void postScreenPointerInput(IScreenPointerInputEvent event);
	
	public void postScreenPressInput(IScreenPressInputEvent event);
	
	
	public interface IAndroidInputProducerFactory {
		
		public IAndroidInputProducer create(IAndroidInputDispatch dispatch);
	}
	
}
