package com.tokelon.toktales.extens.def.core.tale.states;

import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapGamescene;
import com.tokelon.toktales.tools.core.procedure.checked.ISupplierCheckedProcedure;

public interface ITaleGamescene extends ILocalMapGamescene {


	public void runSetMap(ISupplierCheckedProcedure<ITaleGamescene, IMapController> procedure) throws Exception;
	
}
