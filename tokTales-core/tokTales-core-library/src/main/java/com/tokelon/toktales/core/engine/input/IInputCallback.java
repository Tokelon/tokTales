package com.tokelon.toktales.core.engine.input;

/** Parent type for all input callbacks.
 * <p>
 * Used as generic type in core usage.
 * In a platform specific context it will need to be cast into one of it's subtypes.
 *
 */
public interface IInputCallback {

	/* Add support for generic callback?
	 * would work like: invokeCallback("VK_SPACE", "DOWN");
	 */
	//public boolean invokeCallback(String target, String action);
	//public boolean invokeCallback(String target, String action, Object... params);
	
}
