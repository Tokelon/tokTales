package com.tokelon.toktales.desktop.input;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.InputCallbackException;

public class DesktopInputRegistration implements IDesktopInputRegistration {
	// TODO: Always register for generic type?
	// TODO: Replace sets with lists to guarantee some sort of calling order?
	//only register/unregister if not custom registered?

	
	private final Set<IInputCallback> generalInputCallbackSet;
	private final Set<IMouseButtonCallback> mouseButtonCallbackSet;
	private final Set<ICursorEnterCallback> cursorEnterCallbackSet;
	private final Set<ICursorPosCallback> cursorPosCallbackSet;
	private final Set<IKeyInputCallback> keyInputCallbackSet;
	private final Set<ICharInputCallback> charInputCallbackSet;
	
	public DesktopInputRegistration() {
		generalInputCallbackSet = Collections.synchronizedSet(new HashSet<IInputCallback>());
		mouseButtonCallbackSet = Collections.synchronizedSet(new HashSet<IMouseButtonCallback>());
		cursorEnterCallbackSet = Collections.synchronizedSet(new HashSet<ICursorEnterCallback>());
		cursorPosCallbackSet = Collections.synchronizedSet(new HashSet<ICursorPosCallback>());
		keyInputCallbackSet = Collections.synchronizedSet(new HashSet<IKeyInputCallback>());
		charInputCallbackSet = Collections.synchronizedSet(new HashSet<ICharInputCallback>());
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
	 * @return The mouse button callback set.
	 */
	protected Set<IMouseButtonCallback> getMouseButtonCallbackSet() {
		return mouseButtonCallbackSet;
	}
	
	/** Remember to synchronize when iterating over the returned set.
	 * 
	 * @return The cursor move callback set.
	 */
	protected Set<ICursorEnterCallback> getCursorEnterCallbackSet() {
		return cursorEnterCallbackSet;
	}

	/** Remember to synchronize when iterating over the returned set.
	 * 
	 * @return The cursor move callback set.
	 */
	protected Set<ICursorPosCallback> getCursorPosCallbackSet() {
		return cursorPosCallbackSet;
	}
	
	/** Remember to synchronize when iterating over the returned set.
	 * 
	 * @return The key input callback set.
	 */
	protected Set<IKeyInputCallback> getKeyInputCallbackSet() {
		return keyInputCallbackSet;
	}
	
	/** Remember to synchronize when iterating over the returned set.
	 * 
	 * @return The char input callback set.
	 */
	protected Set<ICharInputCallback> getCharInputCallbackSet() {
		return charInputCallbackSet;
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
			throw new InputCallbackException("IDesktopGameStateInput does not support the callback type: " + callback.getClass());
		}
	}

