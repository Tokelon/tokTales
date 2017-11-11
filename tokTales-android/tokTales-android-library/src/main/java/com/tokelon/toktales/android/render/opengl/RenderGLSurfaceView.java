package com.tokelon.toktales.android.render.opengl;

import com.tokelon.toktales.android.render.opengl.program.IOpenGLRenderer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/* Remember to call onPause() and onResume() in the enclosing activity.
 * 
 */
public class RenderGLSurfaceView extends GLSurfaceView {

	
	private final Object mInputLock = new Object();
	
	private WrapperGLRenderer mWrapperRenderer;
	
	private IOpenGLRenderer mainRenderer;
	
	
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

		
		// Call before renderer is set for immediate effect
		// TODO: Important - OpenGL Debug Flags
		setDebugFlags(DEBUG_CHECK_GL_ERROR | DEBUG_LOG_GL_CALLS);
		
		
		
		mWrapperRenderer = new WrapperGLRenderer();
		setRenderer(mWrapperRenderer);
		
		
		// Call after renderer is set
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		
	}
	
	
	public void setMainRenderer(final IOpenGLRenderer mainRenderer) {
		synchronized (mInputLock) {

			this.mainRenderer = mainRenderer;
			

			/* To guarantee thread safety this method must be called inside the renderer thread.
			 * 
			 */
			queueEvent(new Runnable() {

				@Override
				public void run() {
					mWrapperRenderer.setMainRenderer(mainRenderer);
				}
			});
		}
		
	}
	
	
	@Override
	public void onPause() {
		super.onPause();
		mWrapperRenderer.onPause();
		
		synchronized (mInputLock) {
			if(mainRenderer != null) {
				mainRenderer.onPause();
			}
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		mWrapperRenderer.onResume();
		
		synchronized (mInputLock) {
			if(mainRenderer != null) {
				mainRenderer.onResume();
			}
		}
	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (mInputLock) {						// Possible perfomance decrease ?

			if(mainRenderer != null) {
				return mainRenderer.onTouch(event);
			}
			else {
				return super.onTouchEvent(event);
				//return false;
			}
		}
	}

	
}
