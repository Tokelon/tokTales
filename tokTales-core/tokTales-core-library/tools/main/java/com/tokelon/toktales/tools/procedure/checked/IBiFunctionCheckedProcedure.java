package com.tokelon.toktales.tools.procedure.checked;

public interface IBiFunctionCheckedProcedure<O, R, U, V> extends IOwnedCheckedProcedure<O> {


	public R run(O owner, U firstParam, V secondParam) throws Exception;
	
	
	public default IFunctionCheckedProcedure<O, R, V> toFunctionWithFirstParam(U param1) {
		return (owner, param) -> run(owner, param1, param);
	}
	
	public default IFunctionCheckedProcedure<O, R, U> toFunctionWithSecondParam(V param2) {
		return (owner, param) -> run(owner, param, param2);
	}
	
	
	public default ISupplierCheckedProcedure<O, R> toSupplier(U param1, V param2) {
		return (owner) -> run(owner, param1, param2);
	}
	
	public default IConsumerCheckedProcedure<O, V> toConsumerWithFirstParam(U param1) {
		return (owner, param) -> run(owner, param1, param);
	}
	
	public default IConsumerCheckedProcedure<O, U> toConsumerWithSecondParam(V param2) {
		return (owner, param) -> run(owner, param, param2);
	}
	

	public default IBiFunctionCheckedProcedure<O, R, U, V> compose(IActionCheckedProcedure<? super O> before) {
		return (owner, param1, param2) -> {
			before.run(owner);
			return run(owner, param1, param2);
		};
	}
	

	public default <K> IBiFunctionCheckedProcedure<O, K, U, V> andThen(IFunctionCheckedProcedure<? super O, ? extends K, ? super R> after) {
		return (owner, param1, param2) -> after.run(owner, run(owner, param1, param2));
	}
	
	public default IBiFunctionCheckedProcedure<O, R, U, V> andThen(IActionCheckedProcedure<? super O> after) {
		return (owner, param1, param2) -> {
			R result = run(owner, param1, param2);
			after.run(owner);
			return result;
		};
	}
	
	
	public interface IBiFunctionCheckedProcedureFactory<O, R, U, V> extends IOwnedCheckedProcedureFactory<O> {
		
		@Override
		public IBiFunctionCheckedProcedure<O, R, U, V> create();
	}
	
}
