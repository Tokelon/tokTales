package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineInputProcessor;

public class RenderAgnosticEngineLooper extends AbstractEngineLooper {


	/** Default constructor.
	 * <p>
	 * A no-op input processor will be used.
	 * 
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
