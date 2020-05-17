package com.tokelon.toktales.desktop.lwjgl;

import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputDispatch;
import com.tokelon.toktales.desktop.lwjgl.input.IGLFWInputConsumer;

public interface ILWJGLInputDispatch extends IDesktopInputDispatch {


	public IGLFWInputConsumer getGLFWInputConsumer();

}
