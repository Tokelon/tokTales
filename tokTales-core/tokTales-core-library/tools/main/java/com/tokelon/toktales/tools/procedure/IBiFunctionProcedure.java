package com.tokelon.toktales.tools.procedure;

import com.tokelon.toktales.tools.procedure.checked.IBiFunctionCheckedProcedure;

public interface IBiFunctionProcedure<O, R, U, V> extends IOwnedProcedure<O>, IBiFunctionCheckedProcedure<O, R, U, V> {


	@Override
	public R run(O owner, U firstParam, V secondParam);
	
	
	@Override
	public default IFunctionProcedure<O, R, V> toFunctionWithFirstParam(U param1) {
		return (owner, param) -> run(owner, param1, param);
	}
	
	@Override
	public default IFunctionProcedure<O, R, U> toFunctionWithSecondParam(V param2) {
		return (owner, param) -> run(owner, param, param2);
	}
	
	
	@Override
	public default ISupplierProcedure<O, R> toSupplier(U param1, V param2) {
		return (owner) -> run(owner, param1, param2);
	}
	
	@Override
	public default IConsumerProcedure<O, V> toConsumerWithFirstParam(U param1) {
		return (owner, param) -> run(owner, param1, param);
	}
	
	@Override
	public default IConsumerProcedure<O, U> toConsumerWithSecondParam(V param2) {
		return (owner, param) -> run(owner, param, param2);
	}
	

	public default IBiFunctionProcedure<O, R, U, V> compose(IActionProcedure<? super O> before) {
		return (owner, param1, param2) -> {
			before.run(owner);
			return run(owner, param1, param2);
		};
	}
	

	public default <K> IBiFunctionProcedure<O, K, U, V> andThen(IFunctionProcedure<? super O, ? extends K, ? super R> after) {
		return (owner, param1, param2) -> after.run(owner, run(owner, param1, param2));
	}
	
	public default IBiFunctionProcedure<O, R, U, V> andThen(IActionProcedure<? super O> after) {
		return (owner, param1, param2) -> {
			R result = run(owner, param1, param2);
			after.run(owner);
			return result;
		};
	}
	
	
	public interface IBiFunctionProcedureFactory<O, R, U, V> extends IOwnedProcedureFactory<O>, IBiFunctionCheckedProcedureFactory<O, R, U, V> {
		
		@Override
		public IBiFunctionProcedure<O, R, U, V> create();
	}
	
}
