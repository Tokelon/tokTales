package com.tokelon.toktales.core.engine.inject;

import com.tokelon.toktales.core.game.IGameAdapter;

public class BaseSetupInjectModule extends AbstractInjectModule {


	private final Class<? extends IGameAdapter> adapterClass;

	/** Constructor with an adapter class.
	 * 
	 * @param adapterClass
	 * @throws NullPointerException If adapterClass is null.
	 */
	public BaseSetupInjectModule(Class<? extends IGameAdapter> adapterClass) {
		if(adapterClass == null) {
			throw new NullPointerException();
		}

		this.adapterClass = adapterClass;
	}


	@Override
	protected void configure() {
		bindInGameScope(IGameAdapter.class, adapterClass);
	}

}
