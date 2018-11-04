package com.tokelon.toktales.test.core.game.states;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import javax.inject.Provider;

import org.junit.Test;

import com.google.common.reflect.TypeToken;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.StandardLogger;
import com.tokelon.toktales.core.game.model.Camera;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.states.BaseGamescene;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.game.states.DefaultGamestate;
import com.tokelon.toktales.core.game.states.GameSceneAssignment;
import com.tokelon.toktales.core.game.states.GenericSceneGamestateProvider;
import com.tokelon.toktales.core.game.states.GenericSceneGamestateProvider.IGenericSceneGamestateFactory;
import com.tokelon.toktales.core.game.states.IGameScene;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;
import com.tokelon.toktales.core.test.engine.inject.CoreInjectorTestHelper;
import com.tokelon.toktales.test.core.game.states.enginestate.DefaultEngineGamestate;
import com.tokelon.toktales.test.core.game.states.enginestate.EngineGamescene;
import com.tokelon.toktales.test.core.game.states.enginestate.EngineGamestate;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamescene;
import com.tokelon.toktales.test.core.game.states.enginestate.subenginestate.ISubEngineGamescene;
import com.tokelon.toktales.test.core.game.states.enginestate.subenginestate.SubEngineGamescene;
import com.tokelon.toktales.test.core.game.states.enginestate.subenginestate.SubEngineGamestate;

@SuppressWarnings("serial")
public class TestCoreStateSceneTypeConstrains {

	@SuppressWarnings("unused")
	private static class TestGamestate<T extends IGameScene> extends BaseGamestate<T> {
		public TestGamestate() {
			super();
		}

		public TestGamestate(Class<T> sceneTypeClass) {
			super(sceneTypeClass);
		}

		public TestGamestate(TypeToken<T> sceneTypeToken) {
			super(sceneTypeToken);
		}
		
		
		@Override
		public TypeToken<T> getSceneTypeToken() {
			return super.getSceneTypeToken();
		}
		@Override
		public Class<? super T> getSceneTypeClass() {
			return super.getSceneTypeClass();
		}
		@Override
		public Provider<? extends T> getSceneProvider() {
			return super.getSceneProvider();
		}
	}
	
	
	private interface ITestGamescene<T> extends IGameScene {
		
	}
	
	private static class TestGamescene<T> extends BaseGamescene implements ITestGamescene<T> {
		@Override
		public ICamera getSceneCamera() {
			return new Camera();
		}
		@Override
		protected ILogger getLog() {
			return new StandardLogger();
		}
	}
	
	private interface ITestEngineGamescene<T> extends IEngineGamescene {
		
	}
	
	@SuppressWarnings("unused")
	private static class TestEngineGamescene<T> extends EngineGamescene implements ITestEngineGamescene<T> {
		@Override
		public ICamera getSceneCamera() {
			return new Camera();
		}
	}
	

	@Test // Compile Test
	@SuppressWarnings("unused")
	public void BaseGamestate_GenericTypes_ShouldCompile() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();

		// Direct instancing only possible via subclass creation
		//BaseGamestate<IGameScene> baseGamestate = new BaseGamestate<>(IGameScene.class); // Alt version
		BaseGamestate<IGameScene> baseGamestate = new BaseGamestate<IGameScene>() { };
		
		BaseGamestate<IGameScene> defaultGamestate = injector.getInstance(DefaultGamestate.class);

		
		//BaseGamestate<IEngineGamescene> engineGamestate = new BaseGamestate<>(IEngineGamescene.class); // Alt version
		BaseGamestate<IEngineGamescene> engineGamestate = new BaseGamestate<IEngineGamescene>() { };
		
		// Not allowed - sceneType has to be same as T
		//BaseGamestate<IEngineGamescene> engineGamestateWithSub = new BaseGamestate<ISubEngineGamescene>() {};
		//BaseGamestate<IEngineGamescene> engineGamestateWithSub = new BaseGamestate<>(ISubEngineGamescene.class); // Alt version
		
