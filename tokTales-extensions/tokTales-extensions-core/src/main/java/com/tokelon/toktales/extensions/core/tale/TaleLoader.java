package com.tokelon.toktales.extensions.core.tale;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.extensions.core.tale.procedure.ILoadTaleProcedure;
import com.tokelon.toktales.extensions.core.tale.state.ITaleGamescene;
import com.tokelon.toktales.extensions.core.tale.state.ITaleGamestate;

public class TaleLoader implements ITaleLoader {


	private final ILogger logger;
	private final IGame game;
	private final ILoadTaleProcedure loadTaleProcedure;
	private final Provider<ITaleGamestate> taleGamestateProvider;
	
	@Inject
	public TaleLoader(ILogging logging, IGame game, ILoadTaleProcedure loadTaleProcedure, Provider<ITaleGamestate> taleGamestateProvider) {
		this.logger = logging.getLogger(getClass());
		this.game = game;
		this.loadTaleProcedure = loadTaleProcedure;
		this.taleGamestateProvider = taleGamestateProvider;
	}
	
	
	@Override
	public ITaleGamescene loadTale(String taleAppPath) throws TaleException {
		return loadTaleProcedure.run(game, taleAppPath);
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
			
			game.getStateControl().addState(gamestateName, gamestate);
		}
		else {
			throw new TaleException("Loading tale failed | Scene and state are incompatible");
		}
		
		logger.info("Loading tale into engine succeeded");
	}

}
