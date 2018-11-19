package com.tokelon.toktales.core.engine.input;

import com.tokelon.toktales.core.engine.IEngineService;

public interface IInputService extends IEngineService {

	/* TODO: Consider how we would add custom inputs as extensions (different callbacks)
	 */
	
	public IInputProducer getInputPoster();
	
	public IInputConsumer getInputDispatcher();
	
}
