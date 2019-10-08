package com.tokelon.toktales.tools.procedure.checked;

public interface ISupplierCheckedProcedure<O, R> extends IOwnedCheckedProcedure<O> {


	public R run(O owner) throws Exception;
	
	
	public default IActionCheckedProcedure<O> toAction() {
		return (owner) -> run(owner);
	}
	
	
	public default ISupplierCheckedProcedure<O, R> compose(IActionCheckedProcedure<? super O> before) {
		return (owner) -> {
			before.run(owner);
			return run(owner);
		};
	}
	
	public default IActionCheckedProcedure<O> andThen(IConsumerCheckedProcedure<O, ? super R> after) {
		return (owner) -> after.run(owner, run(owner));
	}
	
	public default ISupplierCheckedProcedure<O, R> andThen(IActionCheckedProcedure<? super O> after) {
		return (owner) -> {
			R result = run(owner);
			after.run(owner);
			return result;
		};
	}
	
	public default <V> ISupplierCheckedProcedure<O, V> andThen(IFunctionCheckedProcedure<? super O, ? extends V, ? super R> after) {
		return (owner) -> {
			return after.run(owner, run(owner));
		};
	}
	
	
	public interface ISupplierCheckedProcedureFactory<O, R> extends IOwnedCheckedProcedureFactory<O> {
		
		@Override
		public ISupplierCheckedProcedure<O, R> create();
	}
	
}
