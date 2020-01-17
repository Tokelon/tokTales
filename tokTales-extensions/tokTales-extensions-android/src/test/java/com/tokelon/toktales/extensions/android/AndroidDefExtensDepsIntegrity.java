package com.tokelon.toktales.extensions.android;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.tokelon.toktales.android.test.AndroidTestLib;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.test.CoreTestLib;

public class AndroidDefExtensDepsIntegrity {
	
	
	@Test
	public void testInherited_TestCore() {
		String tag = CoreTestLib.TAG;
	}
	
	@Test
	public void testImplementation_TestAndroid() {
		String tag = AndroidTestLib.TAG;
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
