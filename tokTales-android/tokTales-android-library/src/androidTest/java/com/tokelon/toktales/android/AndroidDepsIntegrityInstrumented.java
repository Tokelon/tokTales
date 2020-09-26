package com.tokelon.toktales.android;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.tokelon.toktales.android.test.AndroidTestLib;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.test.CoreTestLib;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class AndroidDepsIntegrityInstrumented {
	

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
