package com.tokelon.toktales.core.game.screen.order;

public interface IRenderCallback {
	
	// add prepare() and isReady()/canRender() ?
	// add callCount ?
	
	
	/** Calls this with the parameters given from the current context.
	 * 
	 * @param layerName The layer name of the calling context.
	 * @param stackPosition the stack position of the calling context.
	 */
	public void renderCall(String layerName, double stackPosition);
	
	
	/**
	 * @return A brief description or name for this callback.
	 */
	public String getDescription();
	
}
