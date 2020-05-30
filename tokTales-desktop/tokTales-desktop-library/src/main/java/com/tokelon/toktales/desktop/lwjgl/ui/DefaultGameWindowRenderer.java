package com.tokelon.toktales.desktop.lwjgl.ui;

import javax.inject.Inject;

import org.lwjgl.opengl.GL11;

import com.tokelon.toktales.core.engine.IEngineDriver;
import com.tokelon.toktales.core.screen.surface.ISurfaceManager;
import com.tokelon.toktales.desktop.lwjgl.input.IGLFWInputRegistration;
import com.tokelon.toktales.desktop.render.IWindowRenderer;

/** Default implementation of {@link IWindowRenderer} for the engine, that calls render on the engine driver.
 */
public class DefaultGameWindowRenderer extends GameWindowRenderer {


	/** Constructor with a surface manager, input registration and engine driver.
	 *
	 * @param surfaceManager
	 * @param inputRegistration
	 * @param engineDriver
	 */
	@Inject
	public DefaultGameWindowRenderer(ISurfaceManager surfaceManager, IGLFWInputRegistration inputRegistration, IEngineDriver engineDriver) {
		super(surfaceManager, inputRegistration, engineDriver);
	}


	@Override
	public void prepareFrame() {
		super.prepareFrame();

		// Reset the surface by clearing what's rendered
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); // | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT

		// Set the coordinates for the GL viewport
		GL11.glViewport(0, 0, getWindow().getFrameBufferWidth(), getWindow().getFrameBufferHeight());

		// Enable blending for transparency
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

}
