package com.tokelon.toktales.test.desktop.engine.inject;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.test.core.game.DummyGameAdapter;

/** Desktop inject module used for testing.
 * <p>
 * Mocks dependencies that are normally provided by the user.
 */
public class DesktopMockPlatformInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		bind(IGameAdapter.class).to(DummyGameAdapter.class);
	}

}
