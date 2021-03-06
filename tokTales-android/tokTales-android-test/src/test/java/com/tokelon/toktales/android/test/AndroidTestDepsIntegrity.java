package com.tokelon.toktales.android.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.tokelon.toktales.android.input.IAndroidInputService;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.test.CoreTestLib;

public class AndroidTestDepsIntegrity {
	
	
	@Test
	public void api_TestCore() {
		String tag = CoreTestLib.TAG;
	}
	
	@Test
	public void implementation_Android() {
		IAndroidInputService inputService;
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
