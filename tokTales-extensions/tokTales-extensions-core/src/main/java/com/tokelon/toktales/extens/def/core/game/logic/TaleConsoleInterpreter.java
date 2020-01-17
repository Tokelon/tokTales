package com.tokelon.toktales.extens.def.core.game.logic;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.states.InjectGameState;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.core.storage.utils.MutablePathImpl;
import com.tokelon.toktales.extens.def.core.game.states.console.IConsoleGamestate;
import com.tokelon.toktales.extens.def.core.tale.ITaleLoader;
import com.tokelon.toktales.extens.def.core.tale.TaleException;
import com.tokelon.toktales.extens.def.core.values.GameStateExtensionsValues;

@InjectGameState
public class TaleConsoleInterpreter implements IConsoleInterpreter {
	

	private IConsoleGamestate consoleGamestate;
	private ILogger logger;
	
	private final ITaleLoader taleLoader;
	
	@Inject
	public TaleConsoleInterpreter(ITaleLoader taleLoader) {
		this.taleLoader = taleLoader;
	}

	@InjectGameState
	protected void injectGamestate(IConsoleGamestate gamestate) {
		this.consoleGamestate = gamestate;
		this.logger = gamestate.getLogging().getLogger(getClass());
	}
	
	
	@Override
	public boolean interpret(IConsole console, String input) {
		if(input.toLowerCase().contains("load tale")) {

			String[] inputSplit = input.split("\\s+");
			if(inputSplit.length > 2) {
				String talename = inputSplit[2];
				return tryLoadTale(console, talename);
			}
			else {
				console.print("ERROR: No tale name provided");
			}
		}
		
		return false;
	}
	
	
	protected boolean tryLoadTale(IConsole console, String talename) {
		// TODO: Implement loading tales with [<index>] ex. [0]
		
		console.print("Loading tale " + talename);

		
		String taleDirAppPath = new MutablePathImpl(new LocationImpl("Tales").getLocationPath()).getPathAppendedBy(talename);
		
		String sceneName = talename;
		String stateName = GameStateExtensionsValues.STATE_LOCAL_MAP;
		try {
			taleLoader.loadTaleIntoGame(taleDirAppPath, sceneName, stateName);
			consoleGamestate.getGame().getStateControl().changeState(stateName);
		}
		catch(TaleException e) {
			logger.error("Loading tale failed:", e);
			console.print("Loading tale failed");
		}
		
		return true;
	}

}
