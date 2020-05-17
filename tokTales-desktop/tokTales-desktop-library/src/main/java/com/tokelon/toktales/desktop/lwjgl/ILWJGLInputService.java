package com.tokelon.toktales.desktop.lwjgl;

import com.tokelon.toktales.desktop.input.IDesktopInputService;

public interface ILWJGLInputService extends IDesktopInputService {


	@Override
	public ILWJGLInputDispatch getMainInputDispatch();

}
