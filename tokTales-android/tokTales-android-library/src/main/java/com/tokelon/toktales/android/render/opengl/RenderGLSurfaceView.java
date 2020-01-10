package com.tokelon.toktales.android.render.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.tokelon.toktales.android.render.DelegateRenderViewAdapter;
import com.tokelon.toktales.android.render.IRenderView;
import com.tokelon.toktales.android.render.IRenderViewAdapter;
import com.tokelon.toktales.core.render.ISurfaceManager;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/** Note: You have to call {@link #onPause()} and {@link #onResume()} in the enclosing activity.
 * 
 */
public class RenderGLSurfaceView extends GLSurfaceView implements IRenderView {


	private final DelegateRenderViewAdapter delegateAdapter = new DelegateRenderViewAdapter();
	private final DelegateRenderer delegateRenderer = new DelegateRenderer();
	
	private String viewName;
	private ISurfaceManager surfaceManager;
	
	
	public RenderGLSurfaceView(Context context) {
		super(context);
		
		init(context);
	}
	
	public RenderGLSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		init(context);
	}

	protected void init(Context context) {
		// TODO: Test if this works consistent, otherwise implement creating a context in resume()
		setPreserveEGLContextOnPause(true);
		
		// Create an OpenGL ES 2.0 context
		setEGLContextClientVersion(2);

		
		// Set the renderer
		setRenderer(delegateRenderer);
		
		
		// Call after renderer is set
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}
	
	
	@Override
	public void setRenderViewAdapter(IRenderViewAdapter adapter) {
		this.surfaceManager = adapter.getSurfaceManagerFactory().create(this);
		
		if(viewName != null) {
			this.surfaceManager.setSurfaceName(viewName);
		}

		// To guarantee thread safety this method must be called inside the renderer thread.
		queueEvent(() -> delegateAdapter.setAdapter(adapter));
	}


	@Override
	public void onResume() {
		super.onResume();
		
		delegateAdapter.onResume();
	}
	
	@Override
	public void onPause() {
		// TODO: Pause delegate adapter first?
		
		super.onPause();
		
		delegateAdapter.onPause();
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// If delegate adapter not set ?
		// return super.onTouchEvent(event);
		
		return delegateAdapter.onTouch(event);
	}
	
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		super.surfaceDestroyed(holder);
		
		// TODO: Does this complement the renderer lifecycle correctly?
		delegateAdapter.onSurfaceDestroyed();
	}

	
	@Override
	public String getViewName() {
		return viewName;
	}
	
	@Override
	public void setViewName(String name) {
		this.viewName = name;
	}
	
	
	protected class DelegateRenderer implements Renderer {
		
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			delegateAdapter.onSurfaceCreated(surfaceManager);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			delegateAdapter.onSurfaceChanged(width, height);
		}
		
		@Override
		public void onDrawFrame(GL10 gl) {
			delegateAdapter.onDrawFrame();
		}
	}
	
}
