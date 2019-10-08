package com.tokelon.toktales.tools.procedure;

public interface IConsumerProcedure<O, P> extends IOwnedProcedure<O> {


	public void run(O owner, P parameter);
	
	
	public default IActionProcedure<O> toAction(P parameter) {
		return (owner) -> run(owner, parameter);
	}
	
	
	public default IConsumerProcedure<O, P> compose(IConsumerProcedure<? super O, ? super P> before) {
		return (owner, parameter) -> {
			before.run(owner, parameter);
			run(owner, parameter);
		};
	}
	
	public default IActionProcedure<O> compose(ISupplierProcedure<? super O, ? extends P> before) {
		return (owner) -> {
			run(owner, before.run(owner));
		};
	}
	
	public default IConsumerProcedure<O, P> compose(IActionProcedure<? super O> before) {
		return (owner, parameter) -> {
			before.run(owner);
			run(owner, parameter);
		};
	}
	
	public default <V> IConsumerProcedure<O, V> compose(IFunctionProcedure<? super O, ? extends P, ? super V> before) {
		return (owner, parameter) -> run(owner, before.run(owner, parameter));
	}
	
	
	public default IConsumerProcedure<O, P> andThen(IConsumerProcedure<? super O, ? super P> after) {
		return (owner, parameter) -> {
			run(owner, parameter);
			after.run(owner, parameter);
		};
	}
	
	public default IConsumerProcedure<O, P> andThen(IActionProcedure<? super O> after) {
		return (owner, parameter) -> {
			run(owner, parameter);
			after.run(owner);
		};
	}
    
	
	public interface IConsumerProcedureFactory<O, P> extends IOwnedProcedureFactory<O> {
		
		public IConsumerProcedure<O, P> create();
	}
	
}
