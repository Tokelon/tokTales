package com.tokelon.toktales.tools.procedure.checked;

public interface IConsumerCheckedProcedure<T, P> extends ITargetedCheckedProcedure<T> {


	public void run(T target, P parameter) throws Exception;
	
	
	public default IActionCheckedProcedure<T> toAction(P parameter) {
		return (target) -> run(target, parameter);
	}
	
	
	public default IConsumerCheckedProcedure<T, P> compose(IConsumerCheckedProcedure<? super T, ? super P> before) {
		return (target, parameter) -> {
			before.run(target, parameter);
			run(target, parameter);
		};
	}
	
	public default IActionCheckedProcedure<T> compose(ISupplierCheckedProcedure<? super T, ? extends P> before) {
		return (target) -> {
			run(target, before.run(target));
		};
	}
	
	public default IConsumerCheckedProcedure<T, P> compose(IActionCheckedProcedure<? super T> before) {
		return (target, parameter) -> {
			before.run(target);
			run(target, parameter);
		};
	}
	
	public default <V> IConsumerCheckedProcedure<T, V> compose(IFunctionCheckedProcedure<? super T, ? extends P, ? super V> before) {
		return (target, parameter) -> run(target, before.run(target, parameter));
	}
	
	
	public default IConsumerCheckedProcedure<T, P> andThen(IConsumerCheckedProcedure<? super T, ? super P> after) {
		return (target, parameter) -> {
			run(target, parameter);
			after.run(target, parameter);
		};
	}
	
	public default IConsumerCheckedProcedure<T, P> andThen(IActionCheckedProcedure<? super T> after) {
		return (target, parameter) -> {
			run(target, parameter);
			after.run(target);
		};
	}
	
	
	public interface IConsumerCheckedProcedureFactory<T, P> extends ITargetedCheckedProcedureFactory<T> {
		
		@Override
		public IConsumerCheckedProcedure<T, P> create();
	}
	
}
