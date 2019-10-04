package com.tokelon.toktales.android.render.opengl;

import com.tokelon.toktales.android.render.opengl.program.IOpenGLRenderer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/** Note: You have to call {@link #onPause()} and {@link #onResume()} in the enclosing activity.
 * 
 */
public class RenderGLSurfaceView extends GLSurfaceView implements IGLRenderView {


	private final Object inputLock = new Object();
	
	private IOpenGLRenderer mainRenderer;

	private DelegateGLRenderer delegateRenderer;

	
	public RenderGLSurfaceView(Context context) {
		super(context);
		
		init(context);
	}
	
	public RenderGLSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		init(context);
	}

	private void init(Context context) {
		// Create an OpenGL ES 2.0 context
		setEGLContextClientVersion(2);

		
		delegateRenderer = new DelegateGLRenderer();
		setRenderer(delegateRenderer);
		
		
		// Call after renderer is set
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}
	
	
	@Override
	public void setMainRenderer(final IOpenGLRenderer mainRenderer) {
		synchronized (inputLock) {
			this.mainRenderer = mainRenderer;

			// To guarantee thread safety this method must be called inside the renderer thread.
			queueEvent(() -> delegateRenderer.setMainRenderer(mainRenderer));
		}
	}
	

	@Override
	public void onResume() {
		super.onResume();
		
		//delegateRenderer.onResume(); // TODO: Use this
		
		synchronized (inputLock) {
			if(mainRenderer != null) {
				mainRenderer.onResume();
			}
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		//delegateRenderer.onPause(); // TODO: Use this
		
		synchronized (inputLock) {
			if(mainRenderer != null) {
				mainRenderer.onPause();
			}
		}
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (inputLock) {	// Possible perfomance decrease ?

			if(mainRenderer != null) { // TODO: Use delegate
				return mainRenderer.onTouch(event);
			}
			else {
				return super.onTouchEvent(event);
				//return false;
			}
		}
	}
	
}
