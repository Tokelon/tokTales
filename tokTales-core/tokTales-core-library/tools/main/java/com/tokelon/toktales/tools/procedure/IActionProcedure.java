package com.tokelon.toktales.tools.procedure;

import com.tokelon.toktales.tools.procedure.checked.IActionCheckedProcedure;

public interface IActionProcedure<T> extends ITargetedProcedure<T>, IActionCheckedProcedure<T> {


	@Override
	public void run(T target);
	
	
	public default Runnable toRunnable(T target) {
		return () -> run(target);
	}
	
	
	public default IActionProcedure<T> compose(IActionProcedure<? super T> before) {
		return (target) -> {
			before.run(target);
			run(target);
		};
	}
	
	public default IActionProcedure<T> andThen(IActionProcedure<? super T> after) {
		return (target) -> {
			run(target);
			after.run(target);
		};
	}
	
	
	public interface IActionProcedureFactory<T> extends ITargetedProcedureFactory<T>, IActionCheckedProcedureFactory<T> {
		
		@Override
		public IActionProcedure<T> create();
	}
	
}
