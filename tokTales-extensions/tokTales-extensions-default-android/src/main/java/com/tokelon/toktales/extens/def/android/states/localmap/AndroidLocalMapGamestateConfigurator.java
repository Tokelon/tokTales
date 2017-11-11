package com.tokelon.toktales.extens.def.android.states.localmap;

import com.tokelon.toktales.android.states.AndroidGameStateInput;
import com.tokelon.toktales.android.states.IAndroidGameStateInput;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameStateConfigurator.AbstractGamestateConfigurator;
import com.tokelon.toktales.extens.def.android.game.screen.AndroidLocalMapStateRenderer;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;

public class AndroidLocalMapGamestateConfigurator extends AbstractGamestateConfigurator<LocalMapGamestate> {

	@Override
	public void configureState(LocalMapGamestate gamestate) {
		gamestate.setStateRenderCustom(createStateRender(gamestate));
		
		IAndroidGameStateInput stateInput = createStateInput(gamestate);
		gamestate.setStateInputCustom(stateInput);
		
		AndroidLocalMapInputHandler stateInputHandler = createStateInputHandler(gamestate);
		gamestate.setStateInputHandler(stateInputHandler);
		gamestate.setStateControlScheme(createStateControlScheme(gamestate));
		gamestate.setStateControlHandlerCustom(createStateControlHandler(gamestate));
		
		
		stateInput.registerScreenButtonCallback(stateInputHandler);
		stateInput.registerScreenPressCallback(stateInputHandler);
	}

	
	@Override
	public ILocalMapStateRenderer createStateRender(LocalMapGamestate gamestate) {
		AndroidLocalMapStateRenderer stateRenderer = new AndroidLocalMapStateRenderer(gamestate);
		return stateRenderer;
	}

	@Override
	public IAndroidGameStateInput createStateInput(LocalMapGamestate gamestate) {
		return new AndroidGameStateInput();
	}
	
	@Override
	public AndroidLocalMapInputHandler createStateInputHandler(LocalMapGamestate gamestate) {
		return new AndroidLocalMapInputHandler(gamestate);
	}
	
	@Override
	public IControlScheme createStateControlScheme(LocalMapGamestate gamestate) {
		return new AndroidLocalMapControlScheme();
	}
	
	@Override
	public ILocalMapControlHandler createStateControlHandler(LocalMapGamestate gamestate) {
		return new AndroidLocalMapControlHandler(gamestate);
	}

}
