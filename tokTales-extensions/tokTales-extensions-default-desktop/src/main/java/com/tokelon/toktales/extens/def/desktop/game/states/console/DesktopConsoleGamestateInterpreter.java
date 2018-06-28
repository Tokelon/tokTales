package com.tokelon.toktales.extens.def.desktop.game.states.console;

import com.google.inject.Inject;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.states.InjectGameState;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.core.storage.utils.MutablePathImpl;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.core.game.states.console.ConsoleGamestateInterpreter;
import com.tokelon.toktales.extens.def.core.game.states.console.IConsoleGamestate;
import com.tokelon.toktales.extens.def.core.tale.ITaleLoader;
import com.tokelon.toktales.extens.def.core.tale.TaleException;
import com.tokelon.toktales.extens.def.core.values.GameStateExtensionsValues;

@InjectGameState
public class DesktopConsoleGamestateInterpreter extends ConsoleGamestateInterpreter implements IConsoleInterpreter {
	// TODO: Refactor structure with super interpreter into wrapper?
	
	public static final String TAG = "DesktopConsoleGamestateInterpreter";
	
	
	private IConsoleGamestate consoleGamestate;
	
	private final ITaleLoader taleLoader;
	
	@Inject
	public DesktopConsoleGamestateInterpreter(ITaleLoader taleLoader) {
		this.taleLoader = taleLoader;
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
				
				// TODO: For debugging - remove
				if(talename.equals("1")) { // TODO: Implement loading tales with [<index>] ex. [0]
					talename = "#015GK93";
				}
				
				
				console.print("Loading tale " + talename);

				
				String taleDirAppPath = new MutablePathImpl(new LocationImpl("Tales").getLocationPath()).getPathAppendedBy(talename);
				
				String sceneName = talename;
				String stateName = GameStateExtensionsValues.STATE_LOCAL_MAP;
				try {
					taleLoader.loadTaleIntoGame(taleDirAppPath, sceneName, stateName);
					consoleGamestate.getGame().getStateControl().changeState(stateName);
				}
				catch(TaleException e) {
					consoleGamestate.getLog().e(TAG, "Loading tale failed: " + e);
				}
				
				return true;
			}
			
			console.print("ERROR: No tale name provided");
			return false; // In this case maybe the super interpreter should get a chance
		}
		
		return super.interpret(console, input);
	}

}
