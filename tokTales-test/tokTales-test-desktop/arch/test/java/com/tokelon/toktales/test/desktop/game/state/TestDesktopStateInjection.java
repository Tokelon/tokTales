package com.tokelon.toktales.test.desktop.game.state;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.test.core.game.state.enginestate.DefaultEngineGamestate;
import com.tokelon.toktales.test.core.game.state.enginestate.EngineGamestate;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamescene;
import com.tokelon.toktales.test.desktop.game.state.DesktopTestStatesInjectModule;
import com.tokelon.toktales.test.desktop.game.state.enginestate.DesktopEngineGamestateControlScheme;
import com.tokelon.toktales.test.desktop.game.state.enginestate.DesktopEngineGamestateInputHandler;

public class TestDesktopStateInjection {


	@Test
	public void EngineGamestateInjection_ShouldPassCorrectTypes() {
		Injector injector = DesktopTestStatesInjectModule.createDesktopStatesInjector();
		
		EngineGamestate<IEngineGamescene> gamestate = injector.getInstance(DefaultEngineGamestate.class);
		
		assertTrue(gamestate.getStateControlScheme().getClass() == DesktopEngineGamestateControlScheme.class);
		assertTrue(gamestate.getStateInputHandler().getClass() == DesktopEngineGamestateInputHandler.class);
	}
	
	@Test
	public void EngineGamestateInjection_ShouldResetScope() {
		Injector injector = DesktopTestStatesInjectModule.createDesktopStatesInjector();
		
		EngineGamestate<IEngineGamescene> gamestate01 = injector.getInstance(DefaultEngineGamestate.class);
		EngineGamestate<IEngineGamescene> gamestate02 = injector.getInstance(DefaultEngineGamestate.class);
		
		assertNotSame(gamestate01, gamestate02);

		assertNotSame(gamestate01.getRenderOrder(), gamestate02.getRenderOrder());
		assertNotSame(gamestate01.getStateInput(), gamestate02.getStateInput());
		
		assertNotSame(gamestate01.getStateInputHandler(), gamestate02.getStateInputHandler());
		assertNotSame(gamestate01.getStateControlScheme(), gamestate02.getStateControlScheme());
	}
	
	@Test
	public void EngineGamestateInjection_ShouldRespectScope() {
		Injector injector = DesktopTestStatesInjectModule.createDesktopStatesInjector();
		
		EngineGamestate<IEngineGamescene> gamestate = injector.getInstance(DefaultEngineGamestate.class);
		
		assertSame(gamestate, ((DesktopEngineGamestateInputHandler)gamestate.getStateInputHandler()).getGamestate());
	}
	
}
