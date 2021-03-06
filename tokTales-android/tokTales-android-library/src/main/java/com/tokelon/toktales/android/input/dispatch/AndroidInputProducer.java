package com.tokelon.toktales.android.input.dispatch;

import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPointerInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPressInputEvent;
import com.tokelon.toktales.core.engine.input.IInputEvent;

public class AndroidInputProducer implements IAndroidInputProducer {


	private final IAndroidInputDispatch dispatch;

	public AndroidInputProducer(IAndroidInputDispatch dispatch) {
		this.dispatch = dispatch;
	}


	@Override
	public void postInput(IInputEvent event) {
		dispatch.getInputConsumer().getMasterInputCallback().handle(event);
	}

	@Override
	public void postScreenButtonInput(IScreenButtonInputEvent event) {
		dispatch.getInputConsumer().getMasterScreenButtonCallback().handleScreenButtonInput(event);
	}

	@Override
	public void postScreenPointerInput(IScreenPointerInputEvent event) {
		dispatch.getInputConsumer().getMasterScreenPointerCallback().handleScreenPointerInput(event);
	}

	@Override
	public void postScreenPressInput(IScreenPressInputEvent event) {
		dispatch.getInputConsumer().getMasterScreenPressCallback().handleScreenPressInput(event);
	}


	public static class AndroidInputProducerFactory implements IAndroidInputProducerFactory {

		@Override
		public IAndroidInputProducer create(IAndroidInputDispatch dispatch) {
			return new AndroidInputProducer(dispatch);
		}
	}

}
