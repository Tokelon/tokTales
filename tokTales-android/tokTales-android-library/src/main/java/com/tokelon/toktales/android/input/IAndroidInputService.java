package com.tokelon.toktales.android.input;

import com.tokelon.toktales.android.input.dispatch.IAndroidInputDispatch;
import com.tokelon.toktales.core.engine.input.IInputService;

public interface IAndroidInputService extends IInputService {

	
	@Override
	public IAndroidInputDispatch getMainInputDispatch();
	
}
