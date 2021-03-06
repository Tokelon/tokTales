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

/** Desktop platform implementation of {@link IEngineApplication}.
 */
public class TokTalesApplication implements IEngineApplication {


	private final IDesktopLauncherFactory launcherFactory;
	private final IWindowContextFactory windowContextFactory;
	private final IWindowFactory windowFactory;

	private final ILogger logger;

	/** Default constructor.
	 * <p>
	 * The default logger factory will be used.
	 */
	public TokTalesApplication() {
		this(LoggingManager.getLoggerFactory());
	}

	/** Constructor with a logger factory.
	 *
	 * @param loggerFactory
	 */
	public TokTalesApplication(ILoggerFactory loggerFactory) {
		this.logger = loggerFactory.getLogger(getClass());

		this.launcherFactory = createLauncherFactory();
		this.windowContextFactory = createWindowContextFactory();
		this.windowFactory = createWindowFactory();
	}


	/**
	 * @return The logger for this application.
	 */
	protected ILogger getLogger() {
		return logger;
	}


	@Override
	public void run(String[] args) throws EngineException {
		IDesktopEngineLauncher launcher = makeDefaultEngineLauncher(makeDefaultInjectConfig());
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
		defaultLauncher.launchWithSetup(makeDefaultGameAdapter(), makeDefaultEngineSetup());
	}


	@Override
	public IDesktopEngineLauncher makeDefaultEngineLauncher(IHierarchicalInjectConfig defaultInjectConfig) {
		return getLauncherFactory()
				.createDefaultLauncherBuilder()
				.withInjectConfig(defaultInjectConfig)
				.withWindow(makeDefaultWindowBuilder(), makeDefaultWindowConfigurator())
				.build();
	}

	@Override
	public IHierarchicalInjectConfig makeDefaultInjectConfig() {
		return new MasterDesktopInjectConfig();
	}

	@Override
	public IEngineSetup makeDefaultEngineSetup() {
		return new DesktopEngineSetup();
	}

	@Override
	public Class<? extends IGameAdapter> makeDefaultGameAdapter() {
		return EmptyGameAdapter.class;
	}


	/** Returns a default window builder that will be used for the default engine launcher, in {@link #makeDefaultEngineLauncher(IHierarchicalInjectConfig)}.
	 *
	 * @return The default window builder.
	 */
	public IWindowBuilder makeDefaultWindowBuilder() {
		return getWindowFactory().createDefaultBuilder();
	}

	/** Returns a default window configurator that will be used for the default engine launcher, in {@link #makeDefaultEngineLauncher(IHierarchicalInjectConfig)}.
	 *
	 * @return The default window configurator.
	 */
	public IWindowConfigurator makeDefaultWindowConfigurator() {
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
