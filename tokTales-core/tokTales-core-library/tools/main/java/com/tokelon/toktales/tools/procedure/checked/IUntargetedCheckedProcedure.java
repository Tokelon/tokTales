package com.tokelon.toktales.tools.procedure.checked;

public interface IUntargetedCheckedProcedure extends ICheckedProcedure {


	public interface IUntargetedCheckedProcedureFactory extends ICheckedProcedureFactory {
		
		@Override
		public IUntargetedCheckedProcedure create();
	}
	
}
