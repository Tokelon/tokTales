package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.desktop.engine.setup.DesktopEngineSetup;
import com.tokelon.toktales.desktop.engine.setup.WindowContextManageSetupStep;
import com.tokelon.toktales.desktop.lwjgl.LWJGLInputProcessor;
import com.tokelon.toktales.desktop.ui.window.IWindowContext;
import com.tokelon.toktales.desktop.ui.window.IWindowHandler;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public class WindowEngineLauncher extends DesktopEngineLauncher implements IWindowEngineLauncher {


	public static final String SETUP_STEP_WINDOW_CONTEXT_MANAGE = "SETUP_STEP_WINDOW_CONTEXT_MANAGE";

	public static final String WINDOW_CONTEXT_MANAGE_RELATIVE_STEP = DesktopEngineSetup.SETUP_STEP_LWJGL_LAST;
	public static final double WINDOW_CONTEXT_MANAGE_RELATIVE_POSITION = 0.5d;


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
	protected IEngineContext createEngine(Class<? extends IGameAdapter> adapter, IEngineSetup setup) throws EngineException {
		WindowContextManageSetupStep windowContextManageStep = new WindowContextManageSetupStep(windowHandler);

		Double relativeStepPosition = setup.getSteps().getStepPosition(WINDOW_CONTEXT_MANAGE_RELATIVE_STEP);
		if(relativeStepPosition == null) {
			getLogger().debug("Inserting setup step: {}", SETUP_STEP_WINDOW_CONTEXT_MANAGE);
			setup.getSteps().insertStep(SETUP_STEP_WINDOW_CONTEXT_MANAGE, windowContextManageStep);
		}
		else {
			double stepPosition = relativeStepPosition + WINDOW_CONTEXT_MANAGE_RELATIVE_POSITION;
			getLogger().debug("Inserting setup step: {}, at position {}", SETUP_STEP_WINDOW_CONTEXT_MANAGE, stepPosition);
			setup.getSteps().insertStep(SETUP_STEP_WINDOW_CONTEXT_MANAGE, windowContextManageStep, stepPosition);
		}

		if(!setup.getSteps().hasStep(DesktopEngineSetup.SETUP_STEP_LWJGL_INIT_GLFW)) {
			getLogger().warn("Required setup step is not configured: {}", DesktopEngineSetup.SETUP_STEP_LWJGL_INIT_GLFW);
		}

		return super.createEngine(adapter, setup);
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
