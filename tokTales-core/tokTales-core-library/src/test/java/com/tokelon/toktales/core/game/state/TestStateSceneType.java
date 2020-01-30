package com.tokelon.toktales.core.game.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.reflect.TypeToken;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.tokelon.toktales.core.game.state.BaseGamestate;
import com.tokelon.toktales.core.game.state.BaseGamestateProvider;
import com.tokelon.toktales.core.game.state.scene.BaseGamescene;
import com.tokelon.toktales.core.game.state.scene.IGameScene;
import com.tokelon.toktales.core.test.engine.inject.CoreInjectorTestHelper;

@SuppressWarnings("serial")
public class TestStateSceneType {

	
	public static class GenericGamescene<T> extends BaseGamescene implements IGameScene {
		
	}
	
	
	@Test
	public void BaseGamestateBasic_Injected_ShouldHaveCorrectSceneType() {
		Injector injector = CoreInjectorTestHelper.createCoreMockInjector();
		BaseGamestate<IGameScene> gamestate = injector.getInstance(new Key<BaseGamestate<IGameScene>>() {});
		
		assertEquals(TypeToken.of(IGameScene.class), gamestate.getSceneTypeToken());
		assertEquals(IGameScene.class, gamestate.getSceneTypeToken().getType());
		assertEquals(IGameScene.class, gamestate.getSceneTypeClass());
		assertTrue(gamestate.getActiveScene() instanceof IGameScene);
		assertTrue(gamestate.getSceneProvider().get() instanceof IGameScene);
	}
	
	@Test
	public void BaseGamestateGeneric_Injected_ShouldHaveCorrectSceneType() {
		Injector injector = CoreInjectorTestHelper.createCoreMockInjector(new Module[] {
				(binder) -> binder.bind(new TypeLiteral<BaseGamestate<GenericGamescene<String>>>() {}).toProvider(new BaseGamestateProvider<GenericGamescene<String>>(new TypeToken<GenericGamescene<String>>() {})) 
		}, new Module[0]);
		
		BaseGamestate<GenericGamescene<String>> gamestate = injector.getInstance(new Key<BaseGamestate<GenericGamescene<String>>>() {});
		
		assertEquals(new TypeToken<GenericGamescene<String>>() {}, gamestate.getSceneTypeToken());
		assertEquals(new TypeToken<GenericGamescene<String>>() {}.getType(), gamestate.getSceneTypeToken().getType());
		assertEquals(GenericGamescene.class, gamestate.getSceneTypeClass());
		assertTrue(gamestate.getActiveScene() instanceof GenericGamescene);
		assertTrue(gamestate.getSceneProvider().get() instanceof GenericGamescene);
	}
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void BaseGamestateGeneric_InstanceWithImplicitTypeToken_ShouldThrowException() {
		@SuppressWarnings("unused")
		BaseGamestate<GenericGamescene<String>> gamestate = new BaseGamestate<>();
	}
	
	@Test
	public void BaseGamestateGeneric_SubclassWithImplicitTypeToken_ShouldHaveCorrectSceneType() {
		BaseGamestate<GenericGamescene<String>> gamestate = new BaseGamestate<GenericGamescene<String>>() { };
		
		assertEquals(new TypeToken<GenericGamescene<String>>() {}, gamestate.getSceneTypeToken());
		assertEquals(new TypeToken<GenericGamescene<String>>() {}.getType(), gamestate.getSceneTypeToken().getType());
		assertEquals(GenericGamescene.class, gamestate.getSceneTypeClass());
	}
	
