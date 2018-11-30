package com.tokelon.toktales.core.engine.input;

public interface IInputDispatch {
	/*
	// Would call the producer - what if it is not buffered?
	public int processEvents();

	// Weird - Would have to replace the consumer or the producer with noop
	public void startProcessing();
	public void stopProcessing();
	*/
	
	
	public IInputProducer getInputProducer();
	
	public IInputConsumer getInputConsumer();
	
}
