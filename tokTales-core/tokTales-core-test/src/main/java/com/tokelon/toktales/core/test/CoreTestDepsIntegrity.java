package com.tokelon.toktales.core.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.tokelon.toktales.core.engine.IEngine;

public class CoreTestDepsIntegrity {
	
	public static final String TAG = CoreTestDepsIntegrity.class.getSimpleName();
	
	
	@Test
	public void apiCore() {
		IEngine engine;
	}
	
	@Test
	public void testImplementationJUnit() {
		assertTrue(true);
	}
	
	@Test
	public void testImplementationMockito() {
		IEngine engine = mock(IEngine.class);
	}
	
}
