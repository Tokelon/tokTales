package com.tokelon.toktales.desktop.engine.setup;

import com.tokelon.toktales.core.engine.setup.DefaultEngineSetup;
import com.tokelon.toktales.desktop.lwjgl.LWJGLProgramManageSetupStep;

public class DesktopEngineSetup extends DefaultEngineSetup {


	public static final String SETUP_STEP_LWJGL_PROGRAM_MANAGE = "SETUP_STEP_LWJGL_PROGRAM_MANAGE";


	@Override
	protected void addDefaultSteps() {
		super.addDefaultSteps();

		getSteps().insertStep(SETUP_STEP_LWJGL_PROGRAM_MANAGE, new LWJGLProgramManageSetupStep());
	}

}
