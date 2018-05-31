package com.tokelon.toktales.extens.def.core.tale.states;

import javax.inject.Inject;

import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler.ILocalMapControlHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlScheme;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer.ILocalMapStateRendererFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;

public class TaleGamestate extends LocalMapGamestate implements ITaleGamestate {

	public static final String SUB_TAG = "TaleGamestate";
	
	
	@Inject
	public TaleGamestate(
			ILocalMapStateRendererFactory stateRendererFactory,
			ILocalMapInputHandlerFactory inputHandlerFactory,
			ILocalMapControlScheme controlScheme,
			ILocalMapControlHandlerFactory controlHandlerFactory
	) {
		super(stateRendererFactory, inputHandlerFactory, controlScheme, controlHandlerFactory);
	}


	@Override
	protected String getTag() {
		return SUB_TAG + "_" + BASE_TAG;
	}
	
}
