package com.tokelon.toktales.test.core.engine.inject;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.google.inject.CreationException;
import com.google.inject.Injector;
import com.google.inject.spi.Message;
import com.tokelon.toktales.core.engine.inject.IInjectConfig;

public final class TestInjectionHelper {

	private TestInjectionHelper() {}


	public static final String[] CORE_EXPECTED_BINDING_TYPES =
		{
				"IEnvironment", "IContentService", "IInputService",
				"ILogService", "IRenderService", "IStorageService",
				"IUIService", "IGameAdapter"
		};
	

	public static void assertInjectorCreationFailsWithExpectedBindings(IInjectConfig injectConfig, String[] expectedBindingTypes) {
		try {
			Injector injector = injectConfig.createInjector();
			fail("Injector creation did not fail");
		} catch(CreationException ex) {
			
			for(Message errorMessage: ex.getErrorMessages()) {
				assertTrue("Error does not match expected bindings: " + errorMessage.getMessage(),
						errorMessageMatchesExpectedBinding(errorMessage.getMessage(), expectedBindingTypes));
			}
		}
	}
	
	public static boolean errorMessageMatchesExpectedBinding(String error, String[] expectedTypes) {
		for(String type: expectedTypes) {
			if(error.contains(type + " was bound")) {
				return true;
			}
		}
		
		return false;
	}
	
}
