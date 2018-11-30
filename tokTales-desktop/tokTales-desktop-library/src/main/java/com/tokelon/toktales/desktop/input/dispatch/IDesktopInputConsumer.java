package com.tokelon.toktales.desktop.input.dispatch;

import com.tokelon.toktales.core.engine.input.IInputConsumer;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICursorEnterCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICursorPosCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IMouseButtonCallback;

public interface IDesktopInputConsumer extends IInputConsumer, IDesktopInputRegistration,
	IMouseButtonCallback, ICursorEnterCallback, ICursorPosCallback, IKeyInputCallback, ICharInputCallback {

	
	
	public interface IDesktopInputConsumerFactory {
		
		public IDesktopInputConsumer create(IDesktopInputDispatch dispatch);
	}
	
}
