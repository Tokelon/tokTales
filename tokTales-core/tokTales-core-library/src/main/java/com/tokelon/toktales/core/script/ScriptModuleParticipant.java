package com.tokelon.toktales.core.script;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.core.game.logic.observers.IParticipant;
import com.tokelon.toktales.tools.core.script.IScriptModule;
import com.tokelon.toktales.tools.core.script.ScriptErrorException;

/**
 * Note that both {@link IParticipant#getObservationInterest(Object, String)} and {@link IParticipant#getParticipationInterest(Object, String)} will not call the module but simply return 'this'.
 * 
 *
 * @param <S>
 */
public class ScriptModuleParticipant<S> extends ScriptModuleObserver<S> implements IParticipant<S> {


	public static final String PARTICIPANT_FUNCTION_ON_SUBJECT_CHANGE = "onSubjectChange";
	
	private static final ILogger logger = LoggingManager.getLogger(ScriptModuleParticipant.class);

	
	private final int maxErrorsLogged = 3;
	private int counterOnSubjectChange = 0;
	
	
	private final IScriptModule module;
	
	public ScriptModuleParticipant(IScriptModule participantModule) {
		super(participantModule);
		
		module = participantModule;
	}

	
	@Override
	public IParticipant<S> getParticipationInterest(S subject, String change) {
		return this;
	}

	@Override
	public boolean onSubjectChange(S subject, String change) {
		
		try {
			Object result = module.callFunction(PARTICIPANT_FUNCTION_ON_SUBJECT_CHANGE, subject, change);
			
			if(result instanceof Boolean) {
				return (Boolean) result;
			}
			else {
				if(++counterOnSubjectChange <= maxErrorsLogged) {
					logger.error("onSubjectChange() did not return Boolean");
				}
				
				return true;
			}
		}
		catch(ScriptErrorException e) {
			if(++counterOnSubjectChange <= maxErrorsLogged) {
				logger.error("onSubjectChange() failed to run:", e);	
			}
			
			return true;
		}
	}
	
}
