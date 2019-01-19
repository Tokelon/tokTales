package com.tokelon.toktales.core.engine.input;

public interface IInputRegistration {
	// TODO: Update documentation with logic for general callback
	//@return True if the callback was registered for a platform type, false if was only registered for the general type.
	
	// Add *Fail version to other methods?
	

	/** Registers the given callback for all types possible.
	 * Registration will not be possible if the callback does not implement any supported types.
	 * 
	 * @param callback
	 * @return True if the callback was registered, false if not.
	 */
	public boolean registerInputCallback(IInputCallback callback);
	
	/** Registers the given callback for the given type, if possible.
	 * Registration will not be possible if the callback type is not supported.
	 * 
	 * @param callback
	 * @param callbackType
	 * @return True if the callback was registered, false if not.
	 */
	public <T extends IInputCallback> boolean registerInputCallback(T callback, Class<T> callbackType);
	
	/** Registers the given callback if possible.
	 * Registration will fail with an exception if the callback does not implement any supported types.
	 * 
	 * @param callback
	 * @throws InputCallbackException If the callback type is not supported.
	 */
	public void registerInputCallbackOrFail(IInputCallback callback) throws InputCallbackException;
	
	
	/** Unregisters the given callback for all types.
	 * 
	 * @param callback
	 * @return True if the callback was registered before, false if not.
	 */
	public boolean unregisterInputCallback(IInputCallback callback);
	
	/** Unregisters the given callback for the given type.
	 * 
	 * @param callback
	 * @param callbackType
	 * @return True if the callback was registered before, false if not.
	 */
	public <T extends IInputCallback> boolean unregisterInputCallback(T callback, Class<T> callbackType);
	
	
	/** Checks if the given callback is registered as any type.
	 * 
	 * @param callback
	 * @return True if the callback is registered, false if not.
	 */
	public boolean hasInputCallback(IInputCallback callback);
	
	/** Checks if the given callback is registered as the given type.
	 * 
	 * @param callback
	 * @param callbackType
	 * @return True if the callback is registered, false if not.
	 */
	public <T extends IInputCallback> boolean hasInputCallback(T callback, Class<T> callbackType);
	
}
