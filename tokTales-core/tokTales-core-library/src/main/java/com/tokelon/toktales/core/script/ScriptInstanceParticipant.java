package com.tokelon.toktales.core.script;

import com.tokelon.toktales.core.game.logic.observers.IParticipant;
import com.tokelon.toktales.tools.script.IScriptInstance;
import com.tokelon.toktales.tools.script.ScriptErrorException;

/**
 * Note that both {@link IParticipant#getObservationInterest(Object, String)} and {@link IParticipant#getParticipationInterest(Object, String)} will not call the instance but simply return 'this'.
 * 
 *
 * @param <S>
 */
public class ScriptInstanceParticipant<S> extends ScriptInstanceObserver<S> implements IParticipant<S> {


	public static final String PARTICIPANT_METHOD_ON_SUBJECT_CHANGE = "onSubjectChange";
	
	
	public ScriptInstanceParticipant(IScriptInstance scriptInstance) {
		super(scriptInstance);
	}

	

	@Override
	public IParticipant<S> getParticipationInterest(S subject, String change) {
		return this;
	}
	
	@Override
	public boolean onSubjectChange(S subject, String change) {
		
		try {
			Object result = getScriptInstance().callMethod(PARTICIPANT_METHOD_ON_SUBJECT_CHANGE, subject, change);
			
			if(result instanceof Boolean) {
				return (Boolean) result;
			}
			else {
				reportError(PARTICIPANT_METHOD_ON_SUBJECT_CHANGE, "onSubjectChange() did not return Boolean");
				return true;
			}
		}
		catch(ScriptErrorException e) {
			reportError(PARTICIPANT_METHOD_ON_SUBJECT_CHANGE, "onSubjectChange() failed to run:", e);
			return true;
		}
	}
	
}
