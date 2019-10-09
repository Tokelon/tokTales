package com.tokelon.toktales.tools.procedure;

import com.tokelon.toktales.tools.procedure.checked.ITargetedCheckedProcedure;

public interface ITargetedProcedure<T> extends IProcedure, ITargetedCheckedProcedure<T> {


	public interface ITargetedProcedureFactory<T> extends IProcedureFactory, ITargetedCheckedProcedureFactory<T> {
		
		@Override
		public ITargetedProcedure<T> create();
	}
	
}
