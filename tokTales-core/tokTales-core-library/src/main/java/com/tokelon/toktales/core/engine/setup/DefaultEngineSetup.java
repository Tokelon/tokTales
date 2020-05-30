package com.tokelon.toktales.core.engine.setup;

import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.setup.steps.AddInitialGamestateSetupStep;
import com.tokelon.toktales.core.engine.setup.steps.InitScriptingSetupStep;
import com.tokelon.toktales.core.engine.setup.steps.LoadMainConfigSetupStep;
import com.tokelon.toktales.core.engine.setup.steps.RedirectScriptOutputSetupStep;
import com.tokelon.toktales.core.engine.setup.steps.RedirectSystemOutputSetupStep;

/** Default implementation of {@link IEngineSetup}.
 */
public class DefaultEngineSetup extends BaseEngineSetup {


	public static final String SETUP_STEP_DEFAULT_FIRST = "SETUP_STEP_DEFAULT_FIRST";
	public static final String SETUP_STEP_REDIRECT_SYSTEM_OUTPUT = "SETUP_STEP_REDIRECT_SYSTEM_OUTPUT";
	public static final String SETUP_STEP_LOAD_MAIN_CONFIG = "SETUP_STEP_LOAD_MAIN_CONFIG";
	public static final String SETUP_STEP_INIT_SCRIPTING = "SETUP_STEP_INIT_SCRIPTING";
	public static final String SETUP_STEP_REDIRECT_SCRIPT_OUTPUT = "SETUP_STEP_REDIRECT_SCRIPT_OUTPUT";
	public static final String SETUP_STEP_ADD_INITIAL_GAMESTATE = "SETUP_STEP_ADD_INITIAL_GAMESTATE";
	public static final String SETUP_STEP_DEFAULT_LAST = "SETUP_STEP_DEFAULT_LAST";


	/** Default constructor.
	 */
	public DefaultEngineSetup() {
		super();
		addDefaultSteps();
	}

	/** Constructor with setup steps.
	 *
	 * @param steps
	 */
	public DefaultEngineSetup(ISetupSteps steps) {
		super(steps);
		addDefaultSteps();
	}

	/** Constructor with a logger factory.
	 *
	 * @param loggerFactory
	 */
	public DefaultEngineSetup(ILoggerFactory loggerFactory) {
		super(loggerFactory);
		addDefaultSteps();
	}

	/** Constructor with setup steps and a logger factory.
	 *
	 * @param steps
	 * @param loggerFactory
	 */
	public DefaultEngineSetup(ISetupSteps steps, ILoggerFactory loggerFactory) {
		super(steps, loggerFactory);
		addDefaultSteps();
	}


	/** Adds the default steps to this setup.
	 * <p>
	 * Called in the constructor.
	 */
	protected void addDefaultSteps() {
		getSteps().insertStep(SETUP_STEP_DEFAULT_FIRST, new EmptySetupStep());
		getSteps().insertStep(SETUP_STEP_REDIRECT_SYSTEM_OUTPUT, new RedirectSystemOutputSetupStep());
		getSteps().insertStep(SETUP_STEP_LOAD_MAIN_CONFIG, new LoadMainConfigSetupStep());
		getSteps().insertStep(SETUP_STEP_INIT_SCRIPTING, new InitScriptingSetupStep());
		getSteps().insertStep(SETUP_STEP_REDIRECT_SCRIPT_OUTPUT, new RedirectScriptOutputSetupStep());
		getSteps().insertStep(SETUP_STEP_ADD_INITIAL_GAMESTATE, new AddInitialGamestateSetupStep());
		getSteps().insertStep(SETUP_STEP_DEFAULT_LAST, new EmptySetupStep());
	}

}
