package com.tokelon.toktales.core.script;

import com.tokelon.toktales.core.game.logic.observers.IObserver;
import com.tokelon.toktales.tools.core.script.IScriptInstance;
import com.tokelon.toktales.tools.core.script.ScriptErrorException;

/**
 * Note that {@link IObserver#getObservationInterest(Object, String)} will not call the instance but simply return 'this'.
 * 
 *
 * @param <S>
 */
public class ScriptInstanceObserver<S> extends ScriptInstanceBase implements IObserver<S> {


	public static final String OBSERVER_METHOD_HAS_INTEREST = "hasInterest";
	public static final String OBSERVER_METHOD_SUBJECT_CHANGED = "subjectChanged";
	
	
	public ScriptInstanceObserver(IScriptInstance scriptInstance) {
		super(scriptInstance);
	}
	
	
	@Override
	public boolean isGeneric() {
		return true;
	}
	
	
	@Override
	public boolean hasInterest(S subject, String change) {
		
		try {
			Object result = getScriptInstance().callMethod(OBSERVER_METHOD_HAS_INTEREST, subject, change);
			
			if(result instanceof Boolean) {
				return (Boolean) result;
			}
			else {
				reportError(OBSERVER_METHOD_HAS_INTEREST, "hasInterest() did not return Boolean");
				return false;
			}
		} catch (ScriptErrorException e) {
			reportError(OBSERVER_METHOD_HAS_INTEREST, "hasInterest() failed to run:", e);
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
			getScriptInstance().callMethod(OBSERVER_METHOD_SUBJECT_CHANGED, subject, change);
			
		} catch (ScriptErrorException e) {
			reportError(OBSERVER_METHOD_SUBJECT_CHANGED, "subjectChanged() failed to run:", e);
		}
	}
	

}
