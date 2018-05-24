package com.tokelon.toktales.test.core.game.states;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import javax.inject.Provider;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.game.states.DefaultGamestate;
import com.tokelon.toktales.core.game.states.IGameScene;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;
import com.tokelon.toktales.test.core.game.states.enginestate.DefaultEngineGamestate;
import com.tokelon.toktales.test.core.game.states.enginestate.EngineGamescene;
import com.tokelon.toktales.test.core.game.states.enginestate.EngineGamestate;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamescene;
import com.tokelon.toktales.test.core.game.states.enginestate.subenginestate.ISubEngineGamescene;
import com.tokelon.toktales.test.core.game.states.enginestate.subenginestate.SubEngineGamescene;
import com.tokelon.toktales.test.core.game.states.enginestate.subenginestate.SubEngineGamestate;

public class TestCoreStateSceneTypeConstrains {


	// Compile Test
	@SuppressWarnings("unused")
	public void BaseGamestate_GenericTypes_ShouldCompile() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();

		BaseGamestate<IGameScene> baseGamestate = new BaseGamestate<>(IGameScene.class);
		BaseGamestate<IGameScene> defaultGamestate = injector.getInstance(DefaultGamestate.class);

		BaseGamestate<IEngineGamescene> engineGamestate = new BaseGamestate<>(IEngineGamescene.class);
		// TODO: This is somewhat problematic - Trying to assign an IEngineGamescene will fail
		BaseGamestate<IEngineGamescene> engineGamestateWithSubClass = new BaseGamestate<>(ISubEngineGamescene.class);
		
		// This does not work because of constrain
		//BaseGamestate<ISubEngineGamescene> engineGamestateBad = new BaseGamestate<>(IEngineGamescene.class);
		
		
		Provider<IGameScene> gamesceneProviderStandard = null;
		IModifiableGameSceneControl<IGameScene> gamesceneControlStandard = null;
		BaseGamestate<IGameScene> baseGamestateStandard = new BaseGamestate<IGameScene>(IGameScene.class, gamesceneProviderStandard, gamesceneControlStandard) { };
		
		Provider<IEngineGamescene> gamesceneProviderSubInterface = null; // Works, has to be same as sceneType
		IModifiableGameSceneControl<IGameScene> gamesceneControlSubInterface = null; // Must be the same as T
		//IModifiableGameSceneControl<IEngineGamescene> gamesceneControlSubInterface = null; // Does not work
		BaseGamestate<IGameScene> baseGamestateSubInterface = new BaseGamestate<IGameScene>(IEngineGamescene.class, gamesceneProviderSubInterface, gamesceneControlSubInterface) { };
		
		Provider<EngineGamescene> gamesceneProviderSub = null;
		IModifiableGameSceneControl<IGameScene> gamesceneControlSub = null;
		BaseGamestate<IGameScene> baseGamestateSub = new BaseGamestate<IGameScene>(EngineGamescene.class, gamesceneProviderSub, gamesceneControlSub) { };
		
	}

	@Test
	public void EngineGamestateInjection_ShouldUseCorrectSceneType() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();
		
		EngineGamestate<IEngineGamescene> gamestate = injector.getInstance(DefaultEngineGamestate.class);
		
		assertTrue(gamestate.getSceneType() == IEngineGamescene.class);
		assertTrue(gamestate.getActiveScene() instanceof EngineGamescene);
		assertTrue(gamestate.getSceneProvider().get() instanceof EngineGamescene);
	}
	
	@Test
	public void SubEngineGamestateInjection_ShouldUseCorrectSceneType() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();
		
		SubEngineGamestate gamestate = injector.getInstance(SubEngineGamestate.class);
		
		assertTrue(gamestate.getSceneType() == ISubEngineGamescene.class);
		assertTrue(gamestate.getActiveScene() instanceof SubEngineGamescene);
		assertTrue(gamestate.getSceneProvider().get() instanceof SubEngineGamescene);
	}
	
	@Test
	public void SubEngineGamestateSceneAddition_ShouldAddSceneToSceneControlSuccessfully() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();
		final String SCENE_NAME = "subenginescene";

		SubEngineGamestate gamestate = injector.getInstance(SubEngineGamestate.class);
		
		ISubEngineGamescene subEngineGamescene = gamestate.getSceneProvider().get();
		gamestate.getSceneControl().addScene(SCENE_NAME, subEngineGamescene);
		
		assertEquals(subEngineGamescene, gamestate.getSceneControl().getScene(SCENE_NAME));
	}

	@Test(expected=ClassCastException.class)
	public void SubEngineGamestateSceneAddition_WithBadSceneControl_ShouldFailToReturnScene() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();
		final String BAD_SCENE_NAME = "badscene";

		SubEngineGamestate gamestate = injector.getInstance(SubEngineGamestate.class);
		
		@SuppressWarnings({ "rawtypes", "unchecked" }) IModifiableGameSceneControl<IEngineGamescene> badSceneControl = (IModifiableGameSceneControl) gamestate.getSceneControl();
		IEngineGamescene badScene = injector.getInstance(EngineGamescene.class);
		badSceneControl.addScene(BAD_SCENE_NAME, badScene);
		
		@SuppressWarnings("unused")	ISubEngineGamescene badSceneReturned = gamestate.getSceneControl().getScene(BAD_SCENE_NAME);
	}
	
	@Test
	public void SubEngineGamestateAssignScene_ShouldAssignSceneSuccessfully() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();
		final String SCENE_NAME = "subenginescene";

		SubEngineGamestate gamestate = injector.getInstance(SubEngineGamestate.class);
		
		SubEngineGamescene scene = injector.getInstance(SubEngineGamescene.class);
		boolean result = gamestate.assignScene(SCENE_NAME, scene);
		
		assertTrue(result);
		assertSame(scene, gamestate.getSceneControl().getScene(SCENE_NAME));
	}
	
}
