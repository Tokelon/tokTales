package com.tokelon.toktales.android.input;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.InputCallbackException;

public class AndroidInputRegistration implements IAndroidInputRegistration {

	private final Set<IScreenButtonCallback> screenButtonCallbackSet;
	private final Set<IScreenPressCallback> screenPressCallbackSet;
	private final Set<IScreenPointerCallback> screenPointerCallbackSet;
	
	public AndroidInputRegistration() {
		screenButtonCallbackSet = Collections.synchronizedSet(new HashSet<IScreenButtonCallback>());
		screenPressCallbackSet = Collections.synchronizedSet(new HashSet<IScreenPressCallback>());
		screenPointerCallbackSet = Collections.synchronizedSet(new HashSet<IScreenPointerCallback>());
	}
	
	
	/** Remember to synchronize when iterating over the returned set.
	 * 
	 * @return The screen button callback set.
	 */
	protected Set<IScreenButtonCallback> getScreenButtonCallbackSet() {
		return screenButtonCallbackSet;
	}
	
	/** Remember to synchronize when iterating over the returned set.
	 * 
	 * @return The screen press callback set.
	 */
	protected Set<IScreenPressCallback> getScreenPressCallbackSet() {
		return screenPressCallbackSet;
	}
	
	/** Remember to synchronize when iterating over the returned set.
	 * 
	 * @return The screen pointer callback set.
	 */
	protected Set<IScreenPointerCallback> getScreenPointerCallbackSet() {
		return screenPointerCallbackSet;
	}
	
	
	
	@Override
	public boolean registerInputCallback(IInputCallback callback) {
		return internalRegisterInputCallback(callback, callback.getClass());
	}

	@Override
	public <T extends IInputCallback> boolean registerInputCallback(T callback, Class<T> callbackType) {
		return internalRegisterInputCallback(callback, callbackType);
	}
	
	@Override
	public void registerInputCallbackOrFail(IInputCallback callback) throws InputCallbackException {
		if(!internalRegisterInputCallback(callback, callback.getClass())) {
			throw new InputCallbackException("IAndroidInputRegistration does not support the callback type: " + callback.getClass());
		}
	}

	private boolean internalRegisterInputCallback(IInputCallback callback, Class<? extends IInputCallback> callbackType) {
		boolean registered = false;

		if(IScreenButtonCallback.class.isAssignableFrom(callbackType)) {
			registerScreenButtonCallback((IScreenButtonCallback) callback);
			registered = true;
		}
		if(IScreenPressCallback.class.isAssignableFrom(callbackType)) {
			registerScreenPressCallback((IScreenPressCallback) callback);
			registered = true;
		}
		if(IScreenPointerCallback.class.isAssignableFrom(callbackType)) {
			registerScreenPointerCallback((IScreenPointerCallback) callback);
			registered = true;
		}
		
		return registered;
	}
	
	
	@Override
	public boolean unregisterInputCallback(IInputCallback callback) {
		return internalUnregisterInputCallback(callback, callback.getClass());
	}
	
	@Override
	public <T extends IInputCallback> boolean unregisterInputCallback(T callback, Class<T> callbackType) {
		return internalUnregisterInputCallback(callback, callbackType);
	}
	
	private boolean internalUnregisterInputCallback(IInputCallback callback, Class<? extends IInputCallback> callbackType) {
		boolean unregistered = false;
		
		if(IScreenButtonCallback.class.isAssignableFrom(callbackType)) {
			unregistered = unregisterScreenButtonCallback((IScreenButtonCallback) callback) || unregistered;
		}
		if(IScreenPressCallback.class.isAssignableFrom(callbackType)) {
			unregistered = unregisterScreenPressCallback((IScreenPressCallback) callback) || unregistered;
		}
		if(IScreenPointerCallback.class.isAssignableFrom(callbackType)) {
			unregistered = unregisterScreenPointerCallback((IScreenPointerCallback) callback) || unregistered;
		}
		
		return unregistered;
	}
	
	
	@Override
	public boolean hasInputCallback(IInputCallback callback) {
		return internalHasInputCallback(callback, callback.getClass());
	}
	
	@Override
	public <T extends IInputCallback> boolean hasInputCallback(T callback, Class<T> callbackType) {
		return internalHasInputCallback(callback, callbackType);
	}
	
	private boolean internalHasInputCallback(IInputCallback callback, Class<? extends IInputCallback> callbackType) {
		boolean isRegistered = false;
		
		if(IScreenButtonCallback.class.isAssignableFrom(callbackType)) {
			isRegistered = hasScreenButtonCallback((IScreenButtonCallback) callback) || isRegistered;
		}
		if(IScreenPressCallback.class.isAssignableFrom(callbackType)) {
			isRegistered = hasScreenPressCallback((IScreenPressCallback) callback) || isRegistered;
		}
		if(IScreenPointerCallback.class.isAssignableFrom(callbackType)) {
			isRegistered = hasScreenPointerCallback((IScreenPointerCallback) callback) || isRegistered;
		}
		
		return isRegistered;
	}
	
	
	
	@Override
	public void registerScreenButtonCallback(IScreenButtonCallback callback) {
		screenButtonCallbackSet.add(callback);
	}

	@Override
	public boolean unregisterScreenButtonCallback(IScreenButtonCallback callback) {
		return screenButtonCallbackSet.remove(callback);
	}

	@Override
	public boolean hasScreenButtonCallback(IScreenButtonCallback callback) {
		return screenButtonCallbackSet.contains(callback);
	}
	
	
	@Override
	public void registerScreenPressCallback(IScreenPressCallback callback) {
		screenPressCallbackSet.add(callback);
	}

	@Override
	public boolean unregisterScreenPressCallback(IScreenPressCallback callback) {
		return screenPressCallbackSet.remove(callback);
	}
	
	@Override
	public boolean hasScreenPressCallback(IScreenPressCallback callback) {
		return screenPressCallbackSet.contains(callback);
	}
	
	
	@Override
	public void registerScreenPointerCallback(IScreenPointerCallback callback) {
		screenPointerCallbackSet.add(callback);
	}
	
	@Override
	public boolean unregisterScreenPointerCallback(IScreenPointerCallback callback) {
		return screenPointerCallbackSet.remove(callback);
	}
	
	@Override
	public boolean hasScreenPointerCallback(IScreenPointerCallback callback) {
		return screenPointerCallbackSet.contains(callback);
	}


}
