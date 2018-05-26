package com.tokelon.toktales.extens.def.desktop.game.states;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.states.InjectGameState;
import com.tokelon.toktales.core.logic.process.IPauseableProcess.EmptyPauseableProcess;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.core.storage.utils.MutablePathImpl;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestateInterpreter;
import com.tokelon.toktales.extens.def.core.game.states.IConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.TokelonGameStates;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapGamestate;
import com.tokelon.toktales.extens.def.core.tale.TaleProcess;

@InjectGameState
public class DesktopConsoleGamestateInterpreter extends ConsoleGamestateInterpreter implements IConsoleInterpreter {
	// TODO: Refactor structure with super interpreter into wrapper?
	
	
	private final Provider<ILocalMapGamestate> localMapGamestateProvider;
	private IConsoleGamestate consoleGamestate;
	
	@Inject
	public DesktopConsoleGamestateInterpreter(Provider<ILocalMapGamestate> localMapGamestateProvider) {
		this.localMapGamestateProvider = localMapGamestateProvider;
	}

	@InjectGameState
	@Override
	protected void injectGamestate(IConsoleGamestate gamestate) {
		this.consoleGamestate = gamestate;
	}
	
	
	@Override
	public boolean interpret(IConsole console, String input) {
		if(input.toLowerCase().contains("load tale")) {

			String[] inputSplit = input.split("\\s+");
			if(inputSplit.length > 2) {
				String talename = inputSplit[2];
				
				if(talename.equals("1")) {
					talename = "#015GK93";
				}
				

				
				ILocalMapGamestate state = localMapGamestateProvider.get();
				consoleGamestate.getGame().getStateControl().addState(TokelonGameStates.STATE_LOCAL_MAP, state);

				EmptyPauseableProcess emptyProcess = new EmptyPauseableProcess();
				TaleProcess taleProcess = new TaleProcess(emptyProcess, consoleGamestate.getEngineContext(), TokelonGameStates.STATE_LOCAL_MAP);
				
				String taleDirAppPath = new MutablePathImpl(new LocationImpl("Tales").getLocationPath()).getPathAppendedBy(talename);
				taleProcess.setObjTaleAppPath(taleDirAppPath);
				
				
				taleProcess.internalAfterStartProcess();
				taleProcess.internalClearObjects();

				
				console.print("Loading tale " + talename);
				return true;
			}
			
			console.print("ERROR: No tale name provided");
			return false; // In this case maybe the super interpreter should get a chance
		}
		
		return super.interpret(console, input);
	}

}
