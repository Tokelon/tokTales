package com.tokelon.toktales.core.script;

import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.logic.observers.IParticipant;
import com.tokelon.toktales.tools.script.IScriptModule;
import com.tokelon.toktales.tools.script.ScriptErrorException;

/**
 * Note that both {@link IParticipant#getObservationInterest(Object, String)} and {@link IParticipant#getParticipationInterest(Object, String)} will not call the module but simply return 'this'.
 * 
 *
 * @param <S>
 */
public class ScriptModuleParticipant<S> extends ScriptModuleObserver<S> implements IParticipant<S> {

	
	public static final String TAG = "ScriptModuleParticipant";

	public static final String PARTICIPANT_FUNCTION_ON_SUBJECT_CHANGE = "onSubjectChange";
	
	
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
					TokTales.getLog().e(TAG, "onSubjectChange() did not return Boolean");
				}
				
				return true;
			}
		}
		catch(ScriptErrorException e) {
			if(++counterOnSubjectChange <= maxErrorsLogged) {
				TokTales.getLog().e(TAG, "onSubjectChange() failed to run: " +e.getMessage());	
			}
			
			return true;
		}
	}
	
	

}
