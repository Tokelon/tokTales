package com.tokelon.toktales.tools.procedure;

import com.tokelon.toktales.tools.procedure.checked.IOwnedCheckedProcedure;

public interface IOwnedProcedure<O> extends IProcedure, IOwnedCheckedProcedure<O> {


	public interface IOwnedProcedureFactory<O> extends IProcedureFactory, IOwnedCheckedProcedureFactory<O> {
		
		@Override
		public IOwnedProcedure<O> create();
	}
	
}
