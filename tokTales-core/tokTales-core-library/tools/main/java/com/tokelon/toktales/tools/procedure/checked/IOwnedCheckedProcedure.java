package com.tokelon.toktales.tools.procedure.checked;

public interface IOwnedCheckedProcedure<O> extends ICheckedProcedure {


	public interface IOwnedCheckedProcedureFactory<O> extends ICheckedProcedureFactory {
		
		@Override
		public IOwnedCheckedProcedure<O> create();
	}
	
}
