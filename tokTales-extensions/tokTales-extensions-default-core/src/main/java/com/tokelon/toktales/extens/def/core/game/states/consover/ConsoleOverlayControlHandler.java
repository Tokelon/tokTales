package com.tokelon.toktales.extens.def.core.game.states.consover;

import com.tokelon.toktales.core.game.controller.IConsoleController;

public class ConsoleOverlayControlHandler implements IConsoleOverlayControlHandler {

	private final IConsoleOverlayGamestate gamestate;
	
	public ConsoleOverlayControlHandler(IConsoleOverlayGamestate gamestate) {
		this.gamestate = gamestate;
	}
	
	
	protected IConsoleController getConsoleController() {
		return gamestate.getConsoleController();
	}
	
	
	@Override
	public boolean handleAction(String target, String action, Object... params) {
		return false;
	}

	
	@Override
	public boolean isConsoleOpen() {
		return gamestate.getConsoleController().isConsoleOpen();
	}
	
	
	@Override
	public boolean handleConsoleEnter() {
		if(gamestate.getConsoleController().isConsoleOpen()) {
			gamestate.getConsoleController().enter();
			return true;
		}
		
		return false;
	}

	@Override
	public boolean handleConsoleDelete() {
		if(gamestate.getConsoleController().isConsoleOpen()) {
			gamestate.getConsoleController().backspace();
			return true;
		}
		
		return false;
	}

	@Override
	public boolean handleConsoleClear() {
		if(gamestate.getConsoleController().isConsoleOpen()) {
			gamestate.getConsoleController().clear();
			return true;
		}
		
		return false;
	}

	@Override
	public boolean handleConsoleToggle() {
		gamestate.getConsoleController().toggleConsole();
		return true;
	}
	
	@Override
	public boolean handleConsoleInput(int codepoint) {
		if(gamestate.getConsoleController().isConsoleOpen()) {
			gamestate.getConsoleController().inputCodepoint(codepoint);
			return true;
		}

		return false;
	}

	
}
