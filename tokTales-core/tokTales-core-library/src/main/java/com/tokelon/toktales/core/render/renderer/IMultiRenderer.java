package com.tokelon.toktales.core.render.renderer;

import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

/** A renderer that supports rendering multiple named contents.
 */
public interface IMultiRenderer extends IRenderer { // ISegmentRenderer
	//public IRenderCall getContentCall(String contentName);


	/** Prepares this renderer for rendering the contents for the next frame.
	 * <p>
	 * Must be called once per frame, before all other render calls.
	 * 
	 * @param currentTimeMillis
	 */
	public void prepareFrame(long currentTimeMillis);
	
	
	/** Renders the content for the given name with the given options.
	 * <p>
	 * If there is no content for that name, does nothing.
	 * 
	 * @param contentName
	 * @param options
	 */
	public void renderContent(String contentName, INamedOptions options);
	
}
