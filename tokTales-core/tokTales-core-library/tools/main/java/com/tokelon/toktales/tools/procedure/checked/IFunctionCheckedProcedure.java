package com.tokelon.toktales.tools.procedure.checked;

public interface IFunctionCheckedProcedure<O, R, P> extends IOwnedCheckedProcedure<O> {


	public R run(O owner, P parameter) throws Exception;

	
	public default ISupplierCheckedProcedure<O, R> toSupplier(P param) {
		return (owner) -> run(owner, param);
	}
	
	public default IConsumerCheckedProcedure<O, P> toConsumer() {
		return (owner, parameter) -> run(owner, parameter);
	}
	
	
	public default <V> IFunctionCheckedProcedure<O, R, V> compose(IFunctionCheckedProcedure<? super O, ? extends P, ? super V> before) {
		return (owner, value) -> run(owner, before.run(owner, value));
	}

	public default ISupplierCheckedProcedure<O, R> compose(ISupplierCheckedProcedure<? super O, ? extends P> before) {
		return (owner) -> run(owner, before.run(owner));
	}
	
	public default IFunctionCheckedProcedure<O, R, P> compose(IActionCheckedProcedure<? super O> before) {
		return (owner, value) -> {
			before.run(owner);
			return run(owner, value);
		};
	}
	
	public default <V> IFunctionCheckedProcedure<O, V, P> andThen(IFunctionCheckedProcedure<? super O, ? extends V, ? super R> after) {
		return (owner, parameter) -> after.run(owner, run(owner, parameter));
	}

	public default IConsumerCheckedProcedure<O, P> andThen(IConsumerCheckedProcedure<? super O, ? super R> after) {
		return (owner, parameter) -> after.run(owner, run(owner, parameter));
	}
	
	public default IFunctionCheckedProcedure<O, R, P> andThen(IActionCheckedProcedure<? super O> after) {
		return (owner, value) -> {
			R result = run(owner, value);
			after.run(owner);
			return result;
		};
	}
	
	
	public interface IFunctionCheckedProcedureFactory<O, R, P> extends IOwnedCheckedProcedureFactory<O> {
		
		@Override
		public IFunctionCheckedProcedure<O, R, P> create();
	}
	
}
