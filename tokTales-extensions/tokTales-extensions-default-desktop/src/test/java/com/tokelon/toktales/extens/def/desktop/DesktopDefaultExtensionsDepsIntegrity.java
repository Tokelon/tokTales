package com.tokelon.toktales.extens.def.desktop;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.test.core.CoreTestDepsIntegrity;
import com.tokelon.toktales.test.desktop.DesktopTestDepsIntegrity;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class DesktopDefaultExtensionsDepsIntegrity {
	
	public static final String TAG = DesktopDefaultExtensionsDepsIntegrity.class.getSimpleName();
	
	
	@Test
	public void testInherited_TestCore() {
		String tag = CoreTestDepsIntegrity.TAG;
	}
	
	@Test
	public void testImplementation_TestDesktop() {
		String tag = DesktopTestDepsIntegrity.TAG;
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
