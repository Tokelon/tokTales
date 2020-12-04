package com.tokelon.toktales.core.render.renderer;

public interface IBatchRenderer extends IRenderer {


	/** Instructs the renderer to initialize it's rendering context.
	 */
	public void startBatch();

	/** Instructs the renderer to clear it's rendering context.
	 */
	public void finishBatch();
	
}
