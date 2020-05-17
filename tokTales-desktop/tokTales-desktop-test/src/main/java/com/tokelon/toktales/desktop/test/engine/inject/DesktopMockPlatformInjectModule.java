package com.tokelon.toktales.desktop.test.engine.inject;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.setup.DefaultEngineSetup;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.core.test.game.DummyGameAdapter;

/** Desktop inject module used for testing.
 * <p>
 * Mocks dependencies that are normally provided by the user.
 */
public class DesktopMockPlatformInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		bind(IGameAdapter.class).to(DummyGameAdapter.class);
		bind(IEngineSetup.class).toInstance(new DefaultEngineSetup());
	}

}
