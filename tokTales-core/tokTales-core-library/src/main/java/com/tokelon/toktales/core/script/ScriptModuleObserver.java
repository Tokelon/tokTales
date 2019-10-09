package com.tokelon.toktales.core.script;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.core.game.logic.observers.IObserver;
import com.tokelon.toktales.tools.core.script.IScriptModule;
import com.tokelon.toktales.tools.core.script.ScriptErrorException;

/**
 * Note that {@link IObserver#getObservationInterest(Object, String)} will not call the module but simply return 'this'.
 * 
 *
 * @param <S>
 */
public class ScriptModuleObserver<S> implements IObserver<S> {


	public static final String OBSERVER_FUNCTION_HAS_INTEREST = "hasInterest";
	public static final String OBSERVER_FUNCTION_SUBJECT_CHANGED = "subjectChanged";

	private static final ILogger logger = LoggingManager.getLogger(ScriptModuleObserver.class);

	
	private final int maxErrorsLogged = 3;
	private int counterHasInterest = 0;
	private int counterSubjectChanged = 0;
	
	
	private final IScriptModule module;
	
	public ScriptModuleObserver(IScriptModule observerModule) {
		this.module = observerModule;
	}
	
	
	
	@Override
	public boolean isGeneric() {
		return true;
	}
	
	
	@Override
	public boolean hasInterest(S subject, String change) {
		try {
			Object result = module.callFunction(OBSERVER_FUNCTION_HAS_INTEREST, subject, change);
			
			if(result instanceof Boolean) {
				return (Boolean) result;
			}
			else {
				if(++counterHasInterest <= maxErrorsLogged) {
					logger.error("hasInterest() did not return Boolean");
				}
				
				return false;
			}
		} catch (ScriptErrorException e) {
			if(++counterHasInterest <= maxErrorsLogged) {
				logger.error("hasInterest() failed to run:", e);
			}
			
			return false;
		}
	}

	@Override
	public IObserver<S> getObservationInterest(S subject, String change) {
		return this;
	}

	@Override
	public void subjectChanged(S subject, String change) {
		try {
			module.callFunction(OBSERVER_FUNCTION_SUBJECT_CHANGED, subject, change);
			
		} catch (ScriptErrorException e) {
			if(++counterSubjectChanged <= maxErrorsLogged) {
				logger.error("subjectChanged() failed to run:", e);
			}
		}
	}

}
