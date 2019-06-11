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
	// TODO: Use Class instead of String?
	
	private InjectionTestHelper() {}


	public static final String[] CORE_EXPECTED_BINDING_TYPES =
		{
				IEnvironment.class.getName(), IContentService.class.getName(), IInputService.class.getName(),
				ILogService.class.getName(), IRenderService.class.getName(), IStorageService.class.getName(), IUIService.class.getName(),
				IGameAdapter.class.getName(),
				IGameStateInput.class.getName(),
				IGL11.class.getName(), IGL13.class.getName(), IGL14.class.getName(), IGL15.class.getName(), IGL20.class.getName(),
				ISoundAssetDecoder.class.getName(),
				IBitmapAssetDecoder.class.getName(),
				ITextureFontAssetDecoder.class.getName(),
				IRenderToolkitFactory.class.getName()
		};
	
	public static final String[][] CORE_EXPECTED_BINDING_ANNOTATIONS =
		{
				{ IGameStateInputHandler.class.getName(), ForClass.class.getName(), InitialGamestate.class.getName() },
				{ Set.class.getName(), IRenderDriverFactory.class.getName(), RenderDrivers.class.getName() }
		};

	
	public static String[] combineExpectedBindingTypes(String[]... arrays) {
		List<String> list = new ArrayList<>();
		for(String[] arr: arrays) {
			for(String str: arr) {
				list.add(str);
			}
		}
		
		return list.toArray(new String[0]);
	}

	public static String[][] combineExpectedBindingAnnotations(String[][]... arrays) {
		List<String[]> list = new ArrayList<>();
		for(String[][] arr: arrays) {
			for(String[] innerArr: arr) {
				list.add(innerArr);
			}
		}
		return list.toArray(new String[0][]);
	}
	
	
	public static void assertInjectorCreationFailsWithExpectedBindings(IInjectConfig injectConfig, String[] expectedBindingTypes, String[][] expectedBindingAnnotations) {
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
	
	public static boolean errorMessageMatchesExpectedBinding(String error, String[] expectedTypes, String[][] expectedBindingAnnotations) {
		for(String type: expectedTypes) {
			if(error.contains(type + " was bound")) {
				return true;
			}
		}
		
		for(String[] annotatedBinding: expectedBindingAnnotations) {
			boolean matches = true;
			for(String str: annotatedBinding) {
				matches = matches && error.contains(str);
			}
			
			if(matches) {
				return true;
			}
		}
		
		return false;
	}
	
}
