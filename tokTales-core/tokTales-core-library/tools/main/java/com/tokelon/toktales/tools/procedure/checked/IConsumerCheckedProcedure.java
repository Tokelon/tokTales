package com.tokelon.toktales.tools.procedure.checked;

public interface IConsumerCheckedProcedure<O, P> extends IOwnedCheckedProcedure<O> {


	public void run(O owner, P parameter) throws Exception;
	
	
	public default IActionCheckedProcedure<O> toAction(P parameter) {
		return (owner) -> run(owner, parameter);
	}
	
	
	public default IConsumerCheckedProcedure<O, P> compose(IConsumerCheckedProcedure<? super O, ? super P> before) {
		return (owner, parameter) -> {
			before.run(owner, parameter);
			run(owner, parameter);
		};
	}
	
	public default IActionCheckedProcedure<O> compose(ISupplierCheckedProcedure<? super O, ? extends P> before) {
		return (owner) -> {
			run(owner, before.run(owner));
		};
	}
	
	public default IConsumerCheckedProcedure<O, P> compose(IActionCheckedProcedure<? super O> before) {
		return (owner, parameter) -> {
			before.run(owner);
			run(owner, parameter);
		};
	}
	
	public default <V> IConsumerCheckedProcedure<O, V> compose(IFunctionCheckedProcedure<? super O, ? extends P, ? super V> before) {
		return (owner, parameter) -> run(owner, before.run(owner, parameter));
	}
	
	
	public default IConsumerCheckedProcedure<O, P> andThen(IConsumerCheckedProcedure<? super O, ? super P> after) {
		return (owner, parameter) -> {
			run(owner, parameter);
			after.run(owner, parameter);
		};
	}
	
	public default IConsumerCheckedProcedure<O, P> andThen(IActionCheckedProcedure<? super O> after) {
		return (owner, parameter) -> {
			run(owner, parameter);
			after.run(owner);
		};
	}
	
	
	public interface IConsumerCheckedProcedureFactory<O, P> extends IOwnedCheckedProcedureFactory<O> {
		
		@Override
		public IConsumerCheckedProcedure<O, P> create();
	}
	
}
