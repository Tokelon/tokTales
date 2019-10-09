package com.tokelon.toktales.tools.core.procedure.checked;

public interface IActionCheckedProcedure<T> extends ITargetedCheckedProcedure<T> {


	public void run(T target) throws Exception;
	
	
	public default IActionCheckedProcedure<T> compose(IActionCheckedProcedure<? super T> before) {
		return (target) -> {
			before.run(target);
			run(target);
		};
	}
	
	public default IActionCheckedProcedure<T> andThen(IActionCheckedProcedure<? super T> after) {
		return (target) -> {
			run(target);
			after.run(target);
		};
	}
	
	
	public interface IActionCheckedProcedureFactory<T> extends ITargetedCheckedProcedureFactory<T> {
	
		@Override
		public IActionCheckedProcedure<T> create();
	}
	
}
