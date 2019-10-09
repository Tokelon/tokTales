package com.tokelon.toktales.tools.core.procedure;

import com.tokelon.toktales.tools.core.procedure.checked.IUntargetedCheckedProcedure;

public interface IUntargetedProcedure extends IProcedure, IUntargetedCheckedProcedure {
	// Add untargeted types? -> Mapped to Java functional types | Would require dependency to Java 8 / Retrofuture


	public interface IUntargetedProcedureFactory extends IProcedureFactory, IUntargetedCheckedProcedureFactory {
		
		@Override
		public IUntargetedProcedure create();
	}
	
}
