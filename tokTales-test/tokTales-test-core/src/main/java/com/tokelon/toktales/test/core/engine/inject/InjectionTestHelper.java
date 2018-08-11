package com.tokelon.toktales.test.core.engine.inject;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.CreationException;
import com.google.inject.Injector;
import com.google.inject.spi.Message;
import com.tokelon.toktales.core.engine.inject.IInjectConfig;

public final class InjectionTestHelper {

	private InjectionTestHelper() {}


	public static final String[] CORE_EXPECTED_BINDING_TYPES =
		{
				"IEnvironment", "IContentService", "IInputService",
				"ILogService", "IRenderService", "IStorageService",	"IUIService",
				"IGameAdapter",
				"IGameStateInput",
				"IGL11", "IGL13", "IGL14", "IGL15", "IGL20"
		};
	
	public static final String[][] CORE_EXPECTED_BINDING_ANNOTATIONS =
		{
				{ "IGameStateInputHandler", "ForClass", "InitialGamestate" }
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
