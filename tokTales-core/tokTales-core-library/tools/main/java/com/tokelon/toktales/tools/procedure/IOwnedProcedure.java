package com.tokelon.toktales.tools.procedure;

public interface IOwnedProcedure<O> extends IProcedure {


	public interface IOwnedProcedureFactory<O> extends IProcedureFactory {
		
		public IOwnedProcedure<O> create();
	}
	
}
