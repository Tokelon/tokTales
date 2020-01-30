package com.tokelon.toktales.extensions.core.game.state.localmap;

import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.state.InjectGameState;
import com.tokelon.toktales.extensions.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extensions.core.values.GameStateExtensionsValues;

@InjectGameState
public class LocalMapConsoleInterpreter implements ILocalMapConsoleIntepreter {

	
	private ILocalMapGamestate gamestate;

	@InjectGameState
	protected void injectGamestate(ILocalMapGamestate gamestate) {
		this.gamestate = gamestate;
	}

	
	@Override
	public boolean interpret(IConsole console, String input) {
		
		IConsoleInterpreter sceneConsInter = gamestate.getActiveScene().getSceneConsoleInterpreter();
		if(sceneConsInter != null && sceneConsInter.interpret(console, input)) {
			return true;
		}
		
		
		String response = "";
		
		if(input.contains("Hello")) {
			response = "Hello!";
		}
		else if(input.equals("td") || input.equals("debug toggle")) {
			//Game.getEventHub().getGlobalBus().send("debug.toggle");	// Event bus?
			
			gamestate.getStateRenderCustom().setDebugRendering(!gamestate.getStateRenderCustom().isDebugRenderingEnabled());
		}
		else if(input.startsWith("camera size")) {
			String[] inputWords = input.split(" ");
			if(inputWords.length >= 4) {
				try {
					int xval = Integer.parseInt(inputWords[2]);
					int yval = Integer.parseInt(inputWords[3]);
					
					gamestate.getActiveScene().getSceneCamera().setSize(xval, yval);
				}
				catch(NumberFormatException nfe) {
					response = "Bad x y values";
				}
			}
			else {
				response = "Bad syntax. Use: camera size x y";
			}
		}
		else if(input.startsWith("camera zoom")) {
			String[] inputWords = input.split(" ");
			if(inputWords.length >= 3) {
				try {
					float zoom = Float.parseFloat(inputWords[2]);
					
					gamestate.getActiveScene().getSceneCamera().setZoom(zoom, false);
				}
				catch(NumberFormatException nfe) {
					response = "Bad zoom value";
				}
			}
			else {
				response = "Bad syntax. Use: camera zoom z";
			}
		}
		else if(input.equals("exit")) {
			// TODO: Implement state history and switch to the previous state
			if(gamestate.getGame().getStateControl().hasState(GameStateExtensionsValues.STATE_CONSOLE)) {
				gamestate.getGame().getStateControl().changeState(GameStateExtensionsValues.STATE_CONSOLE);
				response = "Exiting...";
			}
		}
		else {
			response = "I did not understand that.";
			return false;
		}
		
		console.print(response);
		return true;
	}

}
