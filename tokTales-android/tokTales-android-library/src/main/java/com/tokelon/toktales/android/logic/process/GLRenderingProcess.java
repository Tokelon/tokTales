package com.tokelon.toktales.android.logic.process;

import com.tokelon.toktales.android.render.opengl.RenderGLSurfaceView;
import com.tokelon.toktales.android.render.opengl.program.OpenGLRenderer;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.logic.process.IPauseableProcess;

public class GLRenderingProcess implements IPauseableProcess {

	public static final String TAG = "GLRenderingProcess";
	
	
	private final IGame game;
	private final IEngine engine;
	
	private RenderGLSurfaceView objRenderView;

	private OpenGLRenderer objMainRenderer;
	
	
	public GLRenderingProcess(IGame game, IEngine engine) {
		this.game = game;
		this.engine = engine;
	}
	
	
	@Override
	public void startProcess() {
		TokTales.getLog().d(TAG, "GLRenderingProcess started");

		objMainRenderer = new OpenGLRenderer(game, engine);
		
		
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
		TokTales.getLog().d(TAG, "GLRenderingProcess ended");

		clearObjects();
	}

	
	@Override
	public void pause() {
		TokTales.getLog().d(TAG, "GLRenderingProcess paused");

		objRenderView.onPause();
	}

	
	@Override
	public void unpause() {
		TokTales.getLog().d(TAG, "GLRenderingProcess unpaused");
		
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
