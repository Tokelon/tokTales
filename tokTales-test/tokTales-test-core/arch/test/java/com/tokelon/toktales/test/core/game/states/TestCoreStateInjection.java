package com.tokelon.toktales.test.core.game.states;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.test.core.game.states.enginestate.EngineGamestate;
import com.tokelon.toktales.test.core.game.states.enginestate.EngineGamestateControlHandler;
import com.tokelon.toktales.test.core.game.states.enginestate.EngineGamestateRender;

public class TestCoreStateInjection {
	
	// TODO: Test LocalMapGamestate either here or in extensions projects. And test UserGamestate?
	

	@Test
	public void EngineGamestateInjection_ShouldMatchTypes() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();
		
		EngineGamestate gamestate = injector.getInstance(EngineGamestate.class);
		
		assertTrue(gamestate.getStateRender().getClass() == EngineGamestateRender.class);
		assertTrue(gamestate.getStateControlHandler().getClass() == EngineGamestateControlHandler.class);
	}
	
	@Test
	public void EngineGamestateInjection_ShouldResetScope() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();
		
		EngineGamestate gamestate01 = injector.getInstance(EngineGamestate.class);
		EngineGamestate gamestate02 = injector.getInstance(EngineGamestate.class);
		
		assertNotSame(gamestate01, gamestate02);

		assertNotSame(gamestate01.getRenderOrder(), gamestate02.getRenderOrder());
		
		assertNotSame(gamestate01.getStateRender(), gamestate02.getStateRender());
		assertNotSame(gamestate01.getStateControlHandler(), gamestate02.getStateControlHandler());
	}
	
	@Test
	public void EngineGamestateInjection_ShouldRespectScope() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();
		
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
		EngineGamestate gamestate = injector.getInstance(EngineGamestate.class);
		
		assertSame(gamestate, ((EngineGamestateRender)gamestate.getStateRender()).getGamestate());
		assertSame(engineContext.getEngine().getRenderService().getSurfaceHandler(), ((EngineGamestateRender)gamestate.getStateRender()).getSurfaceHandler());
		
		assertSame(gamestate, ((EngineGamestateControlHandler)gamestate.getStateControlHandler()).getGamestate());
	}
	
}
