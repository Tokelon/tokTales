package com.tokelon.toktales.desktop.lwjgl.ui;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.IEngineDriver;
import com.tokelon.toktales.core.screen.surface.ISurfaceManager;
import com.tokelon.toktales.desktop.lwjgl.input.IGLFWInputRegistration;
import com.tokelon.toktales.desktop.render.IWindowRenderer;

/** Implementation of {@link IWindowRenderer} for the engine, that calls render on the engine driver.
 */
public class GameWindowRenderer extends LWJGLWindowRenderer {


	private IEngineDriver engineDriver;

	/** Constructor with a surface manager, input registration and engine driver.
	 *
	 * @param surfaceManager
	 * @param inputRegistration
	 * @param engineDriver
	 */
	@Inject
	public GameWindowRenderer(ISurfaceManager surfaceManager, IGLFWInputRegistration inputRegistration, IEngineDriver engineDriver) {
		super(surfaceManager, inputRegistration);

		this.engineDriver = engineDriver;
	}


	@Override
	public void drawFrame() {
		super.drawFrame();

		engineDriver.render();
	}

}
