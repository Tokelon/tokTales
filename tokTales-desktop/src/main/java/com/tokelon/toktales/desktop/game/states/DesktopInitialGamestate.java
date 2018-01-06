package com.tokelon.toktales.desktop.game.states;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.states.InitialGamestate;

public class DesktopInitialGamestate extends InitialGamestate {


	public DesktopInitialGamestate(IEngineContext context) {
		super(context);
	}

	@Inject
	public DesktopInitialGamestate(IEngineContext context, IRenderOrder baseOrder) {
		super(context, baseOrder);
	}

}
