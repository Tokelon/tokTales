package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineInputProcessor;
import com.tokelon.toktales.core.engine.IEngineLooper;

/** Implementation of {@link IEngineLooper} that does no rendering.
 * <p>
 * Supports updating state and processing input through the engine driver.
 * <p>
 * Use this if you call render on the engine driver externally but still need state updating and/or input processing.
 */
public class RenderAgnosticEngineLooper extends AbstractEngineLooper {


	/** Default constructor.
	 * <p>
	 * A no-op input processor will be used.
	 */
	public RenderAgnosticEngineLooper() {
		super();
	}

	/** Constructor with an input processor.
	 *
	 * @param inputProcessor
	 */
	public RenderAgnosticEngineLooper(IEngineInputProcessor inputProcessor) {
		super(inputProcessor);
	}


	@Override
	protected void render(IEngineContext engineContext) {
		// Nothing
	}

}
