package com.tokelon.toktales.desktop.input.dispatch;

import com.tokelon.toktales.core.engine.input.IInputConsumer;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.ICursorEnterCallback;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.ICursorPosCallback;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.IMouseButtonCallback;

public interface IDesktopInputConsumer extends IInputConsumer, IDesktopInputRegistration,
	IMouseButtonCallback, ICursorEnterCallback, ICursorPosCallback, IKeyInputCallback, ICharInputCallback {

	
	
	public interface IDesktopInputConsumerFactory {
		
		public IDesktopInputConsumer create(IDesktopInputDispatch dispatch);
	}
	
}
