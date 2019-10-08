package com.tokelon.toktales.tools.procedure;

import com.tokelon.toktales.tools.procedure.checked.IActionCheckedProcedure;

public interface IActionProcedure<O> extends IOwnedProcedure<O>, IActionCheckedProcedure<O> {


	@Override
	public void run(O owner);
	
	
	public default Runnable toRunnable(O owner) {
		return () -> run(owner);
	}
	
	
	public default IActionProcedure<O> compose(IActionProcedure<? super O> before) {
		return (owner) -> {
			before.run(owner);
			run(owner);
		};
	}
	
	public default IActionProcedure<O> andThen(IActionProcedure<? super O> after) {
		return (owner) -> {
			run(owner);
			after.run(owner);
		}; 
	}
	
	
	public interface IActionProcedureFactory<O> extends IOwnedProcedureFactory<O>, IActionCheckedProcedureFactory<O> {
		
		@Override
		public IActionProcedure<O> create();
	}
	
}
