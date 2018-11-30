package com.tokelon.toktales.core.engine.input;

/** Produces input events that can then be passed to a {@link IInputConsumer}.
 */
public interface IInputProducer {
	
	
	public void postInput(IInputEvent event);
	
	
	/* TODO: Implement 
	public boolean isBuffered();
	public int getBufferSize();
	public int processBuffer();
	public List<IInputEvent> getBuffer();
	public List<IInputEvent> peekBuffer();
	*/
	//public List<IInputEvent> getNewEvents();
	//public List<IInputEvent> peekNewEvents();
	
	
	
	public interface IInputProducerFactory {
		
		public IInputProducer create(IInputDispatch dispatch);
	}
	
}
