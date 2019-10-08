package com.tokelon.toktales.tools.procedure.checked;

public interface IUnownedCheckedProcedure extends ICheckedProcedure {


	public interface IUnownedCheckedProcedureFactory extends ICheckedProcedureFactory {
		
		@Override
		public IUnownedCheckedProcedure create();
	}
	
}
