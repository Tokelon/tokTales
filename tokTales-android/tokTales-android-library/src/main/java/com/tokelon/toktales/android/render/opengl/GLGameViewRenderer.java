package com.tokelon.toktales.android.render.opengl;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.IEngineDriver;
import com.tokelon.toktales.core.engine.log.ILogging;

public class GLGameViewRenderer extends GLViewRenderer {


	private final IEngineDriver engineDriver;

	@Inject
	public GLGameViewRenderer(ILogging logging, IEngineDriver engineDriver) {
		super(logging);
		
		this.engineDriver = engineDriver;
	}

	
	@Override
	public void onDrawFrame() {
		super.onDrawFrame();

		engineDriver.render();
	}
	
}
