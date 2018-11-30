package com.tokelon.toktales.desktop.input;

import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputDispatch;

public interface IDesktopInputService extends IInputService {

	
	@Override
	public IDesktopInputDispatch getMainInputDispatch();
	
}
