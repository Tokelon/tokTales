package com.tokelon.toktales.desktop.input.dispatch;

import com.tokelon.toktales.core.engine.input.IInputConsumer;

public interface IDesktopInputConsumer extends IInputConsumer, IDesktopInputRegistration {


	public IMouseButtonCallback getMasterMouseButtonCallback();
	public ICursorEnterCallback getMasterCursorEnterCallback();
	public ICursorPosCallback getMasterCursorPosCallback();
	public IKeyInputCallback getMasterKeyInputCallback();
	public ICharInputCallback getMasterCharInputCallback();


	public interface IDesktopInputConsumerFactory {

		public IDesktopInputConsumer create(IDesktopInputDispatch dispatch);
	}

}
