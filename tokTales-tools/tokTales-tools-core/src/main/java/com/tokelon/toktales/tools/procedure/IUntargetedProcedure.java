package com.tokelon.toktales.tools.procedure;

import com.tokelon.toktales.tools.procedure.checked.IUntargetedCheckedProcedure;

public interface IUntargetedProcedure extends IProcedure, IUntargetedCheckedProcedure {
	// Add untargeted types? -> Mapped to Java functional types | Would require dependency to Java 8 / Retrofuture


	public interface IUntargetedProcedureFactory extends IProcedureFactory, IUntargetedCheckedProcedureFactory {
		
		@Override
		public IUntargetedProcedure create();
	}
	
}
