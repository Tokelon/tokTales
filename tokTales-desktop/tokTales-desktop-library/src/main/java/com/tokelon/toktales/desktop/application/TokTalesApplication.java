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
import com.tokelon.toktales.desktop.engine.IDesktopLauncherFactory;
import com.tokelon.toktales.desktop.engine.inject.MasterDesktopInjectConfig;
import com.tokelon.toktales.desktop.engine.setup.DesktopEngineSetup;
import com.tokelon.toktales.desktop.lwjgl.ui.IWindowContextFactory;
import com.tokelon.toktales.desktop.ui.window.DesktopWindowContextFactory;
import com.tokelon.toktales.desktop.ui.window.DesktopWindowFactory;
import com.tokelon.toktales.desktop.ui.window.IWindowBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowConfigurator;
import com.tokelon.toktales.desktop.ui.window.IWindowFactory;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public class TokTalesApplication implements IEngineApplication {


	private final IDesktopLauncherFactory launcherFactory;
	private final IWindowContextFactory windowContextFactory;
	private final IWindowFactory windowFactory;

	private final ILogger logger;

	public TokTalesApplication() {
		this(LoggingManager.getLoggerFactory());
	}

	public TokTalesApplication(ILoggerFactory loggerFactory) {
		this.logger = loggerFactory.getLogger(getClass());

		this.launcherFactory = createLauncherFactory();
		this.windowContextFactory = createWindowContextFactory();
		this.windowFactory = createWindowFactory();
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
			getLogger().error("Fatal error while running engine: ", engineException);
			throw engineException;
		}
	}

	@Override
	public void launchEngine(IEngineLauncher defaultLauncher) throws EngineException {
		defaultLauncher.launchWithSetup(getDefaultGameAdapter(), createDefaultEngineSetup());
	}


	@Override
	public IDesktopEngineLauncher createDefaultEngineLauncher(IHierarchicalInjectConfig defaultInjectConfig) {
		return getLauncherFactory()
				.createDefaultLauncherBuilder()
				.withInjectConfig(defaultInjectConfig)
				.withWindow(createDefaultWindowBuilder(), createDefaultWindowConfigurator())
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


	/** Creates a default window builder that will be used for the default engine launcher in {@link #createDefaultEngineLauncher(IHierarchicalInjectConfig)}.
	 *
	 * @return A new instance of the default window builder.
	 */
	public IWindowBuilder createDefaultWindowBuilder() {
		return getWindowFactory().createDefaultBuilder();
	}

	/** Creates a default window configurator that will be used for the default engine launcher in {@link #createDefaultEngineLauncher(IHierarchicalInjectConfig)}.
	 *
	 * @return A new instance of the default window configurator.
	 */
	public IWindowConfigurator createDefaultWindowConfigurator() {
		return getWindowFactory().getDefaultHintsConfigurator();
	}


	/** Convenience method for accessing a launcher factory for this application.
	 *
	 * @return The launcher factory for this application.
	 */
	public IDesktopLauncherFactory getLauncherFactory() {
		return launcherFactory;
	}

	/** Convenience method for accessing a window context factory for this application.
	 *
	 * @return The window context factory for this application.
	 */
	public IWindowContextFactory getWindowContextFactory() {
		return windowContextFactory;
	}

	/** Convenience method for accessing a window factory for this application.
	 *
	 * @return The window factory for this application.
	 */
	public IWindowFactory getWindowFactory() {
		return windowFactory;
	}


	/** Creates a launcher factory.
	 *
	 * @return A new instance of the default launcher factory.
	 * @see #getLauncherFactory()
	 */
	public IDesktopLauncherFactory createLauncherFactory() {
		return new DesktopLauncherFactory();
	}

	/** Creates a window context factory.
	 *
	 * @return A new instance of the default window context factory.
	 * @see #getWindowContextFactory()
	 */
	public IWindowContextFactory createWindowContextFactory() {
		return new DesktopWindowContextFactory();
	}

	/** Creates a window factory.
	 *
	 * @return A new instance of the default window factory.
	 * @see #getWindowFactory()
	 */
	public IWindowFactory createWindowFactory() {
		return new DesktopWindowFactory();
	}

}
