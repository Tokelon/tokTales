package com.tokelon.toktales.tools.procedure;

import com.tokelon.toktales.tools.procedure.checked.ICheckedProcedure;

public interface IUnownedProcedure extends IProcedure, ICheckedProcedure {
	// Add unowned types? -> Mapped to Java functional types | Would require dependency to Java 8 / Retrofuture


	public interface IUnownedProcedureFactory extends IProcedureFactory, ICheckedProcedureFactory {
		
		@Override
		public IUnownedProcedure create();
	}
	
}
