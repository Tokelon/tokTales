package com.tokelon.toktales.desktop.input;

import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.inject.annotation.services.InputServiceExtensions;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputDispatch;

public class DesktopInputService extends AbstractEngineService implements IDesktopInputService {


	private final IDesktopInputDispatch mainInputDispatch;

	public DesktopInputService(IDesktopInputDispatch mainInputDispatch) {
		this.mainInputDispatch = mainInputDispatch;
	}
	
	@Inject
	public DesktopInputService(IDesktopInputDispatch mainInputDispatch, @InputServiceExtensions Map<String, IServiceExtension> extensions) {
		super(extensions);
		
		this.mainInputDispatch = mainInputDispatch;
	}
	

	@Override
	public IDesktopInputDispatch getMainInputDispatch() {
		return mainInputDispatch;
	}

}
