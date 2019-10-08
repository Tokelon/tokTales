package com.tokelon.toktales.tools.procedure;

import com.tokelon.toktales.tools.procedure.checked.IConsumerCheckedProcedure;

public interface IConsumerProcedure<T, P> extends ITargetedProcedure<T>, IConsumerCheckedProcedure<T, P> {


	@Override
	public void run(T target, P parameter);
	
	
	@Override
	public default IActionProcedure<T> toAction(P parameter) {
		return (target) -> run(target, parameter);
	}
	
	
	public default IConsumerProcedure<T, P> compose(IConsumerProcedure<? super T, ? super P> before) {
		return (target, parameter) -> {
			before.run(target, parameter);
			run(target, parameter);
		};
	}
	
	public default IActionProcedure<T> compose(ISupplierProcedure<? super T, ? extends P> before) {
		return (target) -> {
			run(target, before.run(target));
		};
	}
	
	public default IConsumerProcedure<T, P> compose(IActionProcedure<? super T> before) {
		return (target, parameter) -> {
			before.run(target);
			run(target, parameter);
		};
	}
	
	public default <V> IConsumerProcedure<T, V> compose(IFunctionProcedure<? super T, ? extends P, ? super V> before) {
		return (target, parameter) -> run(target, before.run(target, parameter));
	}
	
	
	public default IConsumerProcedure<T, P> andThen(IConsumerProcedure<? super T, ? super P> after) {
		return (target, parameter) -> {
			run(target, parameter);
			after.run(target, parameter);
		};
	}
	
	public default IConsumerProcedure<T, P> andThen(IActionProcedure<? super T> after) {
		return (target, parameter) -> {
			run(target, parameter);
			after.run(target);
		};
	}
    
	
	public interface IConsumerProcedureFactory<T, P> extends ITargetedProcedureFactory<T>, IConsumerCheckedProcedureFactory<T, P> {
		
		@Override
		public IConsumerProcedure<T, P> create();
	}
	
}
