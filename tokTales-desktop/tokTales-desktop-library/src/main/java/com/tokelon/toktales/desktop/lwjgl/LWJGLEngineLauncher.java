package com.tokelon.toktales.desktop.lwjgl;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.desktop.engine.DesktopEngineLauncher;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public class LWJGLEngineLauncher extends DesktopEngineLauncher {


	private LWJGLProgram program;
	
	protected LWJGLEngineLauncher(IHierarchicalInjectConfig injectConfig) {
		super(injectConfig);
	}

	protected LWJGLEngineLauncher(IHierarchicalInjectConfig injectConfig, ILoggerFactory loggerFactory) {
		super(injectConfig, loggerFactory);
	}
	
	public LWJGLEngineLauncher(IHierarchicalInjectConfig injectConfig, IEngineLooper defaultLooper) {
		super(injectConfig, defaultLooper);
	}
	
	public LWJGLEngineLauncher(IHierarchicalInjectConfig injectConfig, IEngineLooper defaultLooper, ILoggerFactory loggerFactory) {
		super(injectConfig, defaultLooper, loggerFactory);
	}


	@Override
	protected void startupEngine(IEngineContext engineContext) throws EngineException {
		startupProgram(engineContext);
		
		super.startupEngine(engineContext);
	}
	
	@Override
	protected void shutdownEngine(IEngineContext engineContext) throws EngineException {
		try {
			super.shutdownEngine(engineContext);
		}
		finally {
			shutdownProgram(engineContext);
		}
	}
	
	protected void startupProgram(IEngineContext engineContext) throws EngineException {
		this.program = new LWJGLProgram();
		try {
			program.setup();
		}
		catch (LWJGLException e) {
			throw new EngineException(e);
		}
	}
	
	protected void shutdownProgram(IEngineContext engineContext) throws EngineException {
		program.tearDown();
	}
	
}
