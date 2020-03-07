package com.tokelon.toktales.core.engine.setup;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.steps.AddInitialGamestateStep;
import com.tokelon.toktales.core.engine.setup.steps.InitScriptingStep;
import com.tokelon.toktales.core.engine.setup.steps.LoadMainConfigStep;

public class BaseInjectSetup extends AbstractInjectSetup {


	@Override
	protected void buildUp(IEngineContext context) throws EngineException {

		new LoadMainConfigStep().onBuildUp(context);

		new InitScriptingStep().onBuildUp(context);

		new AddInitialGamestateStep().onBuildUp(context);
	}


}
