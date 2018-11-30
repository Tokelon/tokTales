package com.tokelon.toktales.android.input;

import javax.inject.Inject;

import com.tokelon.toktales.android.input.dispatch.IAndroidInputDispatch;
import com.tokelon.toktales.core.engine.AbstractEngineService;

public class AndroidInputService extends AbstractEngineService implements IAndroidInputService {
	
	
	private final IAndroidInputDispatch mainInputDispatch;

	@Inject
	public AndroidInputService(IAndroidInputDispatch mainInputDispatch) {
		this.mainInputDispatch = mainInputDispatch;
	}
	
	
	@Override
	public IAndroidInputDispatch getMainInputDispatch() {
		return mainInputDispatch;
	}

}
