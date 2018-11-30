package com.tokelon.toktales.core.engine.input;

/** Parent type for all input callbacks.
 * <p>
 * Used as generic type in core usage.
 * In a platform specific context it will need to be cast into one of it's subtypes.
 */
public interface IInputCallback {
	
	
	/** Handles the given event.
	 * 
	 * @param event
	 * @return True if the event was handled, false if not.
	 */
	public boolean handle(IInputEvent event);
	
}
