package com.tokelon.toktales.core.render;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.core.engine.render.ISurfaceController;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;

public interface ISurfaceManager {
	// Add these?
	//public void createSurface(String name, int width, int height);
	//public void createSurface(String name, IScreenViewport viewport, Matrix4f projectionMatrix);
	

	/** Publishes the current surface so that it can be used.
	 */
	public void publishSurface();

	/** Updates the currently published surface.
	 * <p>
	 * This indicates that the surface's properties may have changed.
	 */
	public void updateSurface();

	/** Recalls the currently published surface so that it's won't be used anymore.
	 */
	public void recallSurface();

	
	/** Creates a surface with the given properties and assigns it as current.
	 * <p>
	 * If a surface name has been set with {@link #setSurfaceName(String)} it will be used, otherwise a default name will be generated.
	 * 
	 * @param width
	 * @param height
	 */
	public void createSurface(int width, int height);
	
	/** Creates a surface with the given properties and assigns it as current.
	 * <p>
	 * If a surface name has been set with {@link #setSurfaceName(String)} it will be used, otherwise a default name will be generated.
	 * 
	 * @param viewport
	 * @param projectionMatrix
	 */
	public void createSurface(IScreenViewport viewport, Matrix4f projectionMatrix);

	/** Updates the current surface with the given properties.
	 * 
	 * @param width
	 * @param height
	 */
	public void updateSurface(int width, int height);

	/** Updates the current surface with the given properties.
	 * 
	 * @param viewport
	 * @param projectionMatrix
	 */
	public void updateSurface(IScreenViewport viewport, Matrix4f projectionMatrix);

	
	/** Returns the surface created by {@link #createSurface}.
	 * 
	 * @return The current surface, or null if there is none.
	 */
	public ISurface getSurface();

	
	/** Sets the name that will be used for the surface.
	 * <p>
	 * Only has an effect if called before {@link #createSurface}.
	 * 
	 * @param name
	 */
	public void setSurfaceName(String name);
	
	/**
	 * @return The set surface name, or a generated one if no name has been set.
	 */
	public String getSurfaceName();
	
	
	
	public interface ISurfaceManagerFactory {
		
		public ISurfaceManager create(ISurfaceController surfaceController);
	}
	
}