package com.tokelon.toktales.android.logic.process;

import com.tokelon.toktales.android.render.opengl.RenderGLSurfaceView;
import com.tokelon.toktales.android.render.opengl.program.OpenGLRenderer;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.logic.process.IPauseableProcess;

public class GLRenderingProcess implements IPauseableProcess {

	public static final String TAG = "GLRenderingProcess";
	
	
	private RenderGLSurfaceView objRenderView;

	private OpenGLRenderer objMainRenderer;
	
	private final ILogger logger;
	private final IEngine engine;
	private final IGame game;
	
	public GLRenderingProcess(ILogger logger, IEngine engine, IGame game) {
		this.logger = logger;
		this.engine = engine;
		this.game = game;
	}
	
	
	@Override
	public void startProcess() {
		logger.d(TAG, "GLRenderingProcess started");

		objMainRenderer = new OpenGLRenderer(logger, engine, game);
		
		
		objRenderView.setMainRenderer(objMainRenderer);
		//objRenderView.setOnTouchListener(mainRenderer)	This works as well
		
		
		// Do this in case render framework becomes a surface handler
		/* 
		AndroidGLRenderFramework androidGLRenderFramework = (AndroidGLRenderFramework) Prog.getPIP().getProgramInterface(IPIP.IID_FRAMEWORK_RENDER);
		androidGLRenderFramework.setMainRenderer(mainRenderer);
		*/
		
	}

	
	@Override
	public void endProcess() {
		logger.d(TAG, "GLRenderingProcess ended");

		clearObjects();
	}

	
	@Override
	public void pause() {
		logger.d(TAG, "GLRenderingProcess paused");

		objRenderView.onPause();
	}

	
	@Override
	public void unpause() {
		logger.d(TAG, "GLRenderingProcess unpaused");
		
		objRenderView.onResume();
	}
	
	
	
	
	private void clearObjects() {
		objRenderView = null;
		objMainRenderer = null;
	}
	
	
	public void setObjRenderView(RenderGLSurfaceView renderView) {
		this.objRenderView = renderView;
	}
	
	
	public OpenGLRenderer getObjMainRenderer() {
		return objMainRenderer;
	}
	
}
