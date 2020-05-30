package com.tokelon.toktales.core.application;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineLauncher;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

/** Configures and runs the execution of the engine from the main entry point of the application.
 * <p>
 * Platform specific implementations are provided for your convenience.
 */
public interface IEngineApplication {


	/** Runs the application.
	 * <p>
	 * The main entry point for the application.
	 *
	 * @param args
	 * @throws EngineException If an exception is thrown while running the application.
	 * @see #launchEngine(IEngineLauncher)
	 * @see #makeDefaultEngineLauncher()
	 */
	public void run(String[] args) throws EngineException;


	/** Launches the engine.
	 *
	 * @param defaultLauncher
	 * @throws EngineException If an exception is thrown while launching or running the engine.
	 * @see #makeDefaultEngineSetup()
	 * @see #makeDefaultGameAdapter()
	 */
	public void launchEngine(IEngineLauncher defaultLauncher) throws EngineException;


	/** Returns a default engine launcher that will be used for {@link #launchEngine(IEngineLauncher)}.
	 *
	 * @param defaultInjectConfig
	 * @return The default engine launcher.
	 * @see #makeDefaultInjectConfig()
	 */
	public IEngineLauncher makeDefaultEngineLauncher(IHierarchicalInjectConfig defaultInjectConfig);

	/** Returns a default inject config that will be used for {@link #makeDefaultEngineLauncher()}.
	 *
	 * @return The default inject config.
	 */
	public IHierarchicalInjectConfig makeDefaultInjectConfig();

	/** Returns a default engine setup that will be used in {@link #launchEngine(IEngineLauncher)}.
	 *
	 * @return The default engine setup.
	 */
	public IEngineSetup makeDefaultEngineSetup();

	/** Returns a default game adapter that will be used in {@link #launchEngine(IEngineLauncher)}.
	 *
	 * @return The default game adapter class.
	 */
	public Class<? extends IGameAdapter> makeDefaultGameAdapter();

}
