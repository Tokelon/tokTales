package com.tokelon.toktales.core.game.states;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.inject.ForClass;

public class InitialGamestate extends BaseGamestate<IGameScene> {
	
	public static final String TAG = "InitialGamestate";
	
	
    @Inject
	public InitialGamestate(@ForClass(InitialGamestate.class) IGameStateInputHandler inputHandler) {
    	super(IGameScene.class, null, inputHandler, null, null);
	}
    
    
    @Override
    protected String getTag() {
    	return TAG + "_" + BASE_TAG;
    }
    
}
