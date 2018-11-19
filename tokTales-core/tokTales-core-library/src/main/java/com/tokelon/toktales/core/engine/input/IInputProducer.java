package com.tokelon.toktales.core.engine.input;

/** Post your input here.
 * <br>
 * Usually contains the master input handlers which forward the input to the {@link IInputConsumer}.
 *
 */
public interface IInputProducer {
	
	/* generic version
	 * 
	 */
	//public void invoke(String target, String action);
	//public void invoke(String target, String action, Object... params);
	
	/* or as a separate callback
	 * 
	 *//*
	public IGenericInputCallback getGenericInput();
	public interface IGenericInputCallback {

		//public void invoke(String target, String action);
		//public void invoke(String target, String action, Object... params);
	}
	*/
	
}
