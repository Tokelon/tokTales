package com.tokelon.toktales.core.engine.inject;

import com.tokelon.toktales.core.engine.IEngineLauncher;

public class BaseLauncherInjectModule extends AbstractInjectModule {


	private final IEngineLauncher launcher;

	/** Constructor with a launcher.
	 * 
	 * @param launcher
	 * @throws NullPointerException If launcher is null.
	 */
	public BaseLauncherInjectModule(IEngineLauncher launcher) {
		if(launcher == null) {
			throw new NullPointerException();
		}

		this.launcher = launcher;
	}


	@Override
	protected void configure() {
		bind(IEngineLauncher.class).toInstance(launcher);
	}

}
