package com.tokelon.toktales.android.input.dispatch;

import com.tokelon.toktales.core.engine.input.IInputDispatch;

public interface IAndroidInputDispatch extends IInputDispatch {

	
	@Override
	public IAndroidInputProducer getInputProducer();
	
	@Override
	public IAndroidInputConsumer getInputConsumer();
	
}
