package com.tokelon.toktales.core.render;

public interface IChunkRenderer extends IRenderer {
	/* Maybe even put these into IRenderer
	 * What if a segment renderer uses multiple drivers though?
	 * Also generally speaking a segment renderer does not render multiple times for one frame.
	 */


	public void startBatchDraw();
	
	public void finishBatchDraw();
	
}
