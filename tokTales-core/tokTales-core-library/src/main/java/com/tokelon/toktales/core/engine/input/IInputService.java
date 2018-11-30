package com.tokelon.toktales.core.engine.input;

import com.tokelon.toktales.core.engine.IEngineService;

public interface IInputService extends IEngineService {
	
	
	public IInputDispatch getMainInputDispatch();
	
}
