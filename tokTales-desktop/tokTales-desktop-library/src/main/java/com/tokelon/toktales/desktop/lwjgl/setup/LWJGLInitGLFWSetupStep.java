package com.tokelon.toktales.desktop.lwjgl.setup;

import org.lwjgl.glfw.GLFW;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.ISetupStep;
import com.tokelon.toktales.desktop.lwjgl.LWJGLException;

public class LWJGLInitGLFWSetupStep implements ISetupStep {


	@Override
	public void onBuildUp(IEngineContext engineContext) throws EngineException {
		if(!GLFW.glfwInit()) {
			throw new EngineException(new LWJGLException("Failed to initialize GLFW"));
		}
	}

	@Override
	public void onTearDown(IEngineContext engineContext) throws EngineException {
		GLFW.glfwTerminate();
	}

}