	@Test
	public void BaseGamestateGeneric_InstanceWithExplicitTypeToken_ShouldHaveCorrectSceneType() {
		BaseGamestate<GenericGamescene<String>> gamestate = new BaseGamestate<GenericGamescene<String>>(new TypeToken<GenericGamescene<String>>() {});
		
		assertEquals(new TypeToken<GenericGamescene<String>>() {}, gamestate.getSceneTypeToken());
		assertEquals(new TypeToken<GenericGamescene<String>>() {}.getType(), gamestate.getSceneTypeToken().getType());
		assertEquals(GenericGamescene.class, gamestate.getSceneTypeClass());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void BaseGamestateBasic_InstanceWithImplicitTypeToken_ShouldThrowException() {
		@SuppressWarnings("unused")
		BaseGamestate<IGameScene> gamestate = new BaseGamestate<>();
	}
	
	@Test
	public void BaseGamestateBasic_SubclassWithImplicitTypeToken_ShouldHaveCorrectSceneType() {
		BaseGamestate<IGameScene> gamestate = new BaseGamestate<IGameScene>() { };
		
		assertEquals(TypeToken.of(IGameScene.class), gamestate.getSceneTypeToken());
		assertEquals(IGameScene.class, gamestate.getSceneTypeToken().getType());
		assertEquals(IGameScene.class, gamestate.getSceneTypeClass());
	}
	
	@Test
	public void BaseGamestateBasic_InstanceWithExplicitTypeToken_ShouldHaveCorrectSceneType() {
		BaseGamestate<IGameScene> gamestate = new BaseGamestate<IGameScene>(TypeToken.of(IGameScene.class));
		
		assertEquals(TypeToken.of(IGameScene.class), gamestate.getSceneTypeToken());
		assertEquals(IGameScene.class, gamestate.getSceneTypeToken().getType());
		assertEquals(IGameScene.class, gamestate.getSceneTypeClass());
	}
	
	@Test
	public void BaseGamestateBasic_InstanceWithExplicitTypeClass_ShouldHaveCorrectSceneType() {
		BaseGamestate<IGameScene> gamestate = new BaseGamestate<IGameScene>(IGameScene.class);
		
		assertEquals(TypeToken.of(IGameScene.class), gamestate.getSceneTypeToken());
		assertEquals(IGameScene.class, gamestate.getSceneTypeToken().getType());
		assertEquals(IGameScene.class, gamestate.getSceneTypeClass());
	}
	
	
	
	@Test
	public void BaseGamestateGeneric_MethodSubclassWithImplicitTypeToken_ShouldHaveCorrectSceneType() {
		BaseGamestate<GenericGamescene<String>> createdGamestate = createGoodGamestateTypeToken(new TypeToken<GenericGamescene<String>>() {});
		
		assertEquals(new TypeToken<GenericGamescene<String>>() {}, createdGamestate.getSceneTypeToken());
		assertEquals(new TypeToken<GenericGamescene<String>>() {}.getType(), createdGamestate.getSceneTypeToken().getType());
		assertEquals(GenericGamescene.class, createdGamestate.getSceneTypeClass());
	}

	private <T extends IGameScene> BaseGamestate<T> createGoodGamestateTypeToken(TypeToken<T> sceneTypeToken) {
		BaseGamestate<T> gamestate = new BaseGamestate<T>(sceneTypeToken);
		return gamestate;
	}
	
	
	@Test
	public void BaseGamestateBasic_MethodInstanceWithExplicitTypeClass_ShouldHaveCorrectSceneType() {
		BaseGamestate<IGameScene> createdGamestate = createGoodGamestateClass(IGameScene.class);
		
		assertEquals(TypeToken.of(IGameScene.class), createdGamestate.getSceneTypeToken());
		assertEquals(IGameScene.class, createdGamestate.getSceneTypeToken().getType());
		assertEquals(IGameScene.class, createdGamestate.getSceneTypeClass());
	}
	
	private <T extends IGameScene> BaseGamestate<T> createGoodGamestateClass(Class<T> sceneTypeClass) {
		BaseGamestate<T> gamestate = new BaseGamestate<T>(sceneTypeClass);
		return gamestate;
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void BaseGamestateBasic_MethodInstanceWithExcplicitBadTypeToken_ShouldThrowException() {
		createBadGamestateTypeToken(new TypeToken<GenericGamescene<String>>() {});
	}
	
	private <T extends IGameScene> BaseGamestate<T> createBadGamestateTypeToken(TypeToken<T> sceneTypeToken) {
		BaseGamestate<T> gamestate = new BaseGamestate<T>(new TypeToken<T>(GenericGamescene.class) {});
		return gamestate;
	}
	
	
	@Test
	public void BaseGamestateBasic_MethodInstanceWithExcplicitBadTypeClass_ShouldThrowException() {
		BaseGamestate<BaseGamescene> gamestate = createBadGamestateClass(BaseGamescene.class);

		assertNotEquals(new TypeToken<BaseGamescene>() {}, gamestate.getSceneTypeToken());
		assertNotEquals(new TypeToken<BaseGamescene>() {}.getType(), gamestate.getSceneTypeToken().getType());
		assertNotEquals(BaseGamescene.class, gamestate.getSceneTypeClass());
	}
	
	private <T extends IGameScene> BaseGamestate<T> createBadGamestateClass(Class<T> sceneTypeClass) {
		// This test is kinda wonky - it's not so easy to trick Class and create some kind of Class<T>
		@SuppressWarnings("unchecked")
		BaseGamestate<T> gamestate = new BaseGamestate<T>((Class<T>)(new TypeToken<T>(BaseGamescene.class) {}.getRawType()));
		return gamestate;
	}
	
}
