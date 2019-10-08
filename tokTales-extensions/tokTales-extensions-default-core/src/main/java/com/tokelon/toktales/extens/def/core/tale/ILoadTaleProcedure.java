package com.tokelon.toktales.extens.def.core.tale;

import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.extens.def.core.tale.states.ITaleGamescene;
import com.tokelon.toktales.tools.procedure.checked.IFunctionCheckedProcedure;

public interface ILoadTaleProcedure extends IFunctionCheckedProcedure<IGame, ITaleGamescene, String> {


	@Override
	public ITaleGamescene run(IGame owner, String parameter) throws TaleException;
	
}
