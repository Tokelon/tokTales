package com.tokelon.toktales.desktop.input.dispatch;

import com.tokelon.toktales.core.engine.input.ICustomInputCallback;
import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.IInputRegistration;
import com.tokelon.toktales.desktop.input.events.ICharInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorEnterInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorPosInputEvent;
import com.tokelon.toktales.desktop.input.events.IKeyInputEvent;
import com.tokelon.toktales.desktop.input.events.IMouseButtonInputEvent;

public interface IDesktopInputRegistration extends IInputRegistration {

	
	public interface IMouseButtonCallback extends ICustomInputCallback {
		
		public boolean handleMouseButtonInput(IMouseButtonInputEvent event);
	}
	
	public interface ICursorEnterCallback extends ICustomInputCallback {
		
		public boolean handleCursorEnterInput(ICursorEnterInputEvent event);
	}
	
	public interface ICursorPosCallback extends ICustomInputCallback {

		public boolean handleCursorPosInput(ICursorPosInputEvent event);
	}
	
	public interface IKeyInputCallback extends ICustomInputCallback {
		
		public boolean handleKeyInput(IKeyInputEvent event);
	}
	
	public interface ICharInputCallback extends ICustomInputCallback {
		
		public boolean handleCharInput(ICharInputEvent event);
	}
	
	

	
	/** Registers the given callback for general events.
	 * If the callback is already registered it won't be registered again.
	 * 
	 * @param callback
	 */
	public void registerGeneralCallback(IInputCallback callback);
	
	/** Unregisters the given callback.
	 * 
	 * @param callback
	 * @return True if the callback was registered before, false if not.
	 */
	public boolean unregisterGeneralCallback(IInputCallback callback);
	
	/** Returns whether the given callback is registered or not.
	 * 
	 * @param callback
	 * @return True if the callback is registered, false if not.
	 */
	public boolean hasGeneralCallback(IInputCallback callback);
	
	
	/** Registers the given callback for mouse button events.
	 * If the callback is already registered it won't be registered again.
	 * 
	 * @param callback
	 */
	public void registerMouseButtonCallback(IMouseButtonCallback callback);
	
	/** Unregisters the given callback.
	 * 
	 * @param callback
	 * @return True if the callback was registered before, false if not.
	 */
	public boolean unregisterMouseButtonCallback(IMouseButtonCallback callback);
	
	/** Returns whether the given callback is registered or not.
	 * 
	 * @param callback
	 * @return True if the callback is registered, false if not.
	 */
	public boolean hasMouseButtonCallback(IMouseButtonCallback callback);
	

	/** Registers the given callback for cursor move enter.
	 * If the callback is already registered it won't be registered again.
	 * 
	 * @param callback
	 */
	public void registerCursorEnterCallback(ICursorEnterCallback callback);
	
	/** Unregisters the given callback.
	 * 
	 * @param callback
	 * @return True if the callback was registered before, false if not.
	 */
	public boolean unregisterCursorEnterCallback(ICursorEnterCallback callback);
	
	/** Returns whether the given callback is registered or not.
	 * 
	 * @param callback
	 * @return True if the callback is registered, false if not.
	 */
	public boolean hasCursorEnterCallback(ICursorEnterCallback callback);
	
	
	/** Registers the given callback for cursor move events.
	 * If the callback is already registered it won't be registered again.
	 * 
	 * @param callback
	 */
	public void registerCursorPosCallback(ICursorPosCallback callback);
	
	/** Unregisters the given callback.
	 * 
	 * @param callback
	 * @return True if the callback was registered before, false if not.
	 */
	public boolean unregisterCursorPosCallback(ICursorPosCallback callback);
	
	/** Returns whether the given callback is registered or not.
	 * 
	 * @param callback
	 * @return True if the callback is registered, false if not.
	 */
	public boolean hasCursorPosCallback(ICursorPosCallback callback);
	
	
	
	/** Registers the given callback for key input events.
	 * If the callback is already registered it won't be registered again.
	 * 
	 * @param callback
	 */
	public void registerKeyInputCallback(IKeyInputCallback callback);
	
	/** Unregisters the given callback.
	 * 
	 * @param callback
	 * @return True if the callback was registered before, false if not.
	 */
	public boolean unregisterKeyInputCallback(IKeyInputCallback callback);
	
	/** Returns whether the given callback is registered or not.
	 * 
	 * @param callback
	 * @return True if the callback is registered, false if not.
	 */
	public boolean hasKeyInputCallback(IKeyInputCallback callback);
	
	
	
	/** Registers the given callback for char input events.
	 * If the callback is already registered it won't be registered again.
	 * 
	 * @param callback
	 */
	public void registerCharInputCallback(ICharInputCallback callback);
	
	/** Unregisters the given callback.
	 * 
	 * @param callback
	 * @return True if the callback was registered before, false if not.
	 */
	public boolean unregisterCharInputCallback(ICharInputCallback callback);
	
	/** Returns whether the given callback is registered or not.
	 * 
	 * @param callback
	 * @return True if the callback is registered, false if not.
	 */
	public boolean hasCharInputCallback(ICharInputCallback callback);
	
}
