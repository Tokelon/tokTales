package com.tokelon.toktales.tools.core.procedure;

import com.tokelon.toktales.tools.core.procedure.checked.ITargetedCheckedProcedure;

public interface ITargetedProcedure<T> extends IProcedure, ITargetedCheckedProcedure<T> {


	public interface ITargetedProcedureFactory<T> extends IProcedureFactory, ITargetedCheckedProcedureFactory<T> {
		
		@Override
		public ITargetedProcedure<T> create();
	}
	
}
