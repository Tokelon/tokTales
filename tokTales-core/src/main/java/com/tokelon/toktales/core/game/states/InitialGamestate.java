package com.tokelon.toktales.core.game.states;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;

public class InitialGamestate extends BaseGamestate {
	
	public InitialGamestate(IEngineContext context) {
		super(context);
	}
	
	public InitialGamestate(IEngineContext context, IRenderOrder baseOrder) {
		super(context, baseOrder);
	}

}
