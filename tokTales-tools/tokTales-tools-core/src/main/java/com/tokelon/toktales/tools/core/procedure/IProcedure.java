package com.tokelon.toktales.tools.core.procedure;

import com.tokelon.toktales.tools.core.procedure.checked.ICheckedProcedure;

public interface IProcedure extends ICheckedProcedure {


	public interface IProcedureFactory extends ICheckedProcedureFactory {
		
		@Override
		public IProcedure create();
	}
	
}
