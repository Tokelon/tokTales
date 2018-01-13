package com.tokelon.toktales.extens.def.desktop.game.logic.process;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.logic.process.IPauseableProcess;
import com.tokelon.toktales.core.logic.process.TaleProcess;
import com.tokelon.toktales.extens.def.core.game.states.TokelonGameStates;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;
import com.tokelon.toktales.extens.def.desktop.game.states.localmap.DesktopLocalMapGamestateConfigurator;

public class DesktopTaleProcess extends TaleProcess {

	public DesktopTaleProcess(IEngineContext context, IPauseableProcess parentProcess) {
		super(parentProcess, context, TokelonGameStates.STATE_LOCAL_MAP);
	}

	@Override
	protected void internalAfterStartProcess() {

		LocalMapGamestate state = new LocalMapGamestate(TokTales.getContext());
		DesktopLocalMapGamestateConfigurator configurator = new DesktopLocalMapGamestateConfigurator();
		configurator.configureState(state);
		
		getGame().getStateControl().addState(TokelonGameStates.STATE_LOCAL_MAP, state);
		
		
		// Call super process AFTER setting up the gamestate
		super.internalAfterStartProcess();
	}
	
}
