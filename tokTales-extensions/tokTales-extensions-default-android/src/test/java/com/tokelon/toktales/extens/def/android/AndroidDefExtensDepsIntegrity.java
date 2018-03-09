package com.tokelon.toktales.extens.def.android;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.test.android.AndroidTestDepsIntegrity;
import com.tokelon.toktales.test.core.CoreTestDepsIntegrity;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class AndroidDefExtensDepsIntegrity {
	
	public static final String TAG = AndroidDefExtensDepsIntegrity.class.getSimpleName();
	
	
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
