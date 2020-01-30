package com.tokelon.toktales.extensions.core.tale.state;

import javax.inject.Inject;

import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapConsoleIntepreter;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapControlScheme;
import com.tokelon.toktales.extensions.core.game.state.localmap.LocalMapGamestate;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapControlHandler.ILocalMapControlHandlerFactory;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapStateRenderer.ILocalMapStateRendererFactory;

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
