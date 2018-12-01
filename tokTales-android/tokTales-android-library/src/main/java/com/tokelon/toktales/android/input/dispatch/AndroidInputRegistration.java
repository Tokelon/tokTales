package com.tokelon.toktales.android.input.dispatch;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.InputCallbackException;

public class AndroidInputRegistration implements IAndroidInputRegistration {
	// TODO: Always register for generic type?
	
	
	private final Set<IInputCallback> generalInputCallbackSet;
	private final Set<IScreenButtonCallback> screenButtonCallbackSet;
	private final Set<IScreenPressCallback> screenPressCallbackSet;
	private final Set<IScreenPointerCallback> screenPointerCallbackSet;
	
	public AndroidInputRegistration() {
		generalInputCallbackSet = Collections.synchronizedSet(new HashSet<IInputCallback>());
		screenButtonCallbackSet = Collections.synchronizedSet(new HashSet<IScreenButtonCallback>());
		screenPressCallbackSet = Collections.synchronizedSet(new HashSet<IScreenPressCallback>());
		screenPointerCallbackSet = Collections.synchronizedSet(new HashSet<IScreenPointerCallback>());
	}
	
	
	/** Remember to synchronize when iterating over the returned set.
	 * 
	 * @return The general input callback set.
	 */
	protected Set<IInputCallback> getGeneralInputCallbackSet() {
		return generalInputCallbackSet;
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
		boolean registeredCustom = false;
		
		if(IInputCallback.class.equals(callbackType)) {
			registerGeneralCallback(callback);
			return true;
		}

		if(IScreenButtonCallback.class.isAssignableFrom(callbackType)) {
			registerScreenButtonCallback((IScreenButtonCallback) callback);
			registeredCustom = true;
		}
		if(IScreenPressCallback.class.isAssignableFrom(callbackType)) {
			registerScreenPressCallback((IScreenPressCallback) callback);
			registeredCustom = true;
		}
		if(IScreenPointerCallback.class.isAssignableFrom(callbackType)) {
			registerScreenPointerCallback((IScreenPointerCallback) callback);
			registeredCustom = true;
		}
		
		if(!registeredCustom) {
			registerGeneralCallback(callback);
		}
		
		return registeredCustom;
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
		boolean unregisteredCustom = false;
		
		if(IInputCallback.class.equals(callbackType)) {
			return unregisterGeneralCallback(callback);
		}
		
		if(IScreenButtonCallback.class.isAssignableFrom(callbackType)) {
			unregisteredCustom = unregisterScreenButtonCallback((IScreenButtonCallback) callback) || unregisteredCustom;
		}
		if(IScreenPressCallback.class.isAssignableFrom(callbackType)) {
			unregisteredCustom = unregisterScreenPressCallback((IScreenPressCallback) callback) || unregisteredCustom;
		}
		if(IScreenPointerCallback.class.isAssignableFrom(callbackType)) {
			unregisteredCustom = unregisterScreenPointerCallback((IScreenPointerCallback) callback) || unregisteredCustom;
		}
		
		if(!unregisteredCustom) {
			unregisterGeneralCallback(callback);
		}
		
		return unregisteredCustom;
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
		boolean isCustomRegistered = false;
		
		if(IInputCallback.class.equals(callbackType)) {
			return hasGeneralCallback(callback);
		}
		
		if(IScreenButtonCallback.class.isAssignableFrom(callbackType)) {
			isCustomRegistered = hasScreenButtonCallback((IScreenButtonCallback) callback) || isCustomRegistered;
		}
		if(IScreenPressCallback.class.isAssignableFrom(callbackType)) {
			isCustomRegistered = hasScreenPressCallback((IScreenPressCallback) callback) || isCustomRegistered;
		}
		if(IScreenPointerCallback.class.isAssignableFrom(callbackType)) {
			isCustomRegistered = hasScreenPointerCallback((IScreenPointerCallback) callback) || isCustomRegistered;
		}
		
		return isCustomRegistered;
	}
	
	
	

	@Override
	public void registerGeneralCallback(IInputCallback callback) {
		generalInputCallbackSet.add(callback);
	}
	
	@Override
	public boolean unregisterGeneralCallback(IInputCallback callback) {
		return generalInputCallbackSet.remove(callback);
	}
	
	@Override
	public boolean hasGeneralCallback(IInputCallback callback) {
		return generalInputCallbackSet.contains(callback);
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
