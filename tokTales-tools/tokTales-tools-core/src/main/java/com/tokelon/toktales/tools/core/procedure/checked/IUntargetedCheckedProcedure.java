package com.tokelon.toktales.tools.core.procedure.checked;

public interface IUntargetedCheckedProcedure extends ICheckedProcedure {


	public interface IUntargetedCheckedProcedureFactory extends ICheckedProcedureFactory {
		
		@Override
		public IUntargetedCheckedProcedure create();
	}
	
}
