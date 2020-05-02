package com.tokelon.toktales.core.engine.setup;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;

/** A callback that will be executed to configure the engine.
 */
public interface ISetupStep {


	/** Called to prepare the engine for running.
	 *
	 * @param engineContext
	 * @throws EngineException If there are errors with the build up.
	 */
	public void onBuildUp(IEngineContext engineContext) throws EngineException;

	/** Called to clean up after the engine has ran.
	 *
	 * @param engineContext
	 * @throws EngineException If there are errors with the tear down.
	 */
	public void onTearDown(IEngineContext engineContext) throws EngineException;

}
