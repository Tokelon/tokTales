package com.tokelon.toktales.extens.def.android;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.tokelon.toktales.android.test.AndroidTestDepsIntegrity;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.test.CoreTestDepsIntegrity;

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
