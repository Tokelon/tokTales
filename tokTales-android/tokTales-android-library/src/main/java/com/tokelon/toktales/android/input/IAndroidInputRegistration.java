package com.tokelon.toktales.android.input;

import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPointerInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPressInputEvent;
import com.tokelon.toktales.core.engine.input.ICustomInputCallback;
import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.IInputRegistration;

public interface IAndroidInputRegistration extends IInputRegistration {

	
	public interface IScreenButtonCallback extends ICustomInputCallback {
		
		public boolean handleScreenButtonInput(IScreenButtonInputEvent event);
	}
	
	public interface IScreenPressCallback extends ICustomInputCallback {
		
		public boolean handleScreenPressInput(IScreenPressInputEvent event);
	}
	
	public interface IScreenPointerCallback extends ICustomInputCallback {
		
		public boolean handleScreenPointerInput(IScreenPointerInputEvent event);
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
