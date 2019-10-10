package com.tokelon.toktales.core.game.screen;

import com.tokelon.toktales.core.render.IRenderer;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

public interface ISegmentRenderer extends IRenderer {

	// TODO: Strongly consider removing or refactor to be useful with layers
	public void prepare(long currentTimeMillis);
	
	
	//public void drawLayer(INamedOptions options, int layerIndex, int layersRemaining);
	public void drawLayer(INamedOptions options, String layerName);
	
	
	// Just do nothing instead of throwing an exception?
	
	/** Renderers should implement this if they can draw full without a layer index.
	 * 
	 * @param options
	 * @throws UnsupportedOperationException If draw full makes no sense for this renderer.
	 */
	public void drawFull(INamedOptions options);
	
}
