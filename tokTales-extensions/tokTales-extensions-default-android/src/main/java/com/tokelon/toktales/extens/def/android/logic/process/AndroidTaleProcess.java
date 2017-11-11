package com.tokelon.toktales.extens.def.android.logic.process;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.logic.process.GameProcess;
import com.tokelon.toktales.core.logic.process.TaleProcess;
import com.tokelon.toktales.extens.def.android.states.localmap.AndroidLocalMapGamestateConfigurator;
import com.tokelon.toktales.extens.def.core.game.states.TokelonGameStates;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;

public class AndroidTaleProcess extends TaleProcess {
	
	
	public AndroidTaleProcess(IEngineContext context, GameProcess parentProcess) {
		super(parentProcess, context, TokelonGameStates.STATE_LOCAL_MAP);
	}

	
	
	@Override
	protected void internalAfterStartProcess() {
		
		LocalMapGamestate state = new LocalMapGamestate(TokTales.getContext());
		AndroidLocalMapGamestateConfigurator configurator = new AndroidLocalMapGamestateConfigurator();
		configurator.configureState(state);
		
		getGame().getStateControl().addState(TokelonGameStates.STATE_LOCAL_MAP, state);
		
		
		// Call super process AFTER setting up the gamestate
		super.internalAfterStartProcess();
		
	}
	
	
}
