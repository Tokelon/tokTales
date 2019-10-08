package com.tokelon.toktales.extens.def.core.tale;

import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.extens.def.core.tale.states.ITaleGamescene;
import com.tokelon.toktales.tools.procedure.checked.IBiFunctionCheckedProcedure;

public interface ISetMapTaleProcedure extends IBiFunctionCheckedProcedure<ITaleGamescene, IMapController, IGame, IBlockMap> {

	
	@Override
	public IMapController run(ITaleGamescene taleScene, IGame game, IBlockMap map) throws TaleException;
	
}
