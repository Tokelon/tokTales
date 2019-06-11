package com.tokelon.toktales.android.input;

import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.android.input.dispatch.IAndroidInputDispatch;
import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.inject.annotation.services.InputServiceExtensions;

public class AndroidInputService extends AbstractEngineService implements IAndroidInputService {


	private final IAndroidInputDispatch mainInputDispatch;

	public AndroidInputService(IAndroidInputDispatch mainInputDispatch) {
		this.mainInputDispatch = mainInputDispatch;
	}
	
	@Inject
	public AndroidInputService(IAndroidInputDispatch mainInputDispatch, @InputServiceExtensions Map<String, IServiceExtension> extensions) {
		super(extensions);
		
		this.mainInputDispatch = mainInputDispatch;
	}
	
	
	@Override
	public IAndroidInputDispatch getMainInputDispatch() {
		return mainInputDispatch;
	}

}
