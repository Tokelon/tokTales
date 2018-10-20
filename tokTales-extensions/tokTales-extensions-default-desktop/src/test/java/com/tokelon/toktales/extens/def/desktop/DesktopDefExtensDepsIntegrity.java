package com.tokelon.toktales.extens.def.desktop;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.test.CoreTestLib;
import com.tokelon.toktales.desktop.test.DesktopTestLib;

public class DesktopDefExtensDepsIntegrity {
	
	
	@Test
	public void testInherited_TestCore() {
		String tag = CoreTestLib.TAG;
	}
	
	@Test
	public void testImplementation_TestDesktop() {
		String tag = DesktopTestLib.TAG;
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
