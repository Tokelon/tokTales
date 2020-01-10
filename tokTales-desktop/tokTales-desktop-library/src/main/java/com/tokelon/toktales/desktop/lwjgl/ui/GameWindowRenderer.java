package com.tokelon.toktales.desktop.lwjgl.ui;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.IEngineDriver;
import com.tokelon.toktales.core.engine.render.ISurfaceHandler;

public class GameWindowRenderer extends LWJGLWindowRenderer {


	private IEngineDriver engineDriver;

	@Inject
	public GameWindowRenderer(IEngineDriver engineDriver, ISurfaceHandler surfaceHandler) {
		super(surfaceHandler);
		
		this.engineDriver = engineDriver;
	}
	
	
	@Override
	public void drawFrame() {
		super.drawFrame();

		engineDriver.render();
	}
	
}
