package com.tokelon.toktales.core.engine;

public class NoopEngineLooper implements IEngineLooper {


	@Override
	public void loop(IEngineContext engineContext) throws EngineException {
	}

	@Override
	public void stop() {
	}

}
