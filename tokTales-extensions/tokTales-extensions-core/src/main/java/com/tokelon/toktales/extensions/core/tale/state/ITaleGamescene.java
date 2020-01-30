package com.tokelon.toktales.extensions.core.tale.state;

import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapGamescene;
import com.tokelon.toktales.tools.core.procedure.checked.ISupplierCheckedProcedure;

public interface ITaleGamescene extends ILocalMapGamescene {


	public void runSetMap(ISupplierCheckedProcedure<ITaleGamescene, IMapController> procedure) throws Exception;
	
}
