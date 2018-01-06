package com.tokelon.toktales.core.engine.setup;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.scripts.AddInitialGamestateSetupScript;
import com.tokelon.toktales.core.engine.setup.scripts.InitScriptingSetupScript;
import com.tokelon.toktales.core.engine.setup.scripts.LoadMainConfigSetupScript;

public class BaseInjectSetup extends AbstractInjectSetup {


	@Override
	protected void doRun(IEngineContext context) throws EngineException {

		new LoadMainConfigSetupScript().run(context);

		new InitScriptingSetupScript().run(context);

		new AddInitialGamestateSetupScript().run(context);
	}


}
