package com.tokelon.toktales.tools.procedure;

public interface ISupplierProcedure<O, R> extends IOwnedProcedure<O> {


	public R run(O owner);
	
	
	public default IActionProcedure<O> toAction() {
		return (owner) -> run(owner);
	}
	
	
	public default ISupplierProcedure<O, R> compose(IActionProcedure<? super O> before) {
		return (owner) -> {
			before.run(owner);
			return run(owner);
		};
	}
	
	public default IActionProcedure<O> andThen(IConsumerProcedure<O, ? super R> after) {
		return (owner) -> after.run(owner, run(owner));
	}
	
	public default ISupplierProcedure<O, R> andThen(IActionProcedure<? super O> after) {
		return (owner) -> {
			R result = run(owner);
			after.run(owner);
			return result;
		};
	}
	
	public default <V> ISupplierProcedure<O, V> andThen(IFunctionProcedure<? super O, ? extends V, ? super R> after) {
		return (owner) -> {
			return after.run(owner, run(owner));
		};
	}
	
	
	public interface ISupplierProcedureFactory<O, R> extends IOwnedProcedureFactory<O> {
		
		public ISupplierProcedure<O, R> create();
	}
	
}
