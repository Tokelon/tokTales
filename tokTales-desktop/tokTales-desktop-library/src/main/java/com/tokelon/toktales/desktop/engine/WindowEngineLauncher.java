package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.desktop.lwjgl.LWJGLEngineLauncher;
import com.tokelon.toktales.desktop.lwjgl.LWJGLInputProcessor;
import com.tokelon.toktales.desktop.ui.window.IWindowContext;
import com.tokelon.toktales.desktop.ui.window.IWindowHandler;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public class WindowEngineLauncher extends LWJGLEngineLauncher implements IWindowEngineLauncher {


	private IEngineLooper looper;
	
	private final IWindowHandler windowHandler;

	public WindowEngineLauncher(IHierarchicalInjectConfig injectConfig, IWindowHandler windowHandler) {
		super(injectConfig, new WindowEngineLooper(windowHandler, new LWJGLInputProcessor()));
		
		this.windowHandler = windowHandler;
	}
	
	public WindowEngineLauncher(IHierarchicalInjectConfig injectConfig, IWindowHandler windowHandler, ILoggerFactory loggerFactory) {
		super(injectConfig, new WindowEngineLooper(windowHandler, new LWJGLInputProcessor()), loggerFactory);
		
		this.windowHandler = windowHandler;
	}

	public WindowEngineLauncher(IHierarchicalInjectConfig injectConfig, IWindowHandler windowHandler, IEngineLooper defaultLooper) {
		super(injectConfig, defaultLooper);
		
		this.windowHandler = windowHandler;
	}
	
	public WindowEngineLauncher(IHierarchicalInjectConfig injectConfig, IWindowHandler windowHandler, IEngineLooper defaultLooper, ILoggerFactory loggerFactory) {
		super(injectConfig, defaultLooper, loggerFactory);
		
		this.windowHandler = windowHandler;
	}


	@Override
	protected void startupProgram(IEngineContext engineContext) throws EngineException {
		super.startupProgram(engineContext);
		
		windowHandler.createWindowContext(engineContext);
	}
	
	@Override
	protected void shutdownProgram(IEngineContext engineContext) throws EngineException {
		try {
			windowHandler.destroyWindowContext();
		}
		finally {
			super.shutdownProgram(engineContext);
		}
	}
	
	
	@Override
	protected void loop(IEngineContext engineContext, IEngineLooper defaultLooper) throws EngineException {
		this.looper = defaultLooper;
		
		looper.loop(engineContext);
	}
	
	@Override
	public void terminate() {
		if(looper != null) {
			looper.stop();
		}
	}
	
	
	@Override
	public IWindowContext getWindowContext() {
		return windowHandler.getWindowContext();
	}
	
}
