package com.tokelon.toktales.core.render.opengl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoopGLErrorUtils implements IGLErrorUtils {

	
	private static final List<IGLError> NOOP_ERROR_LIST = Collections.unmodifiableList(new ArrayList<>(0));

	@Override
	public void enableErrorChecking(boolean enabled) { }

	@Override
	public boolean isErrorCheckingEnabled() { return false;	}

	@Override
	public void assertNoGLErrors() throws OpenGLErrorException { }

	@Override
	public int logGLErrors(String context) { return 0; }

	@Override
	public List<IGLError> getGLErrors() { return NOOP_ERROR_LIST; }

}
