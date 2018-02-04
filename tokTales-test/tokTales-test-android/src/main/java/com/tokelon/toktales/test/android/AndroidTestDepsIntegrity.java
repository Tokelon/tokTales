package com.tokelon.toktales.test.android;

import com.tokelon.toktales.android.input.IAndroidInputService;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.test.core.CoreTestDepsIntegrity;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class AndroidTestDepsIntegrity {
	
	public static final String TAG = AndroidTestDepsIntegrity.class.getSimpleName();
	
	
	@Test
	public void api_TestCore() {
		String tag = CoreTestDepsIntegrity.TAG;
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
