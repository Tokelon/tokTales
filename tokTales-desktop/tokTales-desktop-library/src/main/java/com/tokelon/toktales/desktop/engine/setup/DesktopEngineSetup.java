package com.tokelon.toktales.desktop.engine.setup;

import com.tokelon.toktales.core.engine.setup.DefaultEngineSetup;
import com.tokelon.toktales.core.engine.setup.EmptySetupStep;
import com.tokelon.toktales.desktop.lwjgl.setup.LWJGLAddErrorCallbackSetupStep;
import com.tokelon.toktales.desktop.lwjgl.setup.LWJGLInitGLFWSetupStep;
import com.tokelon.toktales.desktop.lwjgl.setup.LWJGLRegisterCallbacksSetupStep;
import com.tokelon.toktales.desktop.lwjgl.setup.LWJGLSetDebugStreamSetupStep;

public class DesktopEngineSetup extends DefaultEngineSetup {


	public static final String SETUP_STEP_LWJGL_FIRST = "SETUP_STEP_LWJGL_FIRST";
	public static final String SETUP_STEP_LWJGL_SET_DEBUG_STREAM = "SETUP_STEP_LWJGL_SET_DEBUG_STREAM";
	public static final String SETUP_STEP_LWJGL_INIT_GLFW = "SETUP_STEP_LWJGL_INIT_GLFW";
	public static final String SETUP_STEP_LWJGL_ADD_ERROR_CALLBACK = "SETUP_STEP_LWJGL_ADD_ERROR_CALLBACK";
	public static final String SETUP_STEP_LWJGL_REGISTER_CALLBACKS = "SETUP_STEP_LWJGL_REGISTER_CALLBACKS";
	public static final String SETUP_STEP_LWJGL_LAST = "SETUP_STEP_LWJGL_LAST";


	@Override
	protected void addDefaultSteps() {
		super.addDefaultSteps();

		getSteps().insertStep(SETUP_STEP_LWJGL_FIRST, new EmptySetupStep());
		getSteps().insertStep(SETUP_STEP_LWJGL_SET_DEBUG_STREAM, new LWJGLSetDebugStreamSetupStep());
		getSteps().insertStep(SETUP_STEP_LWJGL_INIT_GLFW, new LWJGLInitGLFWSetupStep());
		getSteps().insertStep(SETUP_STEP_LWJGL_ADD_ERROR_CALLBACK, new LWJGLAddErrorCallbackSetupStep());
		getSteps().insertStep(SETUP_STEP_LWJGL_REGISTER_CALLBACKS, new LWJGLRegisterCallbacksSetupStep());
		getSteps().insertStep(SETUP_STEP_LWJGL_LAST, new EmptySetupStep());
	}

}
