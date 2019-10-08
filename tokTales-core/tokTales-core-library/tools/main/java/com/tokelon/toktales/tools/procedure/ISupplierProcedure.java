package com.tokelon.toktales.tools.procedure;

import com.tokelon.toktales.tools.procedure.checked.ISupplierCheckedProcedure;

public interface ISupplierProcedure<T, R> extends ITargetedProcedure<T>, ISupplierCheckedProcedure<T, R> {


	@Override
	public R run(T target);
	
	
	@Override
	public default IActionProcedure<T> toAction() {
		return (target) -> run(target);
	}
	
	
	public default ISupplierProcedure<T, R> compose(IActionProcedure<? super T> before) {
		return (target) -> {
			before.run(target);
			return run(target);
		};
	}
	
	public default IActionProcedure<T> andThen(IConsumerProcedure<T, ? super R> after) {
		return (target) -> after.run(target, run(target));
	}
	
	public default ISupplierProcedure<T, R> andThen(IActionProcedure<? super T> after) {
		return (target) -> {
			R result = run(target);
			after.run(target);
			return result;
		};
	}
	
	public default <V> ISupplierProcedure<T, V> andThen(IFunctionProcedure<? super T, ? extends V, ? super R> after) {
		return (target) -> {
			return after.run(target, run(target));
		};
	}
	
	
	public interface ISupplierProcedureFactory<T, R> extends ITargetedProcedureFactory<T>, ISupplierCheckedProcedureFactory<T, R> {
		
		@Override
		public ISupplierProcedure<T, R> create();
	}
	
}
