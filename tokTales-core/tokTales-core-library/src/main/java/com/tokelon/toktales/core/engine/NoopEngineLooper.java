package com.tokelon.toktales.core.engine;

/** Implementation of {@link IEngineLooper} that does nothing.
 */
public class NoopEngineLooper implements IEngineLooper {


	@Override
	public void loop(IEngineContext engineContext) throws EngineException {
	}

	@Override
	public void stop() {
	}

}
