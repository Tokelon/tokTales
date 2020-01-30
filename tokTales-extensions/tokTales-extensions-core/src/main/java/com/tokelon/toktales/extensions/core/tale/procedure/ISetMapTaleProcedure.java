package com.tokelon.toktales.extensions.core.tale.procedure;

import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.extensions.core.tale.TaleException;
import com.tokelon.toktales.extensions.core.tale.state.ITaleGamescene;
import com.tokelon.toktales.tools.core.procedure.checked.IBiFunctionCheckedProcedure;

public interface ISetMapTaleProcedure extends IBiFunctionCheckedProcedure<ITaleGamescene, IMapController, IGame, IBlockMap> {

	
	@Override
	public IMapController run(ITaleGamescene taleScene, IGame game, IBlockMap map) throws TaleException;
	
}
