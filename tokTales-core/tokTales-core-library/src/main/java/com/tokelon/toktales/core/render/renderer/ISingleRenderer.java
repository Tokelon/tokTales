package com.tokelon.toktales.core.render.renderer;

/** A renderer that renders once per frame.
 */
public interface ISingleRenderer extends IRenderer { // IFrameRenderer, IContentRenderer


	/** Renders the content.
	 */
	public void renderContents(); // Add time millis parameter? INamedOptions?

}
