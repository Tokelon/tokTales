package com.tokelon.toktales.android.render.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.tokelon.toktales.android.render.opengl.program.IOpenGLRenderer;

import android.opengl.GLSurfaceView.Renderer;

public class WrapperGLRenderer implements Renderer {

	private IOpenGLRenderer mMainRenderer;
	
	private boolean surfaceCreated = false;
	private boolean surfaceChanged = false;
	
	private int currentWidth = 0;
	private int currentHeight = 0;
	

	
	/** Sets the actual renderer.<br><br>
	 * Caution: This method must be called inside the rendering thread or before rendering has started, otherwise thread safety cannot be guaranteed.
	 * 
	 * @param mainRenderer The renderer to forward calls to.
	 */
	public void setMainRenderer(IOpenGLRenderer mainRenderer) {
		this.mMainRenderer = mainRenderer;
		
		if(mainRenderer != null) {
			if(surfaceCreated) {
				mainRenderer.onSurfaceCreated();
				
				if(surfaceChanged) {
					mainRenderer.onSurfaceChanged(currentWidth, currentHeight);
				}
			}
		}
	}
	
	
	public void onPause() {
		// Pause stuff
	}
	
	
	public void onResume() {
		// Unpause stuff
	}
	
	
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		surfaceCreated = true;
		surfaceChanged = false;
		
		if(mMainRenderer != null) {
			mMainRenderer.onSurfaceCreated();
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		currentWidth = width;
		currentHeight = height;
		
		surfaceChanged = true;
		
		if(mMainRenderer != null) {
			mMainRenderer.onSurfaceChanged(width, height);
		}
	}

	
	@Override
	public void onDrawFrame(GL10 gl) {
		// Do something ?
		
		if(mMainRenderer != null) {
			mMainRenderer.onDrawFrame();
		}
	}
	

}
