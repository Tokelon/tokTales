package com.tokelon.toktales.tokteller;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.test.android.AndroidTestDepsIntegrity;
import com.tokelon.toktales.test.core.CoreTestDepsIntegrity;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class AndroidAppDepsIntegrity {
	
	public static final String TAG = AndroidAppDepsIntegrity.class.getSimpleName();
	
	
	@Test
	public void testInherited_TestCore() {
		String tag = CoreTestDepsIntegrity.TAG;
	}
	
	@Test
	public void testImplementation_TestAndroid() {
		String tag = AndroidTestDepsIntegrity.TAG;
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