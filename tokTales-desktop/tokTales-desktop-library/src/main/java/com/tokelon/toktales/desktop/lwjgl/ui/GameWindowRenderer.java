package com.tokelon.toktales.desktop.lwjgl.ui;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.IEngineDriver;
import com.tokelon.toktales.core.screen.surface.ISurfaceManager;

public class GameWindowRenderer extends LWJGLWindowRenderer {


	private IEngineDriver engineDriver;

	@Inject
	public GameWindowRenderer(IEngineDriver engineDriver, ISurfaceManager surfaceManager) {
		super(surfaceManager);
		
		this.engineDriver = engineDriver;
	}
	
	
	@Override
	public void drawFrame() {
		super.drawFrame();

		engineDriver.render();
	}
	
}
