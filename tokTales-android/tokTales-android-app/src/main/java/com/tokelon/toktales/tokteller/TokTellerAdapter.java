package com.tokelon.toktales.tokteller;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.EmptyGameAdapter;

public class TokTellerAdapter extends EmptyGameAdapter {


    @Override
    public void onCreate(IEngineContext engineContext) {
    	// TODO: Important - References to extensions - fix to use
    	/*
        ConsoleGamestate consoleState = new ConsoleGamestate(engineContext, new AndroidConsoleRenderingStrategy());
        AndroidConsoleGamestateConfigurator consoleConfigurator = new AndroidConsoleGamestateConfigurator();
        consoleConfigurator.configureState(consoleState);

        engineContext.getGame().getStateControl().addState(TokelonExtensionsStates.STATE_CONSOLE, consoleState);
        engineContext.getGame().getStateControl().changeState(TokelonExtensionsStates.STATE_CONSOLE);
        */
    }
}
