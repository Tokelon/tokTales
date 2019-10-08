package com.tokelon.toktales.tools.procedure;

public interface IActionProcedure<O> extends IOwnedProcedure<O> {


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
	
	
	public interface IActionProcedureFactory<O> extends IOwnedProcedureFactory<O> {
		
		public IActionProcedure<O> create();
	}
	
}
