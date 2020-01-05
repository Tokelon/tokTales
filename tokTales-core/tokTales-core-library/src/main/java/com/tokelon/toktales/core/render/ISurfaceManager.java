package com.tokelon.toktales.core.render;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.core.engine.render.ISurfaceController;
import com.tokelon.toktales.core.engine.render.ISurfaceHandler;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;

public interface ISurfaceManager {


	public void publishSurface();

	public void updateSurface();

	public void recallSurface();

	public void createSurface(String name, int width, int height);

	public void createSurface(String name, IScreenViewport viewport, Matrix4f projectionMatrix);

	public void updateSurface(int width, int height);

	public void updateSurface(IScreenViewport viewport, Matrix4f projectionMatrix);

	public ISurface getSurface();
	
	
	public interface ISurfaceManagerFactory {
		
		public ISurfaceManager create(ISurfaceHandler surfaceHandler, ISurfaceController surfaceController);
	}
	
}