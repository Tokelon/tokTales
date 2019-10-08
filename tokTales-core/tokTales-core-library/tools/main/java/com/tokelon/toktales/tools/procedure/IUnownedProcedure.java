package com.tokelon.toktales.tools.procedure;

public interface IUnownedProcedure extends IProcedure {
	// Add unowned types? -> Mapped to Java functional types | Would require dependency to Java 8 / Retrofuture


	public interface IUnownedProcedureFactory extends IProcedureFactory {
		
		public IUnownedProcedure create();
	}
	
}
