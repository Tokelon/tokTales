package com.tokelon.toktales.extens.def.desktop.desktop.game.states;

import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.logic.process.GameProcess;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.core.storage.utils.MutablePathImpl;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.desktop.desktop.game.logic.process.DesktopTaleProcess;

public class DesktopConsoleGamestateInterpreter implements IConsoleInterpreter {

	private final IGameState gamestate;
	
	public DesktopConsoleGamestateInterpreter(IGameState gamestate) {
		this.gamestate = gamestate;
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
				

				GameProcess gameProcess = new GameProcess(TokTales.getContext());
				
				DesktopTaleProcess taleProcess = new DesktopTaleProcess(TokTales.getContext(), gameProcess);
				
				String taleDirAppPath = new MutablePathImpl(new LocationImpl("Tales").getLocationPath()).getPathAppendedBy(talename);

				taleProcess.setObjTaleAppPath(taleDirAppPath);
				

				
				gamestate.getGame().getGameControl().pauseGame();
				gamestate.getGame().getGameControl().stopGame();
				
				taleProcess.startProcess();
				taleProcess.unpause();
				
				
				console.print("Loading tale " + talename);
				return true;
			}
			
			console.print("ERROR: No tale name provided");
			return false;
		}
		else {
			return false;	
		}
	}

}
