package com.tokelon.toktales.desktop.lwjgl;

import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.inject.annotation.services.InputServiceExtensions;
import com.tokelon.toktales.desktop.input.DesktopInputService;

public class LWJGLInputService extends DesktopInputService implements ILWJGLInputService {


	private final ILWJGLInputDispatch mainInputDispatch;

	public LWJGLInputService(ILWJGLInputDispatch mainInputDispatch) {
		super(mainInputDispatch);

		this.mainInputDispatch = mainInputDispatch;
	}

	@Inject
	public LWJGLInputService(ILWJGLInputDispatch mainInputDispatch, @InputServiceExtensions Map<String, IServiceExtension> extensions) {
		super(mainInputDispatch, extensions);

		this.mainInputDispatch = mainInputDispatch;
	}


	@Override
	public ILWJGLInputDispatch getMainInputDispatch() {
		return mainInputDispatch;
	}

}
