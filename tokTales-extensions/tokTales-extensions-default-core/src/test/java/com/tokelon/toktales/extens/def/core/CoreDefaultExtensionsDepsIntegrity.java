package com.tokelon.toktales.extens.def.core;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.test.core.CoreTestDepsIntegrity;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class CoreDefaultExtensionsDepsIntegrity {
	
	public static final String TAG = CoreDefaultExtensionsDepsIntegrity.class.getSimpleName();
	
	
	@Test
	public void testImplementation_TestCore() {
		String tag = CoreTestDepsIntegrity.TAG;
	}
	
	
	@Test
	public void testInherited_JUnit() {
		assertTrue(true);
	}
	
	@Test
	public void testInherited_Mockito() {
		IEngine engine = mock(IEngine.class);
	}
	
}
