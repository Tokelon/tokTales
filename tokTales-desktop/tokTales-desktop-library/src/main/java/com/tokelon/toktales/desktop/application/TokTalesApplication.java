package com.tokelon.toktales.desktop.application;

import com.tokelon.toktales.core.application.IEngineApplication;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineLauncher;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.EmptyGameAdapter;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.desktop.engine.DesktopLauncherFactory;
import com.tokelon.toktales.desktop.engine.IDesktopEngineLauncher;
import com.tokelon.toktales.desktop.engine.inject.MasterDesktopInjectConfig;
import com.tokelon.toktales.desktop.engine.setup.DesktopEngineSetup;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public class TokTalesApplication implements IEngineApplication {


	private final ILogger logger;

	public TokTalesApplication() {
		this(LoggingManager.getLoggerFactory());
	}

	public TokTalesApplication(ILoggerFactory loggerFactory) {
		this.logger = loggerFactory.getLogger(getClass());
	}


	/**
	 * @return The logger for this class.
	 */
	protected ILogger getLogger() {
		return logger;
	}


	@Override
	public void run(String[] args) throws EngineException {
		IDesktopEngineLauncher launcher = createDefaultEngineLauncher(createDefaultInjectConfig());
		try {
			launchEngine(launcher);
		}
		catch(EngineException engineException) {
			getLogger().error("Error while launching engine: ", engineException);
			throw engineException;
		}
	}

	@Override
	public void launchEngine(IEngineLauncher defaultLauncher) throws EngineException {
		defaultLauncher.launchWithSetup(getDefaultGameAdapter(), createDefaultEngineSetup());
	}


	@Override
	public IDesktopEngineLauncher createDefaultEngineLauncher(IHierarchicalInjectConfig defaultInjectConfig) {
		return new DesktopLauncherFactory()
				.createDefaultLauncherBuilder()
				.withInjectConfig(defaultInjectConfig)
				.build();
	}

	@Override
	public IHierarchicalInjectConfig createDefaultInjectConfig() {
		return new MasterDesktopInjectConfig();
	}

	@Override
	public IEngineSetup createDefaultEngineSetup() {
		return new DesktopEngineSetup();
	}

	@Override
	public Class<? extends IGameAdapter> getDefaultGameAdapter() {
		return EmptyGameAdapter.class;
	}

}
