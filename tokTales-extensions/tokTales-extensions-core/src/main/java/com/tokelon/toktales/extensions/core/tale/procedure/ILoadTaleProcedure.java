package com.tokelon.toktales.extensions.core.tale.procedure;

import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.extensions.core.tale.TaleException;
import com.tokelon.toktales.extensions.core.tale.states.ITaleGamescene;
import com.tokelon.toktales.tools.core.procedure.checked.IFunctionCheckedProcedure;

public interface ILoadTaleProcedure extends IFunctionCheckedProcedure<IGame, ITaleGamescene, String> {


	@Override
	public ITaleGamescene run(IGame game, String taleAppPath) throws TaleException;
	
}
