package com.tokelon.toktales.desktop.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.test.CoreTestDepsIntegrity;
import com.tokelon.toktales.desktop.input.IDesktopInputService;

public class DesktopTestDepsIntegrity {
	
	public static final String TAG = DesktopTestDepsIntegrity.class.getSimpleName();
	
	
	@Test
	public void api_TestCore() {
		String tag = CoreTestDepsIntegrity.TAG;
	}
	
	@Test
	public void implementation_Desktop() {
		IDesktopInputService inputService;
	}
	
	
	@Test
	public void inherited_Core() {
		IEngine engine;
	}
	
	@Test
	public void inherited_JUnit() {
		assertTrue(true);
	}
	
	@Test
	public void inherited_Mockito() {
		IEngine engine = mock(IEngine.class);
	}
	
}
