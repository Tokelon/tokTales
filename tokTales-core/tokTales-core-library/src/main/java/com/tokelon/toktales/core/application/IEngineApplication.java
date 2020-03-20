package com.tokelon.toktales.core.application;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineLauncher;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public interface IEngineApplication {


	/** Runs the application.
	 * <p>
	 * The main entry point for the application.
	 *
	 * @param args
	 * @throws EngineException If an exception is thrown while running the application.
	 * @see #launchEngine(IEngineLauncher)
	 */
	public void run(String[] args) throws EngineException;


	/** Launches the engine.
	 *
	 * @param defaultLauncher
	 * @throws EngineException If an exception is thrown while launching or running the engine.
	 * @see #createDefaultEngineLauncher()
	 * @see #createDefaultEngineSetup()
	 * @see #getDefaultGameAdapter()
	 */
	public void launchEngine(IEngineLauncher defaultLauncher) throws EngineException;


	/** Creates a default engine launcher that will be used in {@link #launchEngine(IEngineLauncher)}.
	 *
	 * @return A new instance of the default engine launcher.
	 * @see #createDefaultInjectConfig()
	 */
	public IEngineLauncher createDefaultEngineLauncher();

	/** Creates a default inject config that will be used for the default engine launcher in {@link #createDefaultEngineLauncher()}.
	 *
	 * @return A new instance of the default inject config.
	 */
	public IHierarchicalInjectConfig createDefaultInjectConfig();

	/** Creates a default engine setup that will be used in {@link #launchEngine(IEngineLauncher)}.
	 *
	 * @return A new instance of the default engine setup.
	 */
	public IEngineSetup createDefaultEngineSetup();

	/**
	 * @return The default game adapter class.
	 */
	public Class<? extends IGameAdapter> getDefaultGameAdapter();

}
