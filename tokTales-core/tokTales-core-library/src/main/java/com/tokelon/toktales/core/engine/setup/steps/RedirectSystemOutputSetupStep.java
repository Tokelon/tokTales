package com.tokelon.toktales.core.engine.setup.steps;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.ISetupStep;

import uk.org.lidalia.sysoutslf4j.context.LogLevel;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

public class RedirectSystemOutputSetupStep implements ISetupStep {


	@Override
	public void onBuildUp(IEngineContext engineContext) throws EngineException {
		SysOutOverSLF4J.sendSystemOutAndErrToSLF4J(LogLevel.INFO, LogLevel.ERROR);
	}

	@Override
	public void onTearDown(IEngineContext engineContext) throws EngineException {
		SysOutOverSLF4J.restoreOriginalSystemOutputs();
	}

}
