package com.tokelon.toktales.extensions.core;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.test.CoreTestLib;

public class CoreDefExtensDepsIntegrity {
	
	
	@Test
	public void testImplementation_TestCore() {
		String tag = CoreTestLib.TAG;
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