	private boolean internalRegisterInputCallback(IInputCallback callback, Class<? extends IInputCallback> callbackType) {
		boolean registeredCustom = false;
		
		if(IInputCallback.class.equals(callbackType)) {
			registerGeneralCallback(callback);
			return true;
		}
		
		if(IMouseButtonCallback.class.isAssignableFrom(callbackType)) {
			registerMouseButtonCallback((IMouseButtonCallback) callback);
			registeredCustom = true;
		}
		if(ICursorEnterCallback.class.isAssignableFrom(callbackType)) {
			registerCursorEnterCallback((ICursorEnterCallback) callback);
			registeredCustom = true;
		}
		if(ICursorPosCallback.class.isAssignableFrom(callbackType)) {
			registerCursorPosCallback((ICursorPosCallback) callback);
			registeredCustom = true;
		}
		if(IKeyInputCallback.class.isAssignableFrom(callbackType)) {
			registerKeyInputCallback((IKeyInputCallback) callback);
			registeredCustom = true;
		}
		if(ICharInputCallback.class.isAssignableFrom(callbackType)) {
			registerCharInputCallback((ICharInputCallback) callback);
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
		
		if(IMouseButtonCallback.class.isAssignableFrom(callbackType)) {
			unregisteredCustom = unregisterMouseButtonCallback((IMouseButtonCallback) callback) || unregisteredCustom;
		}
		if(ICursorEnterCallback.class.isAssignableFrom(callbackType)) {
			unregisteredCustom = unregisterCursorEnterCallback((ICursorEnterCallback) callback) || unregisteredCustom;
		}
		if(ICursorPosCallback.class.isAssignableFrom(callbackType)) {
			unregisteredCustom = unregisterCursorPosCallback((ICursorPosCallback) callback) || unregisteredCustom;
		}
		if(IKeyInputCallback.class.isAssignableFrom(callbackType)) {
			unregisteredCustom = unregisterKeyInputCallback((IKeyInputCallback) callback) || unregisteredCustom;
		}
		if(ICharInputCallback.class.isAssignableFrom(callbackType)) {
			unregisteredCustom = unregisterCharInputCallback((ICharInputCallback) callback) || unregisteredCustom;
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
		
		if(IMouseButtonCallback.class.isAssignableFrom(callbackType)) {
			isCustomRegistered = hasMouseButtonCallback((IMouseButtonCallback) callback) || isCustomRegistered;
		}
		if(ICursorEnterCallback.class.isAssignableFrom(callbackType)) {
			isCustomRegistered = hasCursorEnterCallback((ICursorEnterCallback) callback) || isCustomRegistered;
		}
		if(ICursorPosCallback.class.isAssignableFrom(callbackType)) {
			isCustomRegistered = hasCursorPosCallback((ICursorPosCallback) callback) || isCustomRegistered;
		}
		if(IKeyInputCallback.class.isAssignableFrom(callbackType)) {
			isCustomRegistered = hasKeyInputCallback((IKeyInputCallback) callback) || isCustomRegistered;
		}
		if(ICharInputCallback.class.isAssignableFrom(callbackType)) {
			isCustomRegistered = hasCharInputCallback((ICharInputCallback) callback) || isCustomRegistered;
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
	public void registerMouseButtonCallback(IMouseButtonCallback callback) {
		mouseButtonCallbackSet.add(callback);
	}

	@Override
	public boolean unregisterMouseButtonCallback(IMouseButtonCallback callback) {
		return mouseButtonCallbackSet.remove(callback);
	}

	@Override
	public boolean hasMouseButtonCallback(IMouseButtonCallback callback) {
		return mouseButtonCallbackSet.contains(callback);
	}


	@Override
	public void registerCursorEnterCallback(ICursorEnterCallback callback) {
		cursorEnterCallbackSet.add(callback);
	}

	@Override
	public boolean unregisterCursorEnterCallback(ICursorEnterCallback callback) {
		return cursorEnterCallbackSet.remove(callback);
	}

	@Override
	public boolean hasCursorEnterCallback(ICursorEnterCallback callback) {
		return cursorEnterCallbackSet.contains(callback);
	}
	
	
	@Override
	public void registerCursorPosCallback(ICursorPosCallback callback) {
		cursorPosCallbackSet.add(callback);
	}

	@Override
	public boolean unregisterCursorPosCallback(ICursorPosCallback callback) {
		return cursorPosCallbackSet.remove(callback);
	}

	@Override
	public boolean hasCursorPosCallback(ICursorPosCallback callback) {
		return cursorPosCallbackSet.contains(callback);
	}
	

	@Override
	public void registerKeyInputCallback(IKeyInputCallback callback) {
		keyInputCallbackSet.add(callback);
	}

	@Override
	public boolean unregisterKeyInputCallback(IKeyInputCallback callback) {
		return keyInputCallbackSet.remove(callback);
	}

	@Override
	public boolean hasKeyInputCallback(IKeyInputCallback callback) {
		return keyInputCallbackSet.contains(callback);
	}

	
	@Override
	public void registerCharInputCallback(ICharInputCallback callback) {
		charInputCallbackSet.add(callback);
	}

	@Override
	public boolean unregisterCharInputCallback(ICharInputCallback callback) {
		return charInputCallbackSet.remove(callback);
	}

	@Override
	public boolean hasCharInputCallback(ICharInputCallback callback) {
		return charInputCallbackSet.contains(callback);
	}

}
