package com.tokelon.toktales.core.render.renderer;

import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

/** A renderer that renders once per frame.
 */
public interface ISingleRenderer extends IRenderer { // IFrameRenderer, IContentRenderer


	/** Renders the contents of this renderer with the given options.
	 * 
	 * @param renderOptions
	 */
	public void renderContents(INamedOptions renderOptions); // Add time millis parameter?

}
