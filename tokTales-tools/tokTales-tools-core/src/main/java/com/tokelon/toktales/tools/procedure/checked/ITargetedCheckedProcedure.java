package com.tokelon.toktales.tools.procedure.checked;

public interface ITargetedCheckedProcedure<T> extends ICheckedProcedure {


	public interface ITargetedCheckedProcedureFactory<T> extends ICheckedProcedureFactory {
		
		@Override
		public ITargetedCheckedProcedure<T> create();
	}
	
}
