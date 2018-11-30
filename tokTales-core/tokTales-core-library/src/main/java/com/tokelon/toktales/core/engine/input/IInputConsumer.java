package com.tokelon.toktales.core.engine.input;

/** Consumes input events by passing them to registered callbacks.
 */
public interface IInputConsumer extends IInputCallback, IInputRegistration {
	// Could have a system where consumers are registered differently, so the actual callbacks get executed first

	
	public interface IInputConsumerFactory {
		
		public IInputConsumer create(IInputDispatch dispatch);
	}
	
}
