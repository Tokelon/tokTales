package com.tokelon.toktales.core.engine.setup.manual;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.scripts.InitScriptingSetupScript;
import com.tokelon.toktales.core.engine.setup.scripts.LoadMainConfigSetupScript;
import com.tokelon.toktales.core.game.IGameAdapter;

public abstract class BaseSetup extends AbstractSetup {

	
	public BaseSetup(IGameAdapter gameAdapter) {
		super(gameAdapter);
	}
	
	
	@Override
	protected void doRun(IEngineContext context) throws EngineException {
		
		new LoadMainConfigSetupScript().run(context);
		
		new InitScriptingSetupScript().run(context);
	}
	

}
