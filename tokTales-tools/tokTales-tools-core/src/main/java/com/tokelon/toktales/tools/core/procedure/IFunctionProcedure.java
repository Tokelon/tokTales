package com.tokelon.toktales.tools.core.procedure;

import com.tokelon.toktales.tools.core.procedure.checked.IFunctionCheckedProcedure;

public interface IFunctionProcedure<T, R, P> extends ITargetedProcedure<T>, IFunctionCheckedProcedure<T, R, P> {


	@Override
	public R run(T target, P parameter);
	
	
	@Override
	public default ISupplierProcedure<T, R> toSupplier(P param) {
		return (target) -> run(target, param);
	}
	
	@Override
	public default IConsumerProcedure<T, P> toConsumer() {
		return (target, parameter) -> run(target, parameter);
	}
	
	
	public default <V> IFunctionProcedure<T, R, V> compose(IFunctionProcedure<? super T, ? extends P, ? super V> before) {
		return (target, value) -> run(target, before.run(target, value));
	}

	public default ISupplierProcedure<T, R> compose(ISupplierProcedure<? super T, ? extends P> before) {
		return (target) -> run(target, before.run(target));
	}
	
	public default IFunctionProcedure<T, R, P> compose(IActionProcedure<? super T> before) {
		return (target, value) -> {
			before.run(target);
			return run(target, value);
		};
	}
	
	public default <V> IFunctionProcedure<T, V, P> andThen(IFunctionProcedure<? super T, ? extends V, ? super R> after) {
		return (target, parameter) -> after.run(target, run(target, parameter));
	}

	public default IConsumerProcedure<T, P> andThen(IConsumerProcedure<? super T, ? super R> after) {
		return (target, parameter) -> after.run(target, run(target, parameter));
	}
	
	public default IFunctionProcedure<T, R, P> andThen(IActionProcedure<? super T> after) {
		return (target, value) -> {
			R result = run(target, value);
			after.run(target);
			return result;
		};
	}
	

	public interface IFunctionProcedureFactory<T, R, P> extends ITargetedProcedureFactory<T>, IFunctionCheckedProcedureFactory<T, R, P> {
		
		@Override
		public IFunctionProcedure<T, R, P> create();
	}
	
}
