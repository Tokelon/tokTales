package com.tokelon.toktales.tools.procedure.checked;

public interface IActionCheckedProcedure<O> extends IOwnedCheckedProcedure<O> {


	public void run(O owner) throws Exception;
	
	
	public default IActionCheckedProcedure<O> compose(IActionCheckedProcedure<? super O> before) {
		return (owner) -> {
			before.run(owner);
			run(owner);
		};
	}
	
	public default IActionCheckedProcedure<O> andThen(IActionCheckedProcedure<? super O> after) {
		return (owner) -> {
			run(owner);
			after.run(owner);
		}; 
	}
	
	
	public interface IActionCheckedProcedureFactory<O> extends IOwnedCheckedProcedureFactory<O> {
	
		@Override
		public IActionCheckedProcedure<O> create();
	}
	
}
