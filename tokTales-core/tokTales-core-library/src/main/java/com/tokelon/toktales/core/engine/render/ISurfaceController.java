package com.tokelon.toktales.core.engine.render;

public interface ISurfaceController {


	/** Queues an event on the render thread of this controller's surface.
	 * 
	 * @param event
	 */
	public void queueEvent(Runnable event);
	
}
