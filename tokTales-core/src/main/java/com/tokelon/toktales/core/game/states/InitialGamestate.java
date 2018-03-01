package com.tokelon.toktales.core.game.states;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.inject.ForClass;

public class InitialGamestate extends BaseGamestate {
	
	public static final String TAG = "InitialGamestate";
	
	
    @Inject
	public InitialGamestate(@ForClass(InitialGamestate.class) IGameStateInputHandler inputHandler) {
    	super(null, inputHandler, null, null);
	}
    
    
    @Override
    protected String getTag() {
    	return TAG + "_" + BASE_TAG;
    }
    
}
