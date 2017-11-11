package com.tokelon.toktales.desktop.input;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.InputCallbackException;

public class DesktopInputRegistration implements IDesktopInputRegistration {

	private final Set<IMouseButtonCallback> mouseButtonCallbackSet;
	private final Set<ICursorMoveCallback> cursorMoveCallbackSet;
	private final Set<IKeyInputCallback> keyInputCallbackSet;
	private final Set<ICharInputCallback> charInputCallbackSet;
	
	public DesktopInputRegistration() {
		mouseButtonCallbackSet = Collections.synchronizedSet(new HashSet<IMouseButtonCallback>());
		cursorMoveCallbackSet = Collections.synchronizedSet(new HashSet<ICursorMoveCallback>());
		keyInputCallbackSet = Collections.synchronizedSet(new HashSet<IKeyInputCallback>());
		charInputCallbackSet = Collections.synchronizedSet(new HashSet<ICharInputCallback>());
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
	protected Set<ICursorMoveCallback> getCursorMoveCallbackSet() {
		return cursorMoveCallbackSet;
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
		boolean registered = false;
		
		if(IMouseButtonCallback.class.isAssignableFrom(callbackType)) {
			registerMouseButtonCallback((IMouseButtonCallback) callback);
			registered = true;
		}
		if(ICursorMoveCallback.class.isAssignableFrom(callbackType)) {
			registerCursorMoveCallback((ICursorMoveCallback) callback);
			registered = true;
		}
		if(IKeyInputCallback.class.isAssignableFrom(callbackType)) {
			registerKeyInputCallback((IKeyInputCallback) callback);
			registered = true;
		}
		if(ICharInputCallback.class.isAssignableFrom(callbackType)) {
			registerCharInputCallback((ICharInputCallback) callback);
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
		
		if(IMouseButtonCallback.class.isAssignableFrom(callbackType)) {
			unregistered = unregisterMouseButtonCallback((IMouseButtonCallback) callback) || unregistered;
		}
		if(ICursorMoveCallback.class.isAssignableFrom(callbackType)) {
			unregistered = unregisterCursorMoveCallback((ICursorMoveCallback) callback) || unregistered;
		}
		if(IKeyInputCallback.class.isAssignableFrom(callbackType)) {
			unregistered = unregisterKeyInputCallback((IKeyInputCallback) callback) || unregistered;
		}
		if(ICharInputCallback.class.isAssignableFrom(callbackType)) {
			unregistered = unregisterCharInputCallback((ICharInputCallback) callback) || unregistered;
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
		
		if(IMouseButtonCallback.class.isAssignableFrom(callbackType)) {
			isRegistered = hasMouseButtonCallback((IMouseButtonCallback) callback) || isRegistered;
		}
		if(ICursorMoveCallback.class.isAssignableFrom(callbackType)) {
			isRegistered = hasCursorMoveCallback((ICursorMoveCallback) callback) || isRegistered;
		}
		if(IKeyInputCallback.class.isAssignableFrom(callbackType)) {
			isRegistered = hasKeyInputCallback((IKeyInputCallback) callback) || isRegistered;
		}
		if(ICharInputCallback.class.isAssignableFrom(callbackType)) {
			isRegistered = hasCharInputCallback((ICharInputCallback) callback) || isRegistered;
		}
		
		return isRegistered;
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
	public void registerCursorMoveCallback(ICursorMoveCallback callback) {
		cursorMoveCallbackSet.add(callback);
	}

	@Override
	public boolean unregisterCursorMoveCallback(ICursorMoveCallback callback) {
		return cursorMoveCallbackSet.remove(callback);
	}

	@Override
	public boolean hasCursorMoveCallback(ICursorMoveCallback callback) {
		return cursorMoveCallbackSet.contains(callback);
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
