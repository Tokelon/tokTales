package com.tokelon.toktales.desktop.engine.setup;

import com.tokelon.toktales.core.engine.setup.DefaultEngineSetup;
import com.tokelon.toktales.desktop.lwjgl.LWJGLAddErrorCallbackSetupStep;
import com.tokelon.toktales.desktop.lwjgl.LWJGLInitGLFWSetupStep;
import com.tokelon.toktales.desktop.lwjgl.LWJGLRegisterCallbacksSetupStep;

public class DesktopEngineSetup extends DefaultEngineSetup {


	public static final String SETUP_STEP_LWJGL_INIT_GLFW = "SETUP_STEP_LWJGL_INIT_GLFW";
	public static final String SETUP_STEP_LWJGL_ADD_ERROR_CALLBACK = "SETUP_STEP_LWJGL_ADD_ERROR_CALLBACK";
	public static final String SETUP_STEP_LWJGL_REGISTER_CALLBACKS = "SETUP_STEP_LWJGL_REGISTER_CALLBACKS";


	@Override
	protected void addDefaultSteps() {
		super.addDefaultSteps();

		getSteps().insertStep(SETUP_STEP_LWJGL_INIT_GLFW, new LWJGLInitGLFWSetupStep());
		getSteps().insertStep(SETUP_STEP_LWJGL_ADD_ERROR_CALLBACK, new LWJGLAddErrorCallbackSetupStep());
		getSteps().insertStep(SETUP_STEP_LWJGL_REGISTER_CALLBACKS, new LWJGLRegisterCallbacksSetupStep());
	}

}
