package com.tokelon.toktales.desktop.engine.setup;

import com.tokelon.toktales.core.engine.setup.DefaultEngineSetup;
import com.tokelon.toktales.desktop.lwjgl.LWJGLProgramManageStep;

public class DefaultDesktopEngineSetup extends DefaultEngineSetup {


	public static final String STEP_LWJGL_PROGRAM_MANAGE = "STEP_LWJGL_PROGRAM_MANAGE";


	@Override
	protected void addDefaultSteps() {
		super.addDefaultSteps();

		getSteps().insertStep(STEP_LWJGL_PROGRAM_MANAGE, new LWJGLProgramManageStep());
	}

}
