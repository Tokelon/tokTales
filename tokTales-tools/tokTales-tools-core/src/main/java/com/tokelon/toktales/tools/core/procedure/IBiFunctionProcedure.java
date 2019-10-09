package com.tokelon.toktales.tools.core.procedure;

import com.tokelon.toktales.tools.core.procedure.checked.IBiFunctionCheckedProcedure;

public interface IBiFunctionProcedure<T, R, U, V> extends ITargetedProcedure<T>, IBiFunctionCheckedProcedure<T, R, U, V> {


	@Override
	public R run(T target, U firstParam, V secondParam);
	
	
	@Override
	public default IFunctionProcedure<T, R, V> toFunctionWithFirstParam(U param1) {
		return (target, param) -> run(target, param1, param);
	}
	
	@Override
	public default IFunctionProcedure<T, R, U> toFunctionWithSecondParam(V param2) {
		return (target, param) -> run(target, param, param2);
	}
	
	
	@Override
	public default ISupplierProcedure<T, R> toSupplier(U param1, V param2) {
		return (target) -> run(target, param1, param2);
	}
	
	@Override
	public default IConsumerProcedure<T, V> toConsumerWithFirstParam(U param1) {
		return (target, param) -> run(target, param1, param);
	}
	
	@Override
	public default IConsumerProcedure<T, U> toConsumerWithSecondParam(V param2) {
		return (target, param) -> run(target, param, param2);
	}
	

	public default IBiFunctionProcedure<T, R, U, V> compose(IActionProcedure<? super T> before) {
		return (target, param1, param2) -> {
			before.run(target);
			return run(target, param1, param2);
		};
	}
	

	public default <K> IBiFunctionProcedure<T, K, U, V> andThen(IFunctionProcedure<? super T, ? extends K, ? super R> after) {
		return (target, param1, param2) -> after.run(target, run(target, param1, param2));
	}
	
	public default IBiFunctionProcedure<T, R, U, V> andThen(IActionProcedure<? super T> after) {
		return (target, param1, param2) -> {
			R result = run(target, param1, param2);
			after.run(target);
			return result;
		};
	}
	
	
	public interface IBiFunctionProcedureFactory<T, R, U, V> extends ITargetedProcedureFactory<T>, IBiFunctionCheckedProcedureFactory<T, R, U, V> {
		
		@Override
		public IBiFunctionProcedure<T, R, U, V> create();
	}
	
}
