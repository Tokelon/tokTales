package com.tokelon.toktales.desktop.input;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputDispatch;

public class DesktopInputService extends AbstractEngineService implements IDesktopInputService {

	
	private final IDesktopInputDispatch mainInputDispatch;

	@Inject
	public DesktopInputService(IDesktopInputDispatch mainInputDispatch) {
		this.mainInputDispatch = mainInputDispatch;
	}
	

	@Override
	public IDesktopInputDispatch getMainInputDispatch() {
		return mainInputDispatch;
	}

}
