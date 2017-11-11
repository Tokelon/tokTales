package com.tokelon.toktales.android.input;

import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.IInputRegistration;

public interface IAndroidInputRegistration extends IInputRegistration {

	
	public interface IScreenButtonCallback extends IInputCallback {
		
		/**
		 * @param vb
		 * @param action
		 * @return True if the event was handled, false if not.
		 */
		public boolean invokeScreenButton(int vb, int action);
	}
	
	
	public interface IScreenPressCallback extends IInputCallback {
		
		/**
		 * @param xpos
		 * @param ypos
		 * @return True if the event was handled, false if not.
		 */
		public boolean invokeScreenPress(double xpos, double ypos);
	}
	
	
	public interface IScreenPointerCallback extends IInputCallback {
		
		/**
		 * 
		 * @param pointerId
		 * @param action
		 * @param xpos
		 * @param ypos
		 * @return True if the event was handled, false if not.
		 */
		public boolean invokeScreenPointer(int pointerId, int action, double xpos, double ypos);
	}

	
	/*
	public interface ICharInputCallback extends IInputCallback {
		// TODO: Implement
		// Any activities that want to support this (send events to these listener), would have to call the actual trigger
		
		public boolean invokeCharInput(int codepoint);
	}
	*/
	
	/*
	public interface ITouchInputCallback {
		// TODO: Implement a full touch event interface
	}
	
	public interface IKeyInputCallback {
		// TODO: Implement for actual hardware keys
	}
	*/
	
	
	

	/** Registers the given callback for screen button events.
	 * If the callback is already registered it won't be registered again.
	 * 
	 * @param callback
	 */
	public void registerScreenButtonCallback(IScreenButtonCallback callback);
	
	/** Unregisters the given callback.
	 * 
	 * @param callback
	 * @return True if the callback was registered before, false if not.
	 */
	public boolean unregisterScreenButtonCallback(IScreenButtonCallback callback);
	
	/** Returns whether the given callback is registered or not.
	 * 
	 * @param callback
	 * @return True if the callback is registered, false if not.
	 */
	public boolean hasScreenButtonCallback(IScreenButtonCallback callback);
	
	
	
	/** Registers the given callback for screen press events.
	 * If the callback is already registered it won't be registered again.
	 * 
	 * @param callback
	 */
	public void registerScreenPressCallback(IScreenPressCallback callback);
	
	/** Unregisters the given callback.
	 * 
	 * @param callback
	 * @return True if the callback was registered before, false if not.
	 */
	public boolean unregisterScreenPressCallback(IScreenPressCallback callback);
	
	/** Returns whether the given callback is registered or not.
	 * 
	 * @param callback
	 * @return True if the callback is registered, false if not.
	 */
	public boolean hasScreenPressCallback(IScreenPressCallback callback);

	
	
	/** Registers the given callback for screen pointer events.
	 * If the callback is already registered it won't be registered again.
	 * 
	 * @param callback
	 */
	public void registerScreenPointerCallback(IScreenPointerCallback callback);
	
	/** Unregisters the given callback.
	 * 
	 * @param callback
	 * @return True if the callback was registered before, false if not.
	 */
	public boolean unregisterScreenPointerCallback(IScreenPointerCallback callback);
	
	/** Returns whether the given callback is registered or not.
	 * 
	 * @param callback
	 * @return True if the callback is registered, false if not.
	 */
	public boolean hasScreenPointerCallback(IScreenPointerCallback callback);
	
}
