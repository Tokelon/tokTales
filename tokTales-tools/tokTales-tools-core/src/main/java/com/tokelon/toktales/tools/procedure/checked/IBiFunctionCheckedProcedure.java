package com.tokelon.toktales.tools.procedure.checked;

public interface IBiFunctionCheckedProcedure<T, R, U, V> extends ITargetedCheckedProcedure<T> {


	public R run(T target, U firstParam, V secondParam) throws Exception;
	
	
	public default IFunctionCheckedProcedure<T, R, V> toFunctionWithFirstParam(U param1) {
		return (target, param) -> run(target, param1, param);
	}
	
	public default IFunctionCheckedProcedure<T, R, U> toFunctionWithSecondParam(V param2) {
		return (target, param) -> run(target, param, param2);
	}
	
	
	public default ISupplierCheckedProcedure<T, R> toSupplier(U param1, V param2) {
		return (target) -> run(target, param1, param2);
	}
	
	public default IConsumerCheckedProcedure<T, V> toConsumerWithFirstParam(U param1) {
		return (target, param) -> run(target, param1, param);
	}
	
	public default IConsumerCheckedProcedure<T, U> toConsumerWithSecondParam(V param2) {
		return (target, param) -> run(target, param, param2);
	}
	

	public default IBiFunctionCheckedProcedure<T, R, U, V> compose(IActionCheckedProcedure<? super T> before) {
		return (target, param1, param2) -> {
			before.run(target);
			return run(target, param1, param2);
		};
	}
	

	public default <K> IBiFunctionCheckedProcedure<T, K, U, V> andThen(IFunctionCheckedProcedure<? super T, ? extends K, ? super R> after) {
		return (target, param1, param2) -> after.run(target, run(target, param1, param2));
	}
	
	public default IBiFunctionCheckedProcedure<T, R, U, V> andThen(IActionCheckedProcedure<? super T> after) {
		return (target, param1, param2) -> {
			R result = run(target, param1, param2);
			after.run(target);
			return result;
		};
	}
	
	
	public interface IBiFunctionCheckedProcedureFactory<T, R, U, V> extends ITargetedCheckedProcedureFactory<T> {
		
		@Override
		public IBiFunctionCheckedProcedure<T, R, U, V> create();
	}
	
}