		//BaseGamestate<ISubEngineGamescene> engineGamestateReversed = new BaseGamestate<>(IEngineGamescene.class); // Does not work
		BaseGamestate<ISubEngineGamescene> engineGamestateWithSubClass = new BaseGamestate<ISubEngineGamescene>() { };
		//BaseGamestate<ISubEngineGamescene> engineGamestateWithSubClass = new BaseGamestate<>(ISubEngineGamescene.class); // Alt version

		
		// Standard Interface
		Provider<IGameScene> gamesceneProviderStandard = null;
		IModifiableGameSceneControl<IGameScene> gamesceneControlStandard = null;
		BaseGamestate<IGameScene> baseGamestateStandard = new BaseGamestate<IGameScene>(gamesceneProviderStandard, gamesceneControlStandard) { }; // Anon type
		
		
		// Different Provider type
		Provider<IEngineGamescene> gamesceneProviderSubInterface = null; // Works, has to extends from sceneType
		
		IModifiableGameSceneControl<IGameScene> gamesceneControlSubInterface = null; // Must be the same as T
		//IModifiableGameSceneControl<IEngineGamescene> gamesceneControlSubInterface = null; // Not allowed
		
		BaseGamestate<IGameScene> baseGamestateSubInterface = new BaseGamestate<IGameScene>(gamesceneProviderSubInterface, gamesceneControlSubInterface) { };
		
		
		// Standard Class
		Provider<EngineGamescene> gamesceneProviderSub = null;
		IModifiableGameSceneControl<EngineGamescene> gamesceneControlSub = null;
		BaseGamestate<EngineGamescene> baseGamestateSub = new BaseGamestate<EngineGamescene>(gamesceneProviderSub, gamesceneControlSub) { };
		
		
		// Generic scene types
		BaseGamestate<ITestGamescene<String>> baseGamestateTestGamescene = new BaseGamestate<ITestGamescene<String>>() { };
		EngineGamestate<ITestEngineGamescene<String>> engineGamestateTestEngineGamescene = new EngineGamestate<ITestEngineGamescene<String>>(null, null, null, null) { };
	}
	
	
	@Test
	public void TypeToken_Equality_ShouldWorkCorrectly() {
		// Maybe move into separate class with additional tests comparing TypeToken with raw classes
		TypeToken<BaseGamestate<ITestGamescene<String>>> typeToken = new TypeToken<BaseGamestate<ITestGamescene<String>>>() {};
		
		assertTrue(typeToken.isSubtypeOf(typeToken));
		assertTrue(typeToken.isSupertypeOf(typeToken));
		
		
		TypeToken<?> of = TypeToken.of(typeToken.getType());

		assertEquals(typeToken, of);
		assertEquals(typeToken.getType(), of.getType());
		assertEquals(typeToken.getRawType(), typeToken.getRawType());
		
		assertTrue(typeToken.isSubtypeOf(of));
		assertTrue(typeToken.isSupertypeOf(of));
		assertTrue(of.isSubtypeOf(typeToken));
		assertTrue(of.isSupertypeOf(typeToken));
	}
	
	
	@Test
	public void TestGamestateOfEngineGamescene_InjectionWithGamestateProvider_ShouldUseCorrectSceneType() {
		IGenericSceneGamestateFactory<TestGamestate<IEngineGamescene>, IEngineGamescene> factory = (token) -> new TestGamestate<IEngineGamescene>(token);
		GenericSceneGamestateProvider<TestGamestate<IEngineGamescene>, IEngineGamescene> provider = new GenericSceneGamestateProvider<>(
				factory,
				new TypeToken<IEngineGamescene>() {}
		);
		
		Injector injector = CoreInjectorTestHelper.createCoreMockInjector(
				new Module[] {
						(binder) -> {
							binder.bind(new TypeLiteral<TestGamestate<IEngineGamescene>>() {}).toProvider(provider);
							binder.bind(IEngineGamescene.class).to(EngineGamescene.class);
						}
				},
				new Module[0]
		);

		TestGamestate<IEngineGamescene> gamestate = injector.getInstance(new Key<TestGamestate<IEngineGamescene>>() {});
		
		
		assertTrue(gamestate.assignScene("1", injector.getInstance(EngineGamescene.class)));
		assertFalse(gamestate.assignScene("2", new BaseGamescene()));
		assertFalse(gamestate.assignScene("3", new TestGamescene<String>()));

		assertEquals(new TypeToken<IEngineGamescene>() {}, gamestate.getSceneTypeToken());
		assertEquals(new TypeToken<IEngineGamescene>() {}.getType(), gamestate.getSceneTypeToken().getType());
		assertTrue(new TypeToken<IEngineGamescene>() {}.isSupertypeOf(gamestate.getActiveScene().getClass()));
		assertTrue(new TypeToken<IEngineGamescene>() {}.isSupertypeOf(gamestate.getSceneProvider().get().getClass()));
		assertEquals(IEngineGamescene.class, gamestate.getSceneTypeToken().getRawType());
		assertTrue(gamestate.getActiveScene() instanceof IEngineGamescene);
		assertTrue(gamestate.getSceneProvider().get() instanceof IEngineGamescene);
	}
	
	@Test
	public void TestGamestateOfTestGamescene_InjectionWithGamestateProvider_ShouldUseCorrectSceneType() {
		IGenericSceneGamestateFactory<TestGamestate<ITestGamescene<String>>, ITestGamescene<String>> factory = (token) -> new TestGamestate<ITestGamescene<String>>(token);
		GenericSceneGamestateProvider<TestGamestate<ITestGamescene<String>>, ITestGamescene<String>> provider = new GenericSceneGamestateProvider<>(
				factory,
				new TypeToken<ITestGamescene<String>>() {}
		);
		
		Injector injector = CoreInjectorTestHelper.createCoreMockInjector(
				new Module[] {
						(binder) -> {
							binder.bind(new TypeLiteral<TestGamestate<ITestGamescene<String>>>() {}).toProvider(provider);
							binder.bind(new TypeLiteral<ITestGamescene<String>>() {}).to(new TypeLiteral<TestGamescene<String>>() {});
						}
				},
				new Module[0]
		);

		TestGamestate<ITestGamescene<String>> gamestate = injector.getInstance(new Key<TestGamestate<ITestGamescene<String>>>() {});

		
		assertTrue(gamestate.assignSceneWithGenericType("1", new GameSceneAssignment<TestGamescene<String>>(new TestGamescene<String>()) { }));
		assertFalse(gamestate.assignSceneWithGenericType("2", new GameSceneAssignment<TestGamescene<Object>>(new TestGamescene<Object>()) { }));
		assertFalse(gamestate.assignSceneWithGenericType("3", new GameSceneAssignment<TestGamescene<IGameScene>>(new TestGamescene<IGameScene>()) { }));
		
		assertEquals(new TypeToken<ITestGamescene<String>>() {}, gamestate.getSceneTypeToken());
		assertEquals(new TypeToken<ITestGamescene<String>>() {}.getType(), gamestate.getSceneTypeToken().getType());
		assertTrue(new TypeToken<ITestGamescene<String>>() {}.getRawType().isInstance(gamestate.getActiveScene()));
		
		assertEquals(ITestGamescene.class, gamestate.getSceneTypeToken().getRawType());
		assertTrue(gamestate.getActiveScene() instanceof ITestGamescene);
		assertTrue(gamestate.getSceneProvider().get() instanceof ITestGamescene);
		
		// Generic comparison does not work because the scene has lost it's generic information already
		assertFalse(new TypeToken<ITestGamescene<String>>() {}.isSupertypeOf(gamestate.getActiveScene().getClass()));
		assertFalse(new TypeToken<ITestGamescene<String>>() {}.isSupertypeOf(gamestate.getSceneProvider().get().getClass()));
	}
	
	
	@Test
	public void EngineGamestateInjection_ShouldUseCorrectSceneType() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();
		
		EngineGamestate<IEngineGamescene> gamestate = injector.getInstance(DefaultEngineGamestate.class);
		
		assertEquals(TypeToken.of(IEngineGamescene.class), gamestate.getSceneTypeToken());
		assertEquals(IEngineGamescene.class, gamestate.getSceneTypeToken().getType());
		assertEquals(IEngineGamescene.class, gamestate.getSceneTypeClass());
		assertTrue(gamestate.getActiveScene() instanceof EngineGamescene);
		assertTrue(gamestate.getSceneProvider().get() instanceof EngineGamescene);
	}
	
	@Test
	public void SubEngineGamestateInjection_ShouldUseCorrectSceneType() {
		Injector injector = CoreTestStatesInjectModule.createCoreStatesInjector();
		
		SubEngineGamestate gamestate = injector.getInstance(SubEngineGamestate.class);
		
		assertEquals(TypeToken.of(ISubEngineGamescene.class), gamestate.getSceneTypeToken());
		assertEquals(ISubEngineGamescene.class, gamestate.getSceneTypeToken().getType());
		assertEquals(ISubEngineGamescene.class, gamestate.getSceneTypeClass());
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
