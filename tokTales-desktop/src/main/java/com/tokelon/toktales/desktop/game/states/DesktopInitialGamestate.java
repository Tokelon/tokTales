package com.tokelon.toktales.desktop.game.states;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.states.InitialGamestate;

public class DesktopInitialGamestate extends InitialGamestate {

	public DesktopInitialGamestate(IEngineContext context) {
		super(context);
	}
	
	public DesktopInitialGamestate(IEngineContext context, IRenderOrder baseOrder) {
		super(context, baseOrder);
	}
	
}
