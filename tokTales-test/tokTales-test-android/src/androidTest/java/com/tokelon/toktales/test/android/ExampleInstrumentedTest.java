package com.tokelon.toktales.test.android;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
	@Test
	public void useAppContext() throws Exception {
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

		assertEquals("com.tokelon.toktales.test.android.test", appContext.getPackageName());
	}

	@Test
	public void useMockito() throws Exception {
		Context appContext = Mockito.mock(Context.class);
		Mockito.when(appContext.getPackageName()).thenReturn("com.tokelon.toktales.test.android.test");

		assertEquals("com.tokelon.toktales.test.android.test", appContext.getPackageName());
	}
}
