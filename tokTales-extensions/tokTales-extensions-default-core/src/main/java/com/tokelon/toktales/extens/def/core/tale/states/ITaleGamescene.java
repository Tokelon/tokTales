package com.tokelon.toktales.extens.def.core.tale.states;

import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapGamescene;

public interface ITaleGamescene extends ILocalMapGamescene {
	
	
	// TODO: Refactor if possible
	public void setMap(IMapController mapController);
	
}