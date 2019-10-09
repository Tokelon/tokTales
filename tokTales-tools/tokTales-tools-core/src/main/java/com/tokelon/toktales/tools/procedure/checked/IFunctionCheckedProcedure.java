package com.tokelon.toktales.tools.procedure.checked;

public interface IFunctionCheckedProcedure<T, R, P> extends ITargetedCheckedProcedure<T> {


	public R run(T target, P parameter) throws Exception;

	
	public default ISupplierCheckedProcedure<T, R> toSupplier(P param) {
		return (target) -> run(target, param);
	}
	
	public default IConsumerCheckedProcedure<T, P> toConsumer() {
		return (target, parameter) -> run(target, parameter);
	}
	
	
	public default <V> IFunctionCheckedProcedure<T, R, V> compose(IFunctionCheckedProcedure<? super T, ? extends P, ? super V> before) {
		return (target, value) -> run(target, before.run(target, value));
	}

	public default ISupplierCheckedProcedure<T, R> compose(ISupplierCheckedProcedure<? super T, ? extends P> before) {
		return (target) -> run(target, before.run(target));
	}
	
	public default IFunctionCheckedProcedure<T, R, P> compose(IActionCheckedProcedure<? super T> before) {
		return (target, value) -> {
			before.run(target);
			return run(target, value);
		};
	}
	
	public default <V> IFunctionCheckedProcedure<T, V, P> andThen(IFunctionCheckedProcedure<? super T, ? extends V, ? super R> after) {
		return (target, parameter) -> after.run(target, run(target, parameter));
	}

	public default IConsumerCheckedProcedure<T, P> andThen(IConsumerCheckedProcedure<? super T, ? super R> after) {
		return (target, parameter) -> after.run(target, run(target, parameter));
	}
	
	public default IFunctionCheckedProcedure<T, R, P> andThen(IActionCheckedProcedure<? super T> after) {
		return (target, value) -> {
			R result = run(target, value);
			after.run(target);
			return result;
		};
	}
	
	
	public interface IFunctionCheckedProcedureFactory<T, R, P> extends ITargetedCheckedProcedureFactory<T> {
		
		@Override
		public IFunctionCheckedProcedure<T, R, P> create();
	}
	
}
