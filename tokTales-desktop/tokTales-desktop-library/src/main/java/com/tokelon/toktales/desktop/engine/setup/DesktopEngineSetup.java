package com.tokelon.toktales.desktop.engine.setup;

import com.tokelon.toktales.core.engine.setup.DefaultEngineSetup;
import com.tokelon.toktales.desktop.lwjgl.LWJGLManageCallbacksSetupStep;
import com.tokelon.toktales.desktop.lwjgl.LWJGLProgramManageSetupStep;

public class DesktopEngineSetup extends DefaultEngineSetup {


	public static final String SETUP_STEP_LWJGL_PROGRAM_MANAGE = "SETUP_STEP_LWJGL_PROGRAM_MANAGE";
	public static final String SETUP_STEP_LWJGL_MANAGE_CALLBACKS = "SETUP_STEP_GLFW_MANAGE_CALLBACKS";


	@Override
	protected void addDefaultSteps() {
		super.addDefaultSteps();

		getSteps().insertStep(SETUP_STEP_LWJGL_PROGRAM_MANAGE, new LWJGLProgramManageSetupStep());
		getSteps().insertStep(SETUP_STEP_LWJGL_MANAGE_CALLBACKS, new LWJGLManageCallbacksSetupStep());
	}

}
