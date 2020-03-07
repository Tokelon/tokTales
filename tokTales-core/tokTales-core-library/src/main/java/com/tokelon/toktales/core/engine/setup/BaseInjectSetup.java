package com.tokelon.toktales.core.engine.setup;

import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.setup.steps.AddInitialGamestateStep;
import com.tokelon.toktales.core.engine.setup.steps.InitScriptingStep;
import com.tokelon.toktales.core.engine.setup.steps.LoadMainConfigStep;

public class BaseInjectSetup extends AbstractInjectSetup {


	public static final String STEP_LOAD_MAIN_CONFIG = "STEP_LOAD_MAIN_CONFIG";
	public static final String STEP_INIT_SCRIPTING = "STEP_INIT_SCRIPTING";
	public static final String STEP_ADD_INITIAL_GAMESTATE = "STEP_ADD_INITIAL_GAMESTATE";
	
	
	public BaseInjectSetup() {
		super();
		addDefaultSteps();
	}

	public BaseInjectSetup(ISetupSteps steps) {
		super(steps);
		addDefaultSteps();
	}
	
	public BaseInjectSetup(ILoggerFactory loggerFactory) {
		super(loggerFactory);
		addDefaultSteps();
	}

	public BaseInjectSetup(ISetupSteps steps, ILoggerFactory loggerFactory) {
		super(steps, loggerFactory);
		addDefaultSteps();
	}

	
	/** Adds the default steps to this setup.
	 * <p>
	 * Called in the constructor.
	 */
	protected void addDefaultSteps() {
		getSteps().insertStep(STEP_LOAD_MAIN_CONFIG, new LoadMainConfigStep());
		getSteps().insertStep(STEP_INIT_SCRIPTING, new InitScriptingStep());
		getSteps().insertStep(STEP_ADD_INITIAL_GAMESTATE, new AddInitialGamestateStep());
	}

}
