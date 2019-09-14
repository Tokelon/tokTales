package com.tokelon.toktales.extens.def.core.tale;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.extens.def.core.tale.states.ITaleGamescene;
import com.tokelon.toktales.extens.def.core.tale.states.ITaleGamestate;

public class TaleLoader implements ITaleLoader {


	private final IEngineContext engineContext;
	private final Provider<ITaleGamestate> taleGamestateProvider;
	private final ILogger logger;
	
	@Inject
	public TaleLoader(IEngineContext engineContext, Provider<ITaleGamestate> taleGamestateProvider) {
		this.engineContext = engineContext;
		this.taleGamestateProvider = taleGamestateProvider;
		this.logger = engineContext.getLogging().getLogger(getClass());
	}
	
	
	@Override
	public ITaleGamescene loadTale(String taleAppPath) throws TaleException {
		TaleProcess taleProcess = new TaleProcess(engineContext, taleAppPath);
		taleProcess.run();
		ITaleGamescene result = taleProcess.getResult();
		
		return result;
	}

	@Override
	public void loadTaleIntoGamestate(String taleAppPath, String gamesceneName, IGameState gamestate) throws TaleException {
		ITaleGamescene taleScene = loadTale(taleAppPath);
		if(taleScene == null) {
			throw new TaleException("Loading tale returned no result");
		}
		
		if(!gamestate.assignScene(gamesceneName, taleScene)) {
			throw new TaleException("Loading tale failed | Scene and state are incompatible");
		}
		
		logger.info("Loading tale into gamestate succeeded");
	}

	@Override
	public void loadTaleIntoGame(String taleAppPath, String gamesceneName, String gamestateName) throws TaleException {
		ITaleGamescene taleScene = loadTale(taleAppPath);
		if(taleScene == null) {
			throw new TaleException("Loading tale returned no result");
		}
		
		ITaleGamestate gamestate = taleGamestateProvider.get();
		
		if(gamestate.assignScene(gamesceneName, taleScene)) {
			gamestate.changeScene(gamesceneName);
			
			engineContext.getGame().getStateControl().addState(gamestateName, gamestate);
		}
		else {
			throw new TaleException("Loading tale failed | Scene and state are incompatible");
		}
		
		logger.info("Loading tale into engine succeeded");
	}

}
