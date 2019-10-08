package com.tokelon.toktales.tools.procedure;

import com.tokelon.toktales.tools.procedure.checked.IFunctionCheckedProcedure;

public interface IFunctionProcedure<O, R, P> extends IOwnedProcedure<O>, IFunctionCheckedProcedure<O, R, P> {


	@Override
	public R run(O owner, P parameter);
	
	
	@Override
	public default ISupplierProcedure<O, R> toSupplier(P param) {
		return (owner) -> run(owner, param);
	}
	
	@Override
	public default IConsumerProcedure<O, P> toConsumer() {
		return (owner, parameter) -> run(owner, parameter);
	}
	
	
	public default <V> IFunctionProcedure<O, R, V> compose(IFunctionProcedure<? super O, ? extends P, ? super V> before) {
		return (owner, value) -> run(owner, before.run(owner, value));
	}

	public default ISupplierProcedure<O, R> compose(ISupplierProcedure<? super O, ? extends P> before) {
		return (owner) -> run(owner, before.run(owner));
	}
	
	public default IFunctionProcedure<O, R, P> compose(IActionProcedure<? super O> before) {
		return (owner, value) -> {
			before.run(owner);
			return run(owner, value);
		};
	}
	
	public default <V> IFunctionProcedure<O, V, P> andThen(IFunctionProcedure<? super O, ? extends V, ? super R> after) {
		return (owner, parameter) -> after.run(owner, run(owner, parameter));
	}

	public default IConsumerProcedure<O, P> andThen(IConsumerProcedure<? super O, ? super R> after) {
		return (owner, parameter) -> after.run(owner, run(owner, parameter));
	}
	
	public default IFunctionProcedure<O, R, P> andThen(IActionProcedure<? super O> after) {
		return (owner, value) -> {
			R result = run(owner, value);
			after.run(owner);
			return result;
		};
	}
	

	public interface IFunctionProcedureFactory<O, R, P> extends IOwnedProcedureFactory<O>, IFunctionCheckedProcedureFactory<O, R, P> {
		
		@Override
		public IFunctionProcedure<O, R, P> create();
	}
	
}
