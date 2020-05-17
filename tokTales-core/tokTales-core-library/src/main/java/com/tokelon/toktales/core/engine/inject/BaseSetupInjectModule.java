package com.tokelon.toktales.core.engine.inject;

import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.IGameAdapter;

public class BaseSetupInjectModule extends AbstractInjectModule {


	private final Class<? extends IGameAdapter> adapterClass;
	private final IEngineSetup engineSetup;

	/** Constructor with an adapter class and engine setup.
	 *
	 * @param adapterClass
	 * @param engineSetup
	 * @throws NullPointerException If adapterClass is null.
	 */
	public BaseSetupInjectModule(Class<? extends IGameAdapter> adapterClass, IEngineSetup engineSetup) {
		if(adapterClass == null || engineSetup == null) {
			throw new NullPointerException("adapterClass and engineSetup must not be null");
		}

		this.adapterClass = adapterClass;
		this.engineSetup = engineSetup;
	}


	@Override
	protected void configure() {
		bindInGameScope(IGameAdapter.class, adapterClass);
		bind(IEngineSetup.class).toInstance(engineSetup);
	}

}
