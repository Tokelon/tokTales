package com.tokelon.toktales.test.desktop.game.states;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.test.core.game.states.enginestate.EngineGamestate;
import com.tokelon.toktales.test.desktop.game.states.enginestate.DesktopEngineGamestateControlScheme;
import com.tokelon.toktales.test.desktop.game.states.enginestate.DesktopEngineGamestateInputHandler;

public class TestDesktopStateInjection {


	@Test
	public void EngineGamestateInjection_ShouldPassCorrectTypes() {
		Injector injector = DesktopTestStatesInjectModule.createDesktopStatesInjector();
		
		EngineGamestate gamestate = injector.getInstance(EngineGamestate.class);
		
		assertTrue(gamestate.getStateControlScheme().getClass() == DesktopEngineGamestateControlScheme.class);
		assertTrue(gamestate.getStateInputHandler().getClass() == DesktopEngineGamestateInputHandler.class);
	}
	
	@Test
	public void EngineGamestateInjection_ShouldResetScope() {
		Injector injector = DesktopTestStatesInjectModule.createDesktopStatesInjector();
		
		EngineGamestate gamestate01 = injector.getInstance(EngineGamestate.class);
		EngineGamestate gamestate02 = injector.getInstance(EngineGamestate.class);
		
		assertNotSame(gamestate01, gamestate02);

		assertNotSame(gamestate01.getRenderOrder(), gamestate02.getRenderOrder());
		assertNotSame(gamestate01.getStateInput(), gamestate02.getStateInput());
		
		assertNotSame(gamestate01.getStateInputHandler(), gamestate02.getStateInputHandler());
		assertNotSame(gamestate01.getStateControlScheme(), gamestate02.getStateControlScheme());
	}
	
	@Test
	public void EngineGamestateInjection_ShouldRespectScope() {
		Injector injector = DesktopTestStatesInjectModule.createDesktopStatesInjector();
		
		EngineGamestate gamestate = injector.getInstance(EngineGamestate.class);
		
		assertSame(gamestate, ((DesktopEngineGamestateInputHandler)gamestate.getStateInputHandler()).getGamestate());
	}
	
}
