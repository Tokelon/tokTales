package com.tokelon.toktales.core.test.engine.inject;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.inject.CreationException;
import com.google.inject.Injector;
import com.google.inject.spi.Message;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetDecoder;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetDecoder;
import com.tokelon.toktales.core.content.manage.sound.ISoundAssetDecoder;
import com.tokelon.toktales.core.engine.IEnvironment;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.inject.ForClass;
import com.tokelon.toktales.core.engine.inject.IInjectConfig;
import com.tokelon.toktales.core.engine.inject.annotation.RenderDrivers;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.log.ILogService;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.core.game.states.IGameStateInput;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.game.states.InitialGamestate;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.IRenderToolkit.IRenderToolkitFactory;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.opengl.gl20.IGL13;
import com.tokelon.toktales.core.render.opengl.gl20.IGL14;
import com.tokelon.toktales.core.render.opengl.gl20.IGL15;
import com.tokelon.toktales.core.render.opengl.gl20.IGL20;

public final class InjectionTestHelper {
	
	private InjectionTestHelper() {}


	public static final Class<?>[] CORE_EXPECTED_BINDING_TYPES =
		{
				IEnvironment.class, IContentService.class, IInputService.class,
				ILogService.class, IRenderService.class, IStorageService.class, IUIService.class,
				IGameAdapter.class,
				IGameStateInput.class,
				IGL11.class, IGL13.class, IGL14.class, IGL15.class, IGL20.class,
				ISoundAssetDecoder.class,
				IBitmapAssetDecoder.class,
				ITextureFontAssetDecoder.class,
				IRenderToolkitFactory.class
		};
	
	public static final Class<?>[][] CORE_EXPECTED_BINDING_ANNOTATIONS =
		{
				{ IGameStateInputHandler.class, ForClass.class, InitialGamestate.class },
				{ Set.class, IRenderDriverFactory.class, RenderDrivers.class }
		};

	
	public static Class<?>[] combineExpectedBindingTypes(Class<?>[]... arrays) {
		List<Class<?>> list = new ArrayList<>();
		for(Class<?>[] arr: arrays) {
			for(Class<?> clazz: arr) {
				list.add(clazz);
			}
		}
		
		return list.toArray(new Class<?>[0]);
	}

	public static Class<?>[][] combineExpectedBindingAnnotations(Class<?>[][]... arrays) {
		List<Class<?>[]> list = new ArrayList<>();
		for(Class<?>[][] arr: arrays) {
			for(Class<?>[] innerArr: arr) {
				list.add(innerArr);
			}
		}
		
		return list.toArray(new Class<?>[0][]);
	}
	
	
	public static void assertInjectorCreationFailsWithExpectedBindings(IInjectConfig injectConfig, Class<?>[] expectedBindingTypes, Class<?>[][] expectedBindingAnnotations) {
		try {
			Injector injector = injectConfig.createInjector();
			fail("Injector creation did not fail");
		} catch(CreationException ex) {
			
			for(Message errorMessage: ex.getErrorMessages()) {
				assertTrue("Error does not match expected bindings: " + errorMessage.getMessage(),
						errorMessageMatchesExpectedBinding(errorMessage.getMessage(), expectedBindingTypes, expectedBindingAnnotations));
			}
		}
	}
	
	public static boolean errorMessageMatchesExpectedBinding(String error, Class<?>[] expectedTypes, Class<?>[][] expectedBindingAnnotations) {
		for(Class<?> clazz: expectedTypes) {
			if(error.contains(clazz.getName() + " was bound")) {
				return true;
			}
		}
		
		for(Class<?>[] annotatedBinding: expectedBindingAnnotations) {
			boolean matches = true;
			for(Class<?> clazz: annotatedBinding) {
				matches = matches && error.contains(clazz.getName());
			}
			
			if(matches) {
				return true;
			}
		}
		
		return false;
	}
	
}
