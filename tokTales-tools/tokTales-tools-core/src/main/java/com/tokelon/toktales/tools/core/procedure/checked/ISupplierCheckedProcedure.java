package com.tokelon.toktales.tools.core.procedure.checked;

public interface ISupplierCheckedProcedure<T, R> extends ITargetedCheckedProcedure<T> {


	public R run(T target) throws Exception;
	
	
	public default IActionCheckedProcedure<T> toAction() {
		return (target) -> run(target);
	}
	
	
	public default ISupplierCheckedProcedure<T, R> compose(IActionCheckedProcedure<? super T> before) {
		return (target) -> {
			before.run(target);
			return run(target);
		};
	}
	
	public default IActionCheckedProcedure<T> andThen(IConsumerCheckedProcedure<T, ? super R> after) {
		return (target) -> after.run(target, run(target));
	}
	
	public default ISupplierCheckedProcedure<T, R> andThen(IActionCheckedProcedure<? super T> after) {
		return (target) -> {
			R result = run(target);
			after.run(target);
			return result;
		};
	}
	
	public default <V> ISupplierCheckedProcedure<T, V> andThen(IFunctionCheckedProcedure<? super T, ? extends V, ? super R> after) {
		return (target) -> {
			return after.run(target, run(target));
		};
	}
	
	
	public interface ISupplierCheckedProcedureFactory<T, R> extends ITargetedCheckedProcedureFactory<T> {
		
		@Override
		public ISupplierCheckedProcedure<T, R> create();
	}
	
}
