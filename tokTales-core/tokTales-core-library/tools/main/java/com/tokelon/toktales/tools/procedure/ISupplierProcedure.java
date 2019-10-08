package com.tokelon.toktales.tools.procedure;

import com.tokelon.toktales.tools.procedure.checked.ISupplierCheckedProcedure;

public interface ISupplierProcedure<O, R> extends IOwnedProcedure<O>, ISupplierCheckedProcedure<O, R> {


	@Override
	public R run(O owner);
	
	
	@Override
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
	
	
	public interface ISupplierProcedureFactory<O, R> extends IOwnedProcedureFactory<O>, ISupplierCheckedProcedureFactory<O, R> {
		
		@Override
		public ISupplierProcedure<O, R> create();
	}
	
}
