package com.tokelon.toktales.desktop.input;

import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.IInputRegistration;

public interface IDesktopInputRegistration extends IInputRegistration {

	
	public interface IMouseButtonCallback extends IInputCallback {
	
		/** 
		 * @param vb
		 * @param action
		 * @return True if the event was handled, false if not.
		 */
		public boolean invokeMouseButton(int vb, int action);
		
		// TODO: Implement mods
		//public void invoke(int vb, int action, int mods);

		
		/* Use these ?
		public void onButtonRelease(int vb);
		public void onButtonPress(int vb);
		public void onButtonRepeat(int vb);
		*/
	}
	
	public interface ICursorMoveCallback extends IInputCallback {

		/**
		 * @param xpos
		 * @param ypos
		 * @return True if the event was handled, false if not.
		 */
		public boolean invokeCursorMove(double xpos, double ypos);
		
	}
	
	
	public interface IKeyInputCallback extends IInputCallback {
		
		/**
		 * @param vk
		 * @param action
		 * @return True if the event was handled, false if not.
		 */
		public boolean invokeKeyInput(int vk, int action);
		
		//public void invoke(int vk, int action, int scancode, int mods);
		
	}
	
	
	public interface ICharInputCallback extends IInputCallback {
		
		/**
		 * 
		 * @param codepoint
		 * @return True if the event was handled, false if not.
		 */
		public boolean invokeCharInput(int codepoint);
	}
	
	

	
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
	
	
	
	/** Registers the given callback for cursor move events.
	 * If the callback is already registered it won't be registered again.
	 * 
	 * @param callback
	 */
	public void registerCursorMoveCallback(ICursorMoveCallback callback);
	
	/** Unregisters the given callback.
	 * 
	 * @param callback
	 * @return True if the callback was registered before, false if not.
	 */
	public boolean unregisterCursorMoveCallback(ICursorMoveCallback callback);
	
	/** Returns whether the given callback is registered or not.
	 * 
	 * @param callback
	 * @return True if the callback is registered, false if not.
	 */
	public boolean hasCursorMoveCallback(ICursorMoveCallback callback);
	
	
	
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
