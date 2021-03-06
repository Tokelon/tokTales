package com.tokelon.toktales.test.core.game.state;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.test.core.game.state.enginestate.DefaultEngineGamestate;
import com.tokelon.toktales.test.core.game.state.enginestate.EngineGamestate;
import com.tokelon.toktales.test.core.game.state.enginestate.EngineGamestateControlHandler;
import com.tokelon.toktales.test.core.game.state.enginestate.EngineGamestateRenderer;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamescene;

public class TestCoreStateInjection {
	// TODO: Test LocalMapGamestate either here or in extensions projects. And test UserGamestate?
	

	@Test
	public void EngineGamestateInjection_ShouldMatchTypes() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();
		
		EngineGamestate<IEngineGamescene> gamestate = injector.getInstance(DefaultEngineGamestate.class);
		
		assertTrue(gamestate.getStateRenderer().getClass() == EngineGamestateRenderer.class);
		assertTrue(gamestate.getStateControlHandler().getClass() == EngineGamestateControlHandler.class);
	}
	
	@Test
	public void EngineGamestateInjection_ShouldResetScope() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();
		
		EngineGamestate<IEngineGamescene> gamestate01 = injector.getInstance(DefaultEngineGamestate.class);
		EngineGamestate<IEngineGamescene> gamestate02 = injector.getInstance(DefaultEngineGamestate.class);
		
		assertNotSame(gamestate01, gamestate02);

		assertNotSame(gamestate01.getStateRenderer(), gamestate02.getStateRenderer());
		assertNotSame(gamestate01.getStateControlHandler(), gamestate02.getStateControlHandler());
	}
	
	@Test
	public void EngineGamestateInjection_ShouldRespectScope() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();
		
		EngineGamestate<IEngineGamescene> gamestate = injector.getInstance(DefaultEngineGamestate.class);
		
		assertSame(gamestate, ((EngineGamestateRenderer)gamestate.getStateRenderer()).getGamestate());		
		assertSame(gamestate, ((EngineGamestateControlHandler)gamestate.getStateControlHandler()).getGamestate());
	}
	
}
