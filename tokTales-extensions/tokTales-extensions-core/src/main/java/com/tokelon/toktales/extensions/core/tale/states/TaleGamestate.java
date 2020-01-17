package com.tokelon.toktales.extensions.core.tale.states;

import javax.inject.Inject;

import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapConsoleIntepreter;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapControlScheme;
import com.tokelon.toktales.extensions.core.game.states.localmap.LocalMapGamestate;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapControlHandler.ILocalMapControlHandlerFactory;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapStateRenderer.ILocalMapStateRendererFactory;

public class TaleGamestate extends LocalMapGamestate implements ITaleGamestate {


	@Inject
	public TaleGamestate(
			ILocalMapStateRendererFactory stateRendererFactory,
			ILocalMapInputHandlerFactory inputHandlerFactory,
			ILocalMapControlScheme controlScheme,
			ILocalMapControlHandlerFactory controlHandlerFactory,
			ILocalMapConsoleIntepreter consoleInterpreter
	) {
		super(stateRendererFactory, inputHandlerFactory, controlScheme, controlHandlerFactory, consoleInterpreter);
	}
	
}
