package com.tokelon.toktales.extens.def.desktop.game.states.localmap;

import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameStateConfigurator;
import com.tokelon.toktales.desktop.game.states.DesktopGameStateInput;
import com.tokelon.toktales.desktop.game.states.IDesktopGameStateInput;
import com.tokelon.toktales.extens.def.core.game.screen.LocalMapStateRenderer;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;

public class DesktopLocalMapGamestateConfigurator implements IGameStateConfigurator<LocalMapGamestate> {

	
	@Override
	public void configureState(LocalMapGamestate gamestate) {
		gamestate.setStateRenderCustom(createStateRender(gamestate));
		
		IDesktopGameStateInput stateInput = createStateInput(gamestate);
		gamestate.setStateInputCustom(stateInput);

		DesktopLocalMapInputHandler stateInputHandler = createStateInputHandler(gamestate);
		gamestate.setStateInputHandler(stateInputHandler);
		gamestate.setStateControlScheme(createStateControlScheme(gamestate));
		gamestate.setStateControlHandlerCustom(createStateControlHandler(gamestate));
		

		stateInput.registerKeyInputCallback(stateInputHandler);
		stateInput.registerCharInputCallback(stateInputHandler);
		
		
		/* Does not work because the camera controller has not be created yet
		ICamera camera = gamestate.getActiveScene().getCameraController().getCamera();
		//camera.setSize(300, 900);
		camera.setSize(640, 360);
		*/
	}

	
	@Override
	public ILocalMapStateRenderer createStateRender(LocalMapGamestate gamestate) {
		return new LocalMapStateRenderer(gamestate);
	}

	@Override
	public IDesktopGameStateInput createStateInput(LocalMapGamestate gamestate) {
		return new DesktopGameStateInput();
	}

	@Override
	public DesktopLocalMapInputHandler createStateInputHandler(LocalMapGamestate gamestate) {
		return new DesktopLocalMapInputHandler(gamestate);
	}
	
	@Override
	public IControlScheme createStateControlScheme(LocalMapGamestate gamestate) {
		return new DesktopLocalMapControlScheme();
	}
	
	@Override
	public ILocalMapControlHandler createStateControlHandler(LocalMapGamestate gamestate) {
		return new DesktopLocalMapControlHandler(gamestate);
	}

}
